package org.jbltd.password.common;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class IRunnable implements Runnable {

    private Runnable _runnable;
    private AtomicInteger _runCount = new AtomicInteger();
    private volatile ScheduledFuture<?> self;
    private final int _maxRuns;

    public IRunnable(Runnable _runnable, int _maxRuns) {

	this._runnable = _runnable;
	this._maxRuns = _maxRuns;

    }

    public void runXTimes(ScheduledExecutorService executor, long delay, long period, TimeUnit unit) {

	self = executor.scheduleAtFixedRate(this, delay, period, unit);

    }

    @Override
    public void run() {

	if (_maxRuns != -1) {

	    if (_runCount.incrementAndGet() - 1 == _maxRuns) {
		self.cancel(false);
	    } else {
		this._runnable.run();
	    }
	} else {
	    this._runnable.run();
	}

    }

}
