package com.bank.publicinfo.util;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message; //сообщение об ошибке
    private long timestamp; //в какое время произошла оишбка

    public ErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
