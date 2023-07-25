package com.yaloostore.batch.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InactiveMemberResponse {

    private Long memberId;
    private boolean isInactiveMember;
    private String emailAddress;



}