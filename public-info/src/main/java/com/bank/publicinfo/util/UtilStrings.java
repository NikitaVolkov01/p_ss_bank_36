package com.bank.publicinfo.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UtilStrings {

    @Value("${spring.datasource.username}")
    private String dbUsername;
}
