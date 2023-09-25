package com.tink.paymentsscheduler.schedulingEngine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.tink.paymentsscheduler.schedulingEngine.JobDataFields.IN_PROGRESS;
import static com.tink.paymentsscheduler.schedulingEngine.JobDataFields.STATUS;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Slf4j
@Component
@RequiredArgsConstructor
class QuartzPaymentScheduler implements PaymentScheduler {

    private static final String JOB_DESCRIPTION = "Test Payment Poll Job";

    private final Scheduler scheduler;

    @Override
    public void schedulePayment(String paymentId, Date startDate) {
        JobDataMap jobData = newJobData();
        JobDetail job = JobBuilder.newJob()
                .ofType(PollPaymentJob.class)
                .storeDurably()
                .withIdentity(paymentId)
                .withDescription(JOB_DESCRIPTION)
                .setJobData(jobData)
                .build();

        try {
            scheduler.scheduleJob(job, newTrigger(startDate));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePaymentJobStatus(String paymentId, String status) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey(paymentId));
            jobDetail.getJobDataMap().put(STATUS, status);
            jobDetail.getJobDataMap().put(IN_PROGRESS, "true");
            scheduler.addJob(jobDetail, true);

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removePayment(String paymentId) {
        try {
            scheduler.deleteJob(jobKey(paymentId)); // trigger should be removed too?
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        log.info(String.format("Removed job for payment with id: %s!", paymentId));
    }

    private static JobDataMap newJobData() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(STATUS, "NEW");
        jobDataMap.put(IN_PROGRESS, "false");
        return jobDataMap;
    }

    // check if we can trigger not all at once
    private static SimpleTrigger newTrigger(Date startDate) {
        return TriggerBuilder.newTrigger()
                .withDescription("Common test trigger for payment polls")
                .startAt(startDate)
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(240))
                .build();
    }
}
