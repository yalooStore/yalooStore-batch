package com.yaloostore.batch.member.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class MemberJobConfig {

    /**
     * 오늘을 기준으로 지난 1년간 로그인한 기록이 없는 회원을 휴먼 회원으로 변경하는 Step을 수행하는 Job 입니다
     *
     * @return 휴먼 회원으로 회원 정보를 변경하는 Job
     * */
    @Bean
    public Job modifyInactiveMemberJob(JobRepository jobRepository, Step modifyInactiveMemberStep){
        return new JobBuilder("modifyInactiveMemberJob",jobRepository)
                .start(modifyInactiveMemberStep)
                .build();

    }
}
