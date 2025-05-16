package com.App.TradeApp.Service;

import com.App.TradeApp.Enum.OrderType;
import com.App.TradeApp.Model.Coin;
import com.App.TradeApp.Model.Order;
import com.App.TradeApp.Model.OrderItem;
import com.App.TradeApp.Model.User;
import com.App.TradeApp.Repository.OrderItemRepo;
import com.App.TradeApp.Repository.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();

        Order order  = new Order();
        order.setUser(user);
        order.setOrderType(orderType);
        order.setOrderItem(orderItem);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimestamp(LocalDate.now());
        return orderRepo.save(order);
    }

    @Override
    public Order getOrderById(long id) {
        return orderRepo.findById(id).orElseThrow(()->new RuntimeException("Order Not Found"));
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepo.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin,double quantity,double buyPrice, double sellPrice){
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepo.save(orderItem);
    }


    @Transactional
    public Order buyAsset(Coin coin,double quantity,User user) throws Exception{
        if(quantity < 0){
            throw new Exception("Quantity is less than 0");
        }

        double buyPrice = coin.getCurrentPrice();

        OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,0);

        Order order = createOrder(user,orderItem,OrderType.BUY);

        orderItem.setOrder(order);

        walletService.payOrderPayment(order,user);

        return order;


    }

    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
        return null;
    }
}
