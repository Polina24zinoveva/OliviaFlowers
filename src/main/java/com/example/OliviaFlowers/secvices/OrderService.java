package com.example.OliviaFlowers.secvices;

import com.example.OliviaFlowers.models.Bouquet;
import com.example.OliviaFlowers.models.User;
import com.example.OliviaFlowers.repositories.BouquetRepository;
import com.example.OliviaFlowers.repositories.OrderRepository;
import com.example.OliviaFlowers.models.Order;
import com.example.OliviaFlowers.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final UserRepository userRepository;



    public OrderService(OrderRepository orderRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public boolean saveOrder(Principal principal, Order order){
        order.setUser(getUserByPrincipal(principal));
        order.setActive((long)1);
        orderRepository.save(order);
        return true;

    }

    public User getUserByPrincipal(Principal principal){
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public boolean SetInactive(Order order){
        order.setActive((long)0);
        return true;
    }
    public Order HaveActiveOrderByPrincipal(Principal principal){
        return orderRepository.findByUserAndActive(getUserByPrincipal(principal), (long)1);
    }

    public Order getOrderByID(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> ListOrderActivity2(Principal principal){
        return orderRepository.findAllByUserAndActive(getUserByPrincipal(principal),(long)2); //заказы к доставке
    }

    public List<Order> ListOrderActivity0(Principal principal){ //полностью закрытые заказы
        return orderRepository.findAllByUserAndActive(getUserByPrincipal(principal),(long)0);
    }

    public List<Order> ListAllOrdersToDeliver(){
        return orderRepository.findAllByActive((long)2);
    }

    @Transactional
    public void CheckoutOrder(Principal principal, Long typepostcard, String textpostcard, Boolean toDeliver, String address, LocalDateTime paydate){
        try{
            Order order = HaveActiveOrderByPrincipal(principal);
            order.setAddress(address);
            order.setToDeliver(toDeliver);
            order.setTypePostcard(typepostcard);
            order.setDatePayment(paydate);
            order.setTextPostcard(textpostcard);
            order.setActive((long)2);
            orderRepository.save(order);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Transactional
    public void DeliverOrder(Order order, LocalDateTime delierydate){
        try{order.setActive((long)3);
            order.setDateTimeDelivery(delierydate);//3 активность - доставлен
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void CancelOrder(Order order){
        try{
            order.setActive((long)4); //4 активность - отменён
        }catch(Exception e){
            e.printStackTrace();
        }
    }





    public List<Order> ListOrders() {
        return orderRepository.findAll();
    }

    public List<Order> ListOrdersActive(Principal principal){
        return orderRepository.findAllByUserAndActive(getUserByPrincipal(principal), (long)1);
    }

    public List<Order> ListOrdersInactive(Principal principal){
        return orderRepository.findAllByUserAndActive(getUserByPrincipal(principal), (long)0);
    }







}
