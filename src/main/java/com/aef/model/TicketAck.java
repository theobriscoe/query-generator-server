/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketAck.java 29 2012-02-09 04:23:40Z gna $
 * $Author: gna $ 
 */
package com.aef.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author sphetsarath
 *
 */
public class TicketAck {
	
	private String orderId;
	private long timestamp = System.currentTimeMillis();
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toString() {
		return new ToStringBuilder(this)
			.append("orderId", orderId)
		    .append("timestamp", timestamp)
		    .append("timestampAsDate", new Date(timestamp))
			.toString();
	}
}
