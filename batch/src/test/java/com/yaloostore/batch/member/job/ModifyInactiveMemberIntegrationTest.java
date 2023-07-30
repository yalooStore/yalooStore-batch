package com.yaloostore.batch.member.job;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.batch.member.config.MemberJobConfig;
import com.yaloostore.batch.member.step.InactiveMemberStep;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@SpringBatchTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes ={InactiveMemberStep.class, TestConfiguration.class})
public class ModifyInactiveMemberIntegrationTest {


    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    private RestTemplate restTemplate;

    private JobLauncher jobLauncher;


    @BeforeEach
    public void setup(@Autowired Job modifyInactiveMemberJob){
        this.jobLauncherTestUtils.setJob(modifyInactiveMemberJob);
        this.jobRepositoryTestUtils.removeJobExecutions();


        restTemplate = Mockito.mock(RestTemplate.class);

        jobLauncher = Mockito.mock(JobLauncher.class);

    }


    @DisplayName("오늘을 기준으로 접속한지 1년이 지난 회원을 휴먼회원화 하는 job의 통합테스트")
    @Test
    public void modifyInactiveMemberJobTest() throws Exception {

//        JobParameters jobParameters = this.jobLauncherTestUtils.getUniqueJobParameters();
//
//        JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(jobParameters);
//
//        Assertions.assertThat(BatchStatus.COMPLETED).isEqualTo(jobExecution.getStatus());

        LocalDate today = LocalDate.now();
        Mockito.when(restTemplate.exchange(
                Mockito.anyString(),
                Mockito.eq(HttpMethod.GET),
                Mockito.isNotNull(),
                Mockito.any(ParameterizedTypeReference.class)
        )).thenReturn(ResponseEntity.ok(new ResponseDto<>()));

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("today", today.toString())
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // Assertions to verify the results
        Assertions.assertThat(BatchStatus.COMPLETED).isEqualTo(jobExecution.getStatus());
        // Add more assertions as needed

    }



}
