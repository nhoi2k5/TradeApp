package com.App.TradeApp.Service;

import com.App.TradeApp.Model.Coin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface    CoinService {


    List<Coin> getCoinList(int page);

    String getMarketChart(String coinId, int days);

    Coin findById(String coinId);

    String searchCoin(String keyword);

    String getTop50();

    String getTreadingCoins();

    String getCoinDetails(String coinId);

}
