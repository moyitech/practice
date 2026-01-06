package com.carbon.assets.constant;

/**
 * 资产交易状态枚举
 */
public enum AssetsTradeStatus {

    /**
     * 挂牌
     */
    OFFER("挂牌"),

    /**
     * 交易中
     */
    TRADING("交易中"),

    /**
     * 已成交
     */
    TRADED("已成交");

    private final String status;

    AssetsTradeStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}

