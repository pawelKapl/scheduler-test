package com.tink.paymentsscheduler.api;

import com.tink.paymentsscheduler.schedulingEngine.PaymentScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping(path = "/poll")
public class TestApi {

    private final PaymentScheduler scheduler;

    public TestApi(PaymentScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostMapping("/{id}")
    ResponseEntity<Void> addPaymentPollJob(@PathVariable("id") String id) {
        scheduler.schedulePayment(id, new Date());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/status/{status}")
    ResponseEntity<Void> addPaymentPollJob(@PathVariable("id") String id, @PathVariable("status") String status) {
        scheduler.updatePaymentJobStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePaymentPollJob(@PathVariable("id") String id) {
        scheduler.removePayment(id);
        return ResponseEntity.ok().build();
    }
}
