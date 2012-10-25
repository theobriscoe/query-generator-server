/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketGeneratorServerDevTest.java 19 2012-02-04 08:13:31Z gna $
 * $Author: gna $ 
 */
package com.pva;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aef.TestTools;
import com.aef.model.TicketBatch;
import com.aef.model.TicketOrder;
import com.pva.model.Price;
import com.pva.model.TimeSeries;

/**
 * @author agile-development-group
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/applicationContext-query-generator-server.xml"})
@ActiveProfiles("dev")
public class QueryGeneratorServerDevTest {

	@Autowired
	@Qualifier("queryGenerator")
	private QueryGeneratorImpl ticketGenerator;
	
	private TimeSeriesService timeSeriesService;
	// let's use real POJOs for domain and model objects
	private List<Price> ticketList;
	
	// NOTE: All test classes will use the query-generator-server.properties from test/resoures
	//      and the log4j.properties
	@Before
	public void setUp() {
		timeSeriesService = mock(TimeSeriesService.class);
		
		ticketGenerator.setSleepInterval(2);
		ticketGenerator.setTimeSeriesService(timeSeriesService);
		
		ticketList = TestTools.createTestPrices();
	}
	
	@Test
	public void doTest() throws Exception {
		// let's send process one batch
		when(timeSeriesService.getPrices()).thenReturn(ticketList);
		
		ticketGenerator.startAndWait();	
				
		// give the system some time to do something
		Thread.sleep(2 * 1000);
		
		ticketGenerator.stopAndWait();
		
		//-- CHECK YOUR RESULTS
		// now check to see if it did anything
		assertEquals(1, ticketGenerator.getTimeSeriesSendCount());
		
		// ask to fake tickQueue if the order got there
		// for redis this would be serialized
		TimeSeries ticketBatch = ticketGenerator.getTimeSeriesQueue().poll();
		
		TestTools.verifyTestPrices(ticketBatch);
	}
}
