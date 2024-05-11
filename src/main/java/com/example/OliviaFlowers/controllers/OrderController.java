package com.example.OliviaFlowers.controllers;

import com.example.OliviaFlowers.models.Order;
import com.example.OliviaFlowers.secvices.BouquetService;
import com.example.OliviaFlowers.secvices.OrderHasBouquetService;
import com.example.OliviaFlowers.secvices.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.security.Principal;

@Controller
public class OrderController {
    @Autowired
    public final OrderService orderService;
    @Autowired
    public final OrderHasBouquetService orderHasBouquetService;
    @Autowired
    private BouquetService bouquetService;

    public OrderController(OrderService orderService, OrderHasBouquetService orderHasBouquetService) {
        this.orderService = orderService;
        this.orderHasBouquetService = orderHasBouquetService;
    }

//    @GetMapping("/order")
//    public String order(){
//        return "order";
//    }

    @GetMapping("/order/add")
    public String addOrder(Order order, Principal principal) throws IOException {
        orderService.saveOrder(principal, order);
        return "redirect:/order";
    }

    @GetMapping("/getorders")
    public String getOrders(Principal principal, Model model){
        model.addAttribute("orders", orderService.ListOrders());
        model.addAttribute("user", orderService.getUserByPrincipal(principal));
        return "order";
    }

    @GetMapping("/order")
    public String getBouqordersAc(Principal principal,Model model){

        model.addAttribute("acBouquets", orderHasBouquetService.getbouquetsByOrder(orderService.HaveActiveOrderByPrincipal(principal)));

        model.addAttribute("inacOrders", orderService.ListOrdersInactive(principal));
        model.addAttribute("acAmounts", orderHasBouquetService.getAmountsByOrder(orderService.HaveActiveOrderByPrincipal(principal)));
        model.addAttribute("acOrder", orderService.HaveActiveOrderByPrincipal(principal));
        return "order";
    }










}
