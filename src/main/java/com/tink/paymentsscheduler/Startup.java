package com.tink.paymentsscheduler;

import com.tink.paymentsscheduler.schedulingEngine.PaymentScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class Startup implements CommandLineRunner {

    private final PaymentScheduler paymentScheduler;
    private final AtomicInteger counter = new AtomicInteger(0);


    @Override
    public void run(String... args) {
        for (int i = 0; i < 30000; i++) {
            String uuid = UUID.randomUUID().toString();
            paymentScheduler.schedulePayment(uuid, pickRandomDate());
        }
    }

    private Date pickRandomDate() {
        LocalDateTime localDateTime = LocalDateTime.now().plus(Duration.of(counter.addAndGet(50), ChronoUnit.MILLIS));
        return java.sql.Timestamp.valueOf(localDateTime);
    }
}
