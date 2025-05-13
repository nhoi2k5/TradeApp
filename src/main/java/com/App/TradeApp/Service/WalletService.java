package com.App.TradeApp.Service;

import com.App.TradeApp.Model.Order;
import com.App.TradeApp.Model.User;
import com.App.TradeApp.Model.Wallet;



public interface WalletService {
    Wallet getUserWallet (User user);
    Wallet addBalance(Wallet wallet,Long money);
    Wallet findWallet(Long id) throws Exception;
    Wallet walletToWalletTransfer(User sender,Wallet receiverWallet, Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;

}
