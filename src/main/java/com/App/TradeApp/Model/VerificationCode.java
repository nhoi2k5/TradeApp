package com.App.TradeApp.Model;


import com.App.TradeApp.Enum.verificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class VerificationCode {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }


    private String otp;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne
    @Getter
    @Setter
    private User user;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Getter
    @Setter
    private String email;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Getter
    @Setter
    private String mobile;


    public verificationType getVerificationtype() {
        return verificationtype;
    }

    public void setVerificationtype(verificationType verificationtype) {
        this.verificationtype = verificationtype;
    }

    private verificationType verificationtype;


}
