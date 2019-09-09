package com.ec.library.retrofit.messages.requests.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateMemberRequest {
    private final String name;
    private final String contacts;
}