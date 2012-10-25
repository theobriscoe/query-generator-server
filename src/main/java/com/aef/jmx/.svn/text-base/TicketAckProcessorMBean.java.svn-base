package com.aef.jmx;


import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.aef.TicketAckProcessor;

@ManagedResource
public class TicketAckProcessorMBean {

	TicketAckProcessor service;

	public TicketAckProcessor getService() {
		return service;
	}

	public void setService(TicketAckProcessor service) {
		this.service = service;
	}
	
	@ManagedAttribute
	public int getTicketAckCount() {
		return service.getTicketAckCount();
	}
}
