package com.tink.paymentsscheduler.adapters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class PaymentPollService {

    // Here we would send kafka event instead.
    public void pollForPayment(String paymentId, String status, boolean inProgress, Date nextFireDate) {
        log.info(String.format("Polling for payment with id: %s, current status: %s, in progress: %s, next fire time: %s", paymentId, status, inProgress, nextFireDate));
    }
}
