package com.yaloostore.batch.member.step;


import com.yaloostore.batch.config.ServerConfig;
import com.yaloostore.batch.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.yalooStore.common_utils.dto.ResponseDto;
import java.util.List;
import java.util.Objects;

/**
 * 생일 회원에게 생일 쿠폰 발급하는 배치 step 입니다.
 * */

@RequiredArgsConstructor
@Configuration
public class BirthDayCouponStep {

    private final ServerConfig serverConfig;
    private final RestTemplate restTemplate;
    private static final int CHUNK_SIZE = 100;


    /**
     * itemReader -> ItemProcessor -> ItemWriter
     *
     * 정해준 날짜 만큼 뒤에 생일인 회원 목록을 가져옵니다. 이때 조회한 날짜에 생일이 없으면 null값을 반환합니다.
     *
     * @param laterDays 오늘 날짜 기준으로 생일을 계산할 일수
     * @return 생일인 회원 목록을 조회하는 ListItemReader, 생일 회원이 없다며 null
     * */
    @Bean
    @StepScope
    public ListItemReader<MemberDto> listItemReader(@Value("#{jobParameters['laterDays']}") Integer laterDays){
        List<MemberDto> birthdayMemberList = getBirthdayMemberList(laterDays);
        if (birthdayMemberList.isEmpty()) {
            return null;
        }
        return new ListItemReader<>(birthdayMemberList);


    }

    private List<MemberDto> getBirthdayMemberList(int laterDays) {

        String uri = UriComponentsBuilder.fromHttpUrl(serverConfig.getShopUrl())
                .pathSegment("api", "service", "members", "birthday")
                .queryParam("lateDays", laterDays)
                .toUriString();

        ResponseEntity<ResponseDto<List<MemberDto>>> response = restTemplate.exchange(uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });


        return Objects.requireNonNull(response.getBody().getData());
    }



}
