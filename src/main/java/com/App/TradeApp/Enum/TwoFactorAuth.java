package com.App.TradeApp.Enum;

import lombok.Data;

@Data
public class TwoFactorAuth {
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    private boolean enable = false;

    public verificationType getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(verificationType verifyType) {
        this.verifyType = verifyType;
    }

    private verificationType verifyType;
}
