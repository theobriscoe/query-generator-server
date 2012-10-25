/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketGeneratorImpl.java 34 2012-02-23 00:40:40Z matt $
 * $Author: matt $ 
 */
package com.pva;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.pva.model.Price;
import com.pva.model.TimeSeries;

/**
 * This is the implementation that encodes that:
 * <ul>
 * <li>Polls the database
 * <li>Creates a ticket
 * <li>Pushes to a redis queue
 * <li>Updates the status of the database
 * </ul>
 * 
 * @author agile-development-group
 * @author mhutchison@parallax.com
 * 
 */
@Component
public class QueryGeneratorImpl extends AbstractExecutionThreadService implements QueryGenerator {
	private static final Log LOGGER = LogFactory.getLog(QueryGeneratorImpl.class);

	private Queue<TimeSeries> timeSeriesQueue;
	private TimeSeriesService timeSeriesService;

	private final AtomicLong timeSeriesSendCount = new AtomicLong();
	private int sleepInterval = 1;
	private int pollCount = 0;
	private int generateCount = 0;

	protected void run() throws Exception {
		while (isRunning()) {
			List<Price> prices = timeSeriesService.getPrices();

			for (Price price : prices) {
				price.setSymbol(symbolToLekFormat(price.getSymbol()));
			}

			if (prices != null && !prices.isEmpty()) {
				// I want to add the tickets all the tickets at once
				TimeSeries timeSeries = new TimeSeries(prices);
				addToTimeSeriesQueue(timeSeries);
				generateCount++;
			}
			pollCount++;
			LOGGER.info(String.format("Sleeping for %d seconds....", sleepInterval));
			TimeUnit.SECONDS.sleep(sleepInterval);
		}
	}

	public void addToTimeSeriesQueue(TimeSeries timeSeries) {
		long startTime = 0L;
		timeSeriesSendCount.incrementAndGet();
		// we're gonna let Redis do the serialization..
		LOGGER.debug(String.format("Adding to queue: %s", timeSeries));
		startTime = System.nanoTime();
		timeSeriesQueue.add(timeSeries);
		LOGGER.debug(String.format("Time to add to queue: %s",
				DurationFormatUtils.formatDurationHMS((System.nanoTime() - startTime) / 1000000)));
	}

	public Queue<TimeSeries> getTimeSeriesQueue() {
		return timeSeriesQueue;
	}

	public void setTimeSeriesQueue(Queue<TimeSeries> timeSeriesQueue) {
		this.timeSeriesQueue = timeSeriesQueue;
	}

	public TimeSeriesService getTimeSeriesService() {
		return timeSeriesService;
	}

	public void setTimeSeriesService(TimeSeriesService timeSeriesService) {
		this.timeSeriesService = timeSeriesService;
	}

	public long getTimeSeriesSendCount() {
		return timeSeriesSendCount.get();
	}

	public void setSleepInterval(int sleepInterval) {
		this.sleepInterval = sleepInterval;
	}

	public int getSleepInterval() {
		return sleepInterval;
	}

	public int getPollCount() {
		return pollCount;
	}

	public void setPollCount(int pollCount) {
		this.pollCount = pollCount;
	}

	public int getGenerateCount() {
		return generateCount;
	}

	public void setGenerateCount(int generateCount) {
		this.generateCount = generateCount;
	}

	public String symbolToLekFormat(String symbol) {
		return symbol;
	}
}
