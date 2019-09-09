package com.ec.library.retrofit.messages.requests.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InsertMemberRequest {
    private final String id;
    private final String name;
    private final String contacts;
}