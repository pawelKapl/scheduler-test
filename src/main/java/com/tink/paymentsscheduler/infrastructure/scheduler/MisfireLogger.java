package com.tink.paymentsscheduler.infrastructure.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.listeners.TriggerListenerSupport;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
//@Component
public class MisfireLogger extends TriggerListenerSupport implements TriggerListener {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public String getName() {
        return "hello";
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.info("Misfired!" + counter.incrementAndGet());
        super.triggerMisfired(trigger);
    }
}
