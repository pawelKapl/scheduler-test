package com.tink.paymentsscheduler.schedulingEngine;

import com.tink.paymentsscheduler.adapters.PaymentPollService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.tink.paymentsscheduler.schedulingEngine.JobDataFields.IN_PROGRESS;
import static com.tink.paymentsscheduler.schedulingEngine.JobDataFields.STATUS;

@Component
@RequiredArgsConstructor
class PollPaymentJob implements Job {

    private final PaymentPollService service;

    @Override
    public void execute(JobExecutionContext context) {
        Date nextFireTime = context.getNextFireTime();
        String paymentId = context.getJobDetail().getKey().getName();
        String status = context.getJobDetail().getJobDataMap().getString(STATUS);
        boolean inProgress = context.getJobDetail().getJobDataMap().getBoolean(IN_PROGRESS);
        service.pollForPayment(paymentId, status, inProgress, nextFireTime);
    }
}
