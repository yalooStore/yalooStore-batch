package com.yaloostore.batch.member.step;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.batch.config.ServerConfig;
import com.yaloostore.batch.member.dto.MemberIdResponse;
import com.yaloostore.batch.member.listener.ModifyInactiveMemberListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ModifyInactiveMemberStep {

    private final RestTemplate restTemplate;
    private final ServerConfig serverConfig;
    private final ModifyInactiveMemberListener modifyInactiveMemberListener;
    private static final int CHUNK_SIZE = 100;

    /**
     * 지난 1년간 접속 기록이 없는 회원을 가져오는 ItemReader Step 입니다.
     * 해당 클래스는 ListItemReader를 사용하고 있어 이는 데이터가 많은 경우 추천하지 않는 방식으로 추후 변경될 수 있습니다.
     *
     * @param today 오늘을 기준으로 1년간 접속 기록이 없는 회원을 찾아옵니다.
     * @return ListItemReader<MemberDto>
     * */
    @Bean
    @StepScope
    public ListItemReader<MemberIdResponse> listItemReader(@Value("#{jobParameters['today']}")LocalDate today) {
        List<MemberIdResponse> response = getInactiveMember(today);
        if (response.isEmpty()) {
            log.info("휴먼 회원 대상자 없음");
            return null;
        }
        return new ListItemReader<>(response);
    }

    @Bean
    public ItemWriter<? super Object> itemWriter(){
        return memberIdResponses -> modifyInactiveMember((List<? extends MemberIdResponse>) memberIdResponses);
    }

    /**
     * 해당 ItemReader, ItemWriter를 하나의 Step으로 만들어줍니다.
     *
     * @param jobRepository 배치 메타 데이터 엔티티의 지속성을 담당하는 레포지토리
     * @param transactionManager 트랜잭션 관리에 사용되는 인터페이스입니다.
     * @return step
     * */
    @Bean
    public Step modifyInactiveMemberStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("modifyInactiveMemberStep", jobRepository)
                .startLimit(10)
                .<MemberIdResponse, MemberIdResponse>chunk(CHUNK_SIZE,transactionManager)
                .reader(listItemReader(null))
                .writer(itemWriter())
                .listener(modifyInactiveMemberListener)
                .build();
    }


    /**
     * 오늘을 기준으로 1년간 접속 기록이 없는 회원 Pk 리스트를 가져옵니다.
     *
     * @param today 오늘을 기준으로 1년간 접속 기록이 없는 회원 Pk
     * @return List<MemberDto> 휴먼 회원 목록
     * */
    private List<MemberIdResponse> getInactiveMember(LocalDate today) {
        String uri = UriComponentsBuilder.fromHttpUrl(serverConfig.getShopUrl())
                .pathSegment("api", "service", "members", "loginHistory")
                .queryParam("today", today).toUriString();

        ResponseEntity<ResponseDto<List<MemberIdResponse>>> exchange =
                restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return Objects.requireNonNull(exchange.getBody().getData());
    }

    /**
     * 휴먼 회원의 Pk 리스트를 휴먼 회원으로 상태 수정 요청합니다.
     *
     * @param memberIdResponses 휴먼 회원 대상 회원들읠 Pk 리스트
     * */
    private void modifyInactiveMember(List<? extends MemberIdResponse> memberIdResponses) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<? extends List<? extends MemberIdResponse>> requestEntity = new HttpEntity<>(memberIdResponses, headers);

        String uri = UriComponentsBuilder.fromUriString(serverConfig.getShopUrl())
                .pathSegment().toUriString();

        ResponseEntity<Object> exchange = restTemplate.exchange(uri,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
    }


}
