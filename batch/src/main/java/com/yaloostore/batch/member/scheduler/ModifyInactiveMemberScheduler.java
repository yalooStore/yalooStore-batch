package com.yaloostore.batch.member.scheduler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Component
public class ModifyInactiveMemberScheduler {

    private final Job modifyInactiveMemberJob;
    private final JobLauncher jobLauncher;
    private final static LocalDate today = LocalDate.now();
    private final static String EVERY_DAY_AT_MIDNIGHT = "0 0 0 * * *";

    /**
     * 매일밤 자정을 기준으로 1년간 접속한 이력이 없는 회원을 휴먼회원화 시키는 modifyInactiveMemberJob을 실행합니다
     */
    @Scheduled(cron = EVERY_DAY_AT_MIDNIGHT, zone = "Asia/Seoul")
    public void scheduleModifyInactiveMember(){
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("today", String.valueOf(today))
                .toJobParameters();

        try{
            jobLauncher.run(modifyInactiveMemberJob, jobParameters);
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        } catch (JobParametersInvalidException e) {
            throw new RuntimeException(e);
        } catch (JobRestartException e) {
            throw new RuntimeException(e);
        }
    }

}
