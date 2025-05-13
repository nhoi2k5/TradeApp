package com.App.TradeApp.Repository;

import com.App.TradeApp.Model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CoinRepo extends JpaRepository<Coin, String> {
}
