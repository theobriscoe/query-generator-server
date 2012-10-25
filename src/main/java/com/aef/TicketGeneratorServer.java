/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketGeneratorServer.java 29 2012-02-09 04:23:40Z gna $
 * $Author: gna $ 
 */
package com.aef;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is the main class loads up the spring context.
 * 
 * @author agile-development-group
 *
 */
public class TicketGeneratorServer {

	private ClassPathXmlApplicationContext context;

	protected TicketGeneratorServer() {
		context = new ClassPathXmlApplicationContext("spring/applicationContext-ticket-generator-server.xml");
		context.getEnvironment().setActiveProfiles("prod");
		context.refresh();
	}

	public void start() {
		TicketGenerator ticketGenerator = context.getBean(TicketGenerator.class);
		ticketGenerator.start();
		// you might want to start the act processor here too
	}

	public static void createAndStartInstance() {
		new TicketGeneratorServer().start();
	}
	
	public static void main(String args[]) {
		TicketGeneratorServer.createAndStartInstance();
	}
}
