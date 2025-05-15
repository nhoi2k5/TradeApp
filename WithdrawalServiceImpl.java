package com.App.TradeApp.Service;

import com.App.TradeApp.Enum.WithdrawalStatus;
import com.App.TradeApp.Model.User;
import com.App.TradeApp.Model.Withdrawal;
import com.App.TradeApp.Repository.WithdrawalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class WithdrawalServiceImpl implements WithdrawalService{

    @Autowired
    private WithdrawalRepo withdrawalRepo;


    @Override
    public Withdrawal requestyWithdrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setDate(LocalDateTime.now());
        withdrawal.setStatus(WithdrawalStatus.PENDING);
        withdrawal.setUser(user);

        return withdrawalRepo.save(withdrawal);
    }

    @Override
    public Withdrawal proceedWithdrawal(Long withdrawalId, boolean accept) throws Exception {
        Optional<Withdrawal> withdrawal = withdrawalRepo.findById(withdrawalId);

        if(withdrawal.isEmpty()){
            throw new Exception("not found");
        }
        Withdrawal withdrawal1 = withdrawal.get();
        withdrawal1.setDate(LocalDateTime.now());
        if (accept){
            withdrawal1.setStatus(WithdrawalStatus.SUCCESS);
        }
        else {
            withdrawal1.setStatus(WithdrawalStatus.PENDING);
        }
        return withdrawalRepo.save(withdrawal1);
    }

    @Override
    public List<Withdrawal> getUsersWithdrawalHistory(User user) {
        return withdrawalRepo.findByUserId(user.getId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRepo.findAll();
    }
}
