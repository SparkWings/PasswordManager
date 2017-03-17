package org.jbltd.password.common;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class IRunnable implements Runnable {

    private Runnable runnable;
    private AtomicInteger runCount = new AtomicInteger();
    private volatile ScheduledFuture<?> self;
    private final int maxRuns;

    public IRunnable(Runnable runnable, int maxRuns) {

	this.runnable = runnable;
	this.maxRuns = maxRuns;

    }

    public void runXTimes(ScheduledExecutorService executor, long delay, long period, TimeUnit unit) {

	self = executor.scheduleAtFixedRate(this, delay, period, unit);

    }

    @Override
    public void run() {

	if (maxRuns != -1) {

	    if (runCount.incrementAndGet() - 1 == maxRuns) {
		self.cancel(false);
	    } else {
		this.runnable.run();
	    }
	} else {
	    this.runnable.run();
	}

    }

}
