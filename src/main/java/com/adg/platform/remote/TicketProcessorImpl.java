/*
 * Copyright (c) 2007-2010 Agile Development Group, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketProcessorImpl.java 61 2012-09-04 14:18:41Z matt $
 * $Author: matt $ 
 */
package com.adg.platform.remote;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.RedisList;

import com.aef.model.TicketBatch;
import com.google.common.util.concurrent.AbstractExecutionThreadService;

/**
 * @author sphetsarath
 * 
 */
public class TicketProcessorImpl extends AbstractExecutionThreadService implements TicketProcessor<String, TicketBatch> {

	private final StringRedisTemplate template;
	private final RedisList<String> ticketQueue;

	private long count;
	private long processedCount = 0;
	private long errorCount = 0;
	private long sleepInterval = 5;
	boolean processOncePerId = true;

	String ticketQueueName = null;
	boolean decodeEnable = true;
	Set<String> ignoreSymbols = new HashSet<String>();

	public TicketProcessorImpl(StringRedisTemplate template, String queueName, ExecutionPlatform platform,
			TicketBatchManagerService ticketBatchManagerService) {
		this.template = template;
		if (this.template != null) {
			ticketQueueName = queueName;
			this.ticketQueue = new DefaultRedisList<String>(ticketQueueName, this.template);
		} else {
			this.ticketQueue = null;
		}
		this.platform = platform;
		this.ticketBatchManagerService = ticketBatchManagerService;
		ignoreSymbols.add("SPY");
	}

	@Override
 protected void run() throws Exception {
 while (isRunning()) {
 try {
 if (platform.isReady()) {
 // THIS BLOCKS and WAITS for a new ticket

 String ticketString = ticketQueue.take();
 //ALGO_LOGGER.info(ticketQueueName + " TICKET: " + ticketString);
 count++;
 //TICKET_LOGGER.info(ticketQueueName + " TICKET: " + ticketString);
 if (decodeEnable) {
 TicketBatch ticketBatch = decode(ticketString);
 if (ticketBatch != null) {
 processTicketBatch(ticketBatch);
 processedTicketBatchMap.put(ticketBatch.getId(), ticketBatch);
 processedCount++;
 //sendAck(ticketBatch);
 } else {
 //TICKET_LOGGER.error(ticketQueueName + " processing null ticket");
 errorCount++;
 }
 }
 } else {
 //TICKET_LOGGER.info(String.format("Waiting for platform to become READY. Sleeping %d seconds...",
 sleepInterval));
 TimeUnit.SECONDS.sleep(sleepInterval);
 }
 } catch (Throwable t) {
 //TICKET_LOGGER.error(ExceptionUtils.getFullStackTrace(t));
 }
 }
 }

	/**
	 * //Decode list? //Iterator over each ticket //Create a map of symbol to
	 * ticketOrder //Check for registering new symbols w platform and stat
	 * service //doUnitRefresh - this initializes the state of the currently
	 * loaded measures //check available loaded measures, if not, check if it
	 * exists, if not, create/load //sendBatchToStaging //initTrajectories
	 * //beginTrading
	 */
	protected void processTicketBatch(TicketBatch ticketBatch) {
 //TICKET_LOGGER.info("TicketProcessor: processTicketBatch(): " +
 ticketBatch.getId());

 String platformName = platform.getName();
 boolean assumeBasketIfCount = true;
 int count = 0;
 //Screen for cancels

 try {
 TimeUnit.MILLISECONDS.sleep(500);
 } catch (InterruptedException e) {
 //TICKET_LOGGER.error(ExceptionUtils.getFullStackTrace(e));
 }
 }

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * @return the processedCount
	 */
	public long getProcessedCount() {
		return processedCount;
	}

	/**
	 * @param processedCount
	 *            the processedCount to set
	 */
	public void setProcessedCount(long processedCount) {
		this.processedCount = processedCount;
	}

	/**
	 * @return the errorCount
	 */
	public long getErrorCount() {
		return errorCount;
	}

	/**
	 * @param errorCount
	 *            the errorCount to set
	 */
	public void setErrorCount(long errorCount) {
		this.errorCount = errorCount;
	}

	public void testAddToRedisTicketQueue(String data) {
		this.ticketQueue.add(data);
	}

	public boolean isProcessOncePerId() {
		return processOncePerId;
	}

	public void setProcessOncePerId(boolean processOncePerId) {
		this.processOncePerId = processOncePerId;
	}

	public boolean isDecodeEnable() {
		return decodeEnable;
	}

	public void setDecodeEnable(boolean decodeEnable) {
		this.decodeEnable = decodeEnable;
	}

	public String getTicketQueueName() {
		return ticketQueueName;
	}

	public void setTicketQueueName(String ticketQueueName) {
		this.ticketQueueName = ticketQueueName;
	}

}
