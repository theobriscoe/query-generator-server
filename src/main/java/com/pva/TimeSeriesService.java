/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketService.java 29 2012-02-09 04:23:40Z gna $
 * $Author: gna $ 
 */
package com.pva;

import java.util.List;

import com.pva.model.Price;

//import com.aef.model.TicketAck;
//import com.aef.model.TicketOrder;
//import com.pva.model.Price;
//import com.pva.model.TimeSeries;

/**
 * @author agile-development-group
 * @author Matthew Hutchison
 * 
 */
public interface TimeSeriesService {

	/**
	 * @return the list of open tickets
	 */
	List<Price> getPrices();
	// /**
	// * Record the ticket ack
	// *
	// * @param ticketAck
	// * @return true if the ack is recorded
	// */
	// boolean recordTicketAck(TicketAck ticketAck);
}
