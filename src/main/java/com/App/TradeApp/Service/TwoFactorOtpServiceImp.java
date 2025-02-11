package com.App.TradeApp.Service;

import com.App.TradeApp.Model.TwoFactorOTP;
import com.App.TradeApp.Model.User;
import com.App.TradeApp.Repository.TwoFactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class TwoFactorOtpServiceImp  implements TwoFactorOtpService {


    @Autowired
    private TwoFactorRepository twoFactorRepository;


    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();


        TwoFactorOTP twoFactorOtp = new TwoFactorOTP();
        twoFactorOtp.setOtp(otp);
        twoFactorOtp.setJwt(jwt);
        twoFactorOtp.setId(id);
        twoFactorOtp.setUser(user);
        return twoFactorRepository.save(twoFactorOtp);

    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {

        return twoFactorRepository.findByUserId(userId);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP> twoFactorOtp = twoFactorRepository.findById(id);
        return twoFactorOtp.orElse(null);
    }

    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp) {
        return twoFactorOtp.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp) {
        twoFactorRepository.delete(twoFactorOtp);

    }
}
