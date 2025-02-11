package com.App.TradeApp.Model;

import com.App.TradeApp.Enum.TwoFactorAuth;
import com.App.TradeApp.Enum.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data

public class User {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String email;

    private String username;


    private String password;

    public USER_ROLE getUser_role() {
        return user_role;
    }

    public void setUser_role(USER_ROLE user_role) {
        this.user_role = user_role;
    }

    private USER_ROLE user_role = USER_ROLE.CUSTOMER;

    public com.App.TradeApp.Enum.TwoFactorAuth getTwoFactorAuth() {
        return TwoFactorAuth;
    }

    public void setTwoFactorAuth(com.App.TradeApp.Enum.TwoFactorAuth twoFactorAuth) {
        TwoFactorAuth = twoFactorAuth;
    }

    @Embedded
    private TwoFactorAuth TwoFactorAuth = new TwoFactorAuth();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
