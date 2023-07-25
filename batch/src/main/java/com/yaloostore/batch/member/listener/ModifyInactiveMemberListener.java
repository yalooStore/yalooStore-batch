package com.yaloostore.batch.member.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.stereotype.Component;


/**
 * 휴먼회원과 관련된 chunk(itemReader, itemWriter)에서 발생한 에러 메시지를 로그로 작성하는 리스너 입니다.
 * */
@Component
@Slf4j
public class ModifyInactiveMemberListener {

    @OnReadError
    public void onReadError(Exception e){
        log.info("==== modifyInactiveMemberStep | ItemReader error message log {}", e.getMessage());
    }
    @OnWriteError
    public void onWriteError(Exception e){
        log.info("==== modifyInactiveMemberStep | ItemWrite error message log {}", e.getMessage());
    }
}
