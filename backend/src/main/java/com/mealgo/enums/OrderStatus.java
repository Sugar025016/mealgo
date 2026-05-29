package com.mealgo.enums;

public enum OrderStatus {

    PENDING(1, "待確認"),
    ACCEPTED(2, "已接單"),
    COOKING(3, "製作中"),
    READY_FOR_PICKUP(4, "待取餐"),
    ON_THE_WAY(5, "配送中"),
    COMPLETED(6, "已完成"),

    CANCELLED(7, "使用者取消"),

    STORE_NOT_ACCEPT(8, "店家未接單"),
    STORE_REFUSED(9, "店家拒接單");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public boolean canUpdateNote() {
        return this == PENDING
                || this == ACCEPTED
                || this == COOKING;
    }

    public boolean canCancel() {
        return this == PENDING
                || this == ACCEPTED
                || this == COOKING
                || this == READY_FOR_PICKUP
                || this == ON_THE_WAY;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static OrderStatus fromCode(Integer code) {
        for (OrderStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + code);
    }

    public boolean isFinished() {
        return this == COMPLETED
                || this == CANCELLED
                || this == STORE_NOT_ACCEPT
                || this == STORE_REFUSED;
    }

    public boolean canUpdateStatus(OrderStatus nextStatus) {

        return switch (this) {
            case PENDING -> nextStatus == ACCEPTED
                    || nextStatus == CANCELLED
                    || nextStatus == STORE_REFUSED;

            case ACCEPTED -> nextStatus == COOKING;

            case COOKING -> nextStatus == READY_FOR_PICKUP
                    || nextStatus == ON_THE_WAY;

            case ON_THE_WAY -> nextStatus == COMPLETED;

            default -> false;
        };
    }

}