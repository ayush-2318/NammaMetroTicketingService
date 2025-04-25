package org.example.controller;

import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentGatewayController {
    @Autowired
    private OrderService orderService;
    @PostMapping("payment/create-order")
    public ResponseEntity<?> createOrder(@RequestParam int amount){
        try {
            String orderResponse=orderService.createPaymentOrder(amount);
            if(orderResponse!=null){
                return ResponseEntity.ok(orderResponse);
            }else{
                return ResponseEntity.badRequest().body("Failed to create razorpay order .");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while creating the order.");
        }
    }
}
