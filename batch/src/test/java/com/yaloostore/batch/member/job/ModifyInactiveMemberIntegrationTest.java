package com.yaloostore.batch.member.job;


import com.yaloostore.batch.member.config.MemberJobConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.BufferedReader;
import java.time.LocalDate;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@SpringBatchTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringJUnitConfig(MemberJobConfig.class)
public class ModifyInactiveMemberIntegrationTest {


    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @BeforeEach
    public void setup(@Autowired Job modifyInactiveMemberJob){
        this.jobLauncherTestUtils.setJob(modifyInactiveMemberJob);
        this.jobRepositoryTestUtils.removeJobExecutions();

    }


    @DisplayName("오늘을 기준으로 접속한지 1년이 지난 회원을 휴먼회원화 하는 job의 통합테스트")
    @Test
    public void modifyInactiveMemberJobTest() throws Exception {

        JobParameters jobParameters = this.jobLauncherTestUtils.getUniqueJobParameters();

        JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(jobParameters);

        Assertions.assertThat(BatchStatus.COMPLETED).isEqualTo(jobExecution.getStatus());
    }

}
