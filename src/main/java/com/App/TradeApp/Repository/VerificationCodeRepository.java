package com.App.TradeApp.Repository;

import com.App.TradeApp.Model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    VerificationCode findByUserId(Long userId); // Corrected return type
}
