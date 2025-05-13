package com.App.TradeApp.Controller;


import com.App.TradeApp.Config.JwtProvider;
import com.App.TradeApp.Model.TwoFactorOTP;
import com.App.TradeApp.Model.User;
import com.App.TradeApp.Repository.UserRepo;
import com.App.TradeApp.Response.AuthResponse;
import com.App.TradeApp.Service.CustomeUserDetailService;
import com.App.TradeApp.Service.EmailService;
import com.App.TradeApp.Service.TwoFactorOtpService;
import com.App.TradeApp.Service.TwoFactorOtpServiceImp;
import com.App.TradeApp.utils.otpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomeUserDetailService customeUserDetailService;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) {
        User isEmailExist = userRepo.findByEmail(user.getEmail());

        if (isEmailExist != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email Already Exists");
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());

        User savedUser = userRepo.save(newUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        String jwt = JwtProvider.generateToken(auth);
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) {
        String email = user.getEmail();  // Authenticate using email
        String password = user.getPassword();
        Authentication auth = authenticate(email, password); // Authenticate user credentials

        // Fetch the authenticated user from the database
        User AuthUser = userRepo.findByEmail(email);

        // Check if 2FA is enabled
        if (AuthUser.getTwoFactorAuth().isEnable()) {
            String otp = otpUtils.generateOTP(); // Generate OTP

            // Handle existing OTPs
            TwoFactorOTP oldTwoFactorOTP = twoFactorOtpService.findByUser(AuthUser.getId());
            if (oldTwoFactorOTP != null) {
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
            }

            // Create a new OTP and associate it with the user
            TwoFactorOTP newTwoFactorOTP = twoFactorOtpService.createTwoFactorOtp(AuthUser, otp, null); // No JWT yet

            // Send OTP to the user's email
            emailService.sendVerificationOtpEmail("luubao279s@gmail.com", otp);

            // Prepare response
            AuthResponse res = new AuthResponse();
            res.setMessage("Success");
            res.setTwoFactorAuthEnabled(true);
            res.setSession(newTwoFactorOTP.getId()); // Return session ID for OTP verification

            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        // Generate JWT if 2FA is not enabled
        String jwt = JwtProvider.generateToken(auth);

        // Prepare response
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customeUserDetailService.loadUserByUsername(userName);
        if(userDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if(!password.equals(userDetails.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password Incorrect");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySigninOTP(@PathVariable String otp, @RequestParam String id) {
        TwoFactorOTP twoFactorOTP = twoFactorOtpService.findById(id);

        if (twoFactorOTP == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid session ID");
        }

        if (twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP, otp)) {
            // Generate final JWT after successful OTP verification
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    twoFactorOTP.getUser(), null, twoFactorOTP.getUser().getAuthorities()
            );
            String jwt = JwtProvider.generateToken(auth);

            AuthResponse res = new AuthResponse();
            res.setMessage("Success");
            res.setTwoFactorAuthEnabled(true);
            res.setJwt(jwt);

            // Delete OTP after successful verification
            twoFactorOtpService.deleteTwoFactorOtp(twoFactorOTP);

            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid OTP");
    }

}