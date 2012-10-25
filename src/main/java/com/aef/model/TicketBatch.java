/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketBatch.java 17 2012-02-04 07:51:26Z gna $
 * $Author: gna $ 
 */
package com.aef.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TicketBatch {

	private String id;
	private long timestamp;
	private List<TicketOrder> orders = new ArrayList <TicketOrder>();
	
	// need for JSON
	public TicketBatch(){
		
	}
	
	public TicketBatch(List<TicketOrder> orders) {
		if (orders!=null) {
			if (orders.get(0)!=null) {
				id = orders.get(0).getBasketId();
			}
		}
		timestamp = System.currentTimeMillis();
		this.orders= orders;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public List<TicketOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<TicketOrder> orders) {
		this.orders = orders;
	}
	
	public String toString() {
		return new ToStringBuilder(this)
			.append("id", id)
		    .append("timestamp", timestamp)
		    .append("timestampAsDate", new Date(timestamp))
		    .append("orders", orders)
		    .toString();
	}
}
