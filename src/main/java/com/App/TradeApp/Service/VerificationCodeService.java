package com.App.TradeApp.Service;

import com.App.TradeApp.Enum.verificationType;
import com.App.TradeApp.Model.User;
import com.App.TradeApp.Model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode sendVerificationCode(User user, verificationType verificationType);

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
