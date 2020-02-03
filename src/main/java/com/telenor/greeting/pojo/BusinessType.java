package com.telenor.greeting.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BusinessType {

    SMALL ("small"), BIG("big");

    @Getter
    private String value;

}