package com.App.TradeApp.Service;

import com.App.TradeApp.Config.JwtProvider;
import com.App.TradeApp.Enum.TwoFactorAuth;
import com.App.TradeApp.Enum.verificationType;
import com.App.TradeApp.Model.User;
import com.App.TradeApp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepo userRepo;


    @Override
    public User findUserProfileByJwt(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepo.findByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        System.out.println("Searching for user with email: " + email);
        return userRepo.findByEmail(email);
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()){
            throw new Exception("user now found");
        }
        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(verificationType verificationType,String sendTo,User user) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnable(true);
        twoFactorAuth.setVerifyType(verificationType);

        user.setTwoFactorAuth(twoFactorAuth);

        return userRepo.save(user);
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepo.save(user);
    }
}
