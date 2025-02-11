package com.App.TradeApp.Service;

import com.App.TradeApp.Enum.verificationType;
import com.App.TradeApp.Model.User;
import com.App.TradeApp.Model.VerificationCode;
import com.App.TradeApp.Repository.VerificationCodeRepository;
import com.App.TradeApp.utils.otpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;


    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);
        if(verificationCode.isPresent()){
            return verificationCode.get();
        }
        throw new Exception("verificationCode not found");
    }

    @Override
    public VerificationCode sendVerificationCode(User user, verificationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        String a = otpUtils.generateOTP();
        verificationCode1.setOtp(a);
        verificationCode1.setVerificationtype(verificationType);
        verificationCode1.setUser(user);
        return verificationCodeRepository.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return (VerificationCode) verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationcode){
        verificationCodeRepository.delete(verificationcode);

    }
}
