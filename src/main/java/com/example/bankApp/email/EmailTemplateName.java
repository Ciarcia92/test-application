package com.example.bankApp.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"),
    CREDIT_CARD_REQUEST("credit_card_request")
    ;


    private final String name;
    EmailTemplateName(String name) {
        this.name = name;
    }
}