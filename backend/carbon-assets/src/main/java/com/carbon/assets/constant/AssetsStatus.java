package com.carbon.assets.constant;

/**
 * 资产状态枚举
 */
public enum AssetsStatus {

    /**
     * 待审批
     */
    WAIT("待审批"),

    /**
     * 已审批
     */
    APPROVED("已审批"),

    /**
     * 已拒绝
     */
    REJECTED("已拒绝");

    private final String status;

    AssetsStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}

