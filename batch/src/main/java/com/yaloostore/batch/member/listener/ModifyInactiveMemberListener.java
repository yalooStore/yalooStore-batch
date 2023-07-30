package com.yaloostore.batch.member.listener;


import com.yaloostore.batch.member.dto.MemberIdResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

import java.util.List;


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
    public void onWriteError(Exception e, Chunk chunk){
        log.info("==== modifyInactiveMemberStep | ItemWrite error message log {}", e.getMessage());
    }
}
