package com.App.TradeApp.Service;
import java.util.List;
import com.App.TradeApp.Model.User;
import com.App.TradeApp.Model.Withdrawal;

public interface WithdrawalService {

    Withdrawal requestyWithdrawal(Long amount, User user);

    Withdrawal proceedWithdrawal(Long withdrawalId, boolean accept) throws Exception;

    List<Withdrawal> getUsersWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequest();
}
