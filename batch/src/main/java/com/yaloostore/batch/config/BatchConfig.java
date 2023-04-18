package com.yaloostore.batch.config;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * batch와 관련된 설정을 해주는 클래스입니다.
 * */

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRegistry jobRegistry;


    /**
     * BeanPostProcessor을 사용해서 jobRegistry를 자동 저장하기 위한 빈등록 메소드
     * */

    @Bean
    public BeanPostProcessor beanPostProcessor(){

        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor =
                new JobRegistryBeanPostProcessor();

        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);


        return jobRegistryBeanPostProcessor;
    }
}
