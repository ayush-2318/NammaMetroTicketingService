package org.example.service;

import com.razorpay.Utils;
import org.example.auth.RazorpayConfig;
import org.springframework.beans.factory.annotation.Autowired;


public class VerifyPaymentService {
    @Autowired
    private RazorpayConfig razorpayConfig;
    public boolean verifyPayment(String orderId, String paymentId, String razorpaySignature) {
        String payload = orderId + '|' + paymentId;
        try {
            return Utils.verifySignature(payload, razorpaySignature, razorpayConfig.getSecret());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
