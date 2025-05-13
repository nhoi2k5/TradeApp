package com.App.TradeApp.Repository;

import com.App.TradeApp.Model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WalletRepo extends JpaRepository<Wallet, Long> {


    Wallet findByUserId(Long id);
}
