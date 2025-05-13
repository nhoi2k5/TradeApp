package com.App.TradeApp.Service;

import com.App.TradeApp.Enum.OrderType;
import com.App.TradeApp.Model.Order;
import com.App.TradeApp.Model.User;
import com.App.TradeApp.Model.Wallet;
import com.App.TradeApp.Repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class WalletServiceImp implements WalletService{

    @Autowired
    private WalletRepo walletRepo;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepo.findByUserId(user.getId());
        if(wallet == null){
            wallet = new Wallet();
            wallet.setUser(user);
        }
        return wallet;
    }


    @Override
    public Wallet addBalance(Wallet wallet, Long money) {
        BigDecimal balance = wallet.getBalance();
        BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));

        wallet.setBalance(newBalance);

        return walletRepo.save(wallet);
    }

    @Override
    public Wallet findWallet(Long id) throws Exception {
        Optional<Wallet> wallet = walletRepo.findById(id);
        if(wallet.isPresent()){
            return wallet.get();
        }
        throw new Exception("not found");

    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);
        if(senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0){
            throw new Exception("minus account");
        }
        BigDecimal balance = senderWallet.getBalance().
                subtract(BigDecimal.valueOf(amount));
        senderWallet.setBalance(balance);
        walletRepo.save(senderWallet);

        BigDecimal newBalance = receiverWallet.getBalance().add(BigDecimal.valueOf(amount));

        receiverWallet.setBalance(newBalance);
        walletRepo.save(receiverWallet);
        return senderWallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);

        if(order.getOrderType().equals(OrderType.BUY)){
            BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
            if(newBalance.compareTo(order.getPrice()) < 0){
                throw new Exception("broke");
            }
            wallet.setBalance(newBalance);
        }
        else {
            BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }
        walletRepo.save(wallet);
        return wallet;
    }
}
