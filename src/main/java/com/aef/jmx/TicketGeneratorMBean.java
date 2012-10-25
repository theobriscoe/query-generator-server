package com.aef.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.aef.TicketGenerator;
@ManagedResource
public class TicketGeneratorMBean {

	TicketGenerator service;

	public TicketGenerator getService() {
		return service;
	}

	public void setService(TicketGenerator service) {
		this.service = service;
	}
	
	@ManagedAttribute
	public int getSleepInterval() {
		return service.getSleepInterval();				
	}
	@ManagedAttribute
	public void setSleepInterval(int sleepInterval) {
		service.setSleepInterval(sleepInterval);
	}
	@ManagedAttribute
	public long getTicketBatchSendCount() {
		return service.getTicketBatchSendCount();
	}
	
	@ManagedAttribute
	public int getPollCount() {
		return service.getPollCount();
	}
	
	@ManagedAttribute
	public int getGenerateCount() {
		return service.getGenerateCount();
	}

}
