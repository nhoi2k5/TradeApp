package com.App.TradeApp.Controller;


import com.App.TradeApp.Config.JwtProvider;
import com.App.TradeApp.Enum.verificationType;
import com.App.TradeApp.Model.User;
import com.App.TradeApp.Model.VerificationCode;
import com.App.TradeApp.Service.EmailService;
import com.App.TradeApp.Service.UserService;
import com.App.TradeApp.Service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization")String jwt){
        User user = userService.findUserByEmail(jwt);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/test-email")
    public ResponseEntity<String> testEmail() {
        emailService.sendVerificationOtpEmail("luubao279s@gmail.com", "123456");
        return ResponseEntity.ok("Test email sent.");
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization")String jwt, @PathVariable("verificationType") String verificationType)throws Exception{
        String token = jwt.substring(7);

        // Get the email from the token
        String email = JwtProvider.getEmailFromToken(token);

        // Retrieve the user by email
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
        if(verificationCode==null){
            verificationCode = verificationCodeService.sendVerificationCode(user, com.App.TradeApp.Enum.verificationType.valueOf(verificationType));
        }

        if(com.App.TradeApp.Enum.verificationType.valueOf(verificationType) == com.App.TradeApp.Enum.verificationType.EMAIL){
            emailService.sendVerificationOtpEmail("luubao279s@gmail.com",verificationCode.getOtp());
        }
        return new ResponseEntity<String>("success for verification", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@PathVariable String otp,@RequestHeader("Authorization")String jwt) throws Exception {
        System.out.println("Raw JWT: " + jwt);

        String token = jwt.replace("Bearer ", "").trim();
        System.out.println("Parsed Token: " + token);
        User user = userService.findUserProfileByJwt(token);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo = verificationCode.getVerificationtype().equals(verificationType.EMAIL)?
                verificationCode.getEmail():verificationCode.getMobile();
        boolean isVerified = verificationCode.getOtp().equals(otp);
        if(isVerified){
            User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationtype(),sendTo,user);
            verificationCodeService.deleteVerificationCodeById(verificationCode );
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);

        }
        throw new Exception("Wrong otp");
    }
 }
