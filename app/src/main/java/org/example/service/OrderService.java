package org.example.service;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.annotation.PostConstruct;
import org.example.auth.RazorpayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

@Service
public class OrderService {

    private RazorpayClient razorpayClient;

    @Value("${payment.gateway.secretKey}")
    private String secretKey;
    @Value("${payment.gateway.publishableKey}")
    private String publishableKey;

    /*
    public OrderService(RazorpayConfig razorpayConfig) throws RazorpayException {
        try {
            //System.out.println(secretKey);
            //this.razorpayClient=new RazorpayClient(secretKey,publishableKey);
            this.razorpayConfig=razorpayConfig;
            System.out.println(razorpayConfig.getSecret());
            this.razorpayClient=new RazorpayClient(razorpayConfig.getSecret(),razorpayConfig.getKey());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

     */

    @PostConstruct
    public void init() throws RazorpayException{
        try {
            System.out.println(secretKey);
            razorpayClient=new RazorpayClient(publishableKey,secretKey);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public String createPaymentOrder(int amount){
        JSONObject options=new JSONObject();
        options.put("amount",amount*100);
        options.put("currency","INR");
        options.put("receipt","order_rcpt_id");
        options.put("payment_capture",1);

        try {
            return razorpayClient.orders.create(options).toString();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }



}
