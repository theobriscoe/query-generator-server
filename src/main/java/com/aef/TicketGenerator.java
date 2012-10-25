/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketGenerator.java 33 2012-02-23 00:38:18Z matt $
 * $Author: matt $ 
 */
package com.aef;

import com.aef.model.TicketBatch;
import com.google.common.util.concurrent.Service;

/**
 * @author agile-development-group
 *
 */
public interface TicketGenerator extends Service {
	/**
	 * @param ticketBatch the ticket back to be added to the ticket queue
	 */
	void addToTicketQueue(TicketBatch ticketBatch);
// Not sure that this should be in the interface	
//	/**
//	 * @return the ticketQueue
//	 */
//	Queue<TicketBatch> getTicketQueue();
	/**
	 * @return the number of ticket batches sent
	 */
	long getTicketBatchSendCount();
	int getSleepInterval();
	void setSleepInterval(int sleepInterval);
	
	int getPollCount();
	int getGenerateCount();
}
