package com.yaloostore.batch.config;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 배치 환경을 자동으로 설정하고 테스트 환경에서 따로 사용하기 위한 배치 테스트 환경 설정입니다.
 * */

@EnableBatchProcessing //배치환경 자동설정
@EnableAutoConfiguration
@Configuration
public class TestBatchConfig {


    /**
     * 스프링 배치 테스트 유틸인 JobLauncherTestUtils을 빈으로 등록합니다.
     * 해당 유틸을 이용한 jobParameter를 사용한 job등의 실행이 이루어집니다.
     * 해당 유틸은 각 테스트 코드에서 @Autowired를 이용한 호출,사용을 진행합니다.
     *
     * @return JobLauncherTestUtils
     * */
    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils() {
        return new JobLauncherTestUtils();
    }
}
