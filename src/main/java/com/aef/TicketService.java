/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketService.java 29 2012-02-09 04:23:40Z gna $
 * $Author: gna $ 
 */
package com.aef;

import java.util.List;

import com.aef.model.TicketAck;
import com.aef.model.TicketOrder;

/**
 * @author agile-development-group
 *
 */
public interface TicketService {
	
	/**
	 * @return the list of open tickets
	 */
	List<TicketOrder> getOpenTickets();	
	/**
	 * Record the ticket ack
	 * 
	 * @param ticketAck
	 * @return true if the ack is recorded
	 */
	boolean recordTicketAck(TicketAck ticketAck); 
}
