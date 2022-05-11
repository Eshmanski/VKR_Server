package com.rest.vkr.models;

import com.rest.vkr.exeptions.ForbiddenException;

public class PostBody<T>{
    private String token;
    private T content;

    public String getToken() {
        return token;
    }

    public T getContent() {
        return content;
    }

    public void checkToken(String token) {
        if (!this.token.equals(token)) {
            throw new ForbiddenException();
        }
    }
}
