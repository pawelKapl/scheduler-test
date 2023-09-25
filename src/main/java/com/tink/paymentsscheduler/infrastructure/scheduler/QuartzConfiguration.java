package com.tink.paymentsscheduler.infrastructure.scheduler;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean factory)
            throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.getListenerManager().addTriggerListener(new MisfireLogger());
        scheduler.start();
        return scheduler;
    }
}
