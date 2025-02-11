package com.App.TradeApp.Repository;

import com.App.TradeApp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo  extends JpaRepository<User, Long> {

    User findByEmail(String username);
}
