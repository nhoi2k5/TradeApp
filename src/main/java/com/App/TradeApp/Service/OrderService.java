package com.App.TradeApp.Service;

import com.App.TradeApp.Enum.OrderType;
import com.App.TradeApp.Model.Coin;
import com.App.TradeApp.Model.Order;
import com.App.TradeApp.Model.OrderItem;
import com.App.TradeApp.Model.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(long id);

    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user);
}
