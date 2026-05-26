package com.mealgo.enums;

public enum PayMethod {

    CASH(1, "現金"),
    CREDIT_CARD(2, "信用卡"),
    LINE_PAY(3, "LINE Pay");

    private final int code;
    private final String description;

    PayMethod(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PayMethod fromCode(int code) {
        for (PayMethod method : values()) {
            if (method.code == code) {
                return method;
            }
        }
        throw new IllegalArgumentException("Unknown pay method: " + code);
    }
}