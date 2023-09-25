package com.tink.paymentsscheduler.schedulingEngine;

import java.util.Date;

public interface PaymentScheduler {

    void schedulePayment(String paymentId, Date startDate);

    void updatePaymentJobStatus(String paymentId, String status);

    void removePayment(String paymentId);
}
