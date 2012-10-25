/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketGenerator.java 33 2012-02-23 00:38:18Z matt $
 * $Author: matt $ 
 */
package com.pva;

import com.google.common.util.concurrent.Service;
import com.pva.model.TimeSeries;

/**
 * @author agile-development-group
 * @author mhutchison@parallax.com
 *
 */
public interface QueryGenerator extends Service {
	/**
	 * @param ticketBatch the ticket back to be added to the ticket queue
	 */
	void addToTimeSeriesQueue(TimeSeries timeSeries);
// Not sure that this should be in the interface	
//	/**
//	 * @return the ticketQueue
//	 */
//	Queue<TicketBatch> getTicketQueue();
	/**
	 * @return the number of ticket batches sent
	 */
	long getTimeSeriesSendCount();
	int getSleepInterval();
	void setSleepInterval(int sleepInterval);
	
	int getPollCount();
	int getGenerateCount();
}
