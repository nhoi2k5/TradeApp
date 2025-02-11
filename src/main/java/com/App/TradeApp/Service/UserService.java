package com.App.TradeApp.Service;

import com.App.TradeApp.Enum.verificationType;
import com.App.TradeApp.Model.User;

public interface UserService {
     public User findUserProfileByJwt(String jwt);
     public User findUserByEmail(String email);
     public User findUserById(Long userId) throws Exception;

     public User enableTwoFactorAuthentication(verificationType verificationType, String sendTo, User user);

     User updatePassword(User user,String newPassword);
}
