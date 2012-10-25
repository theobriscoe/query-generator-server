/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: queryGeneratorServerDevSpringockitoTest.java 25 2012-02-04 23:46:52Z gna $
 * $Author: gna $ 
 */
package com.pva;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pva.TimeSeriesService;
import com.pva.model.Price;
import com.pva.model.TimeSeries;
import com.aef.TestTools;

/**
 * This is the same as {@link queryGeneratorServerDevTest} but uses Springockito to inject
 * the ticket service
 * 
 * @author agile-development-group
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/applicationContext-query-generator-server-springockito.xml"})
public class QueryGeneratorServerDevSpringockitoTest {

	private static final Log LOGGER = LogFactory.getLog(QueryGeneratorServerDevSpringockitoTest.class);
	@Autowired
	private QueryGeneratorImpl queryGenerator;
	
	@Autowired
	private TimeSeriesService timeSeriesService;
	
	@Autowired
	private String ticketQueueName;
	
	// let's use real POJOs for domain and model objects
	private List<Price> prices;
	
	// NOTE: All test classes will use the ticket-generator-server.properties from test/resoures
	//      and the log4j.properties
	@Before
	public void setUp() {		
		queryGenerator.setSleepInterval(2);
		queryGenerator.setTimeSeriesService(timeSeriesService);
	
		LOGGER.info("testQueueName: "+ ticketQueueName);
		
		prices = TestTools.createTestPrices();
	}
	
	@Test
	public void doTest() throws Exception {
		//-- Given
		
		//-- When
		// let's send process one batch
		when(timeSeriesService.getPrices()).thenReturn(prices);
		
		queryGenerator.startAndWait();	
				
		// give the system some time to do something
		Thread.sleep(2 * 1000);
		
		queryGenerator.stopAndWait();
		
		//-- THEN
		//-- CHECK YOUR RESULTS
		// now check to see if it did anything
		assertEquals(1, queryGenerator.getTimeSeriesSendCount());
		
		// ask to fake tickQueue if the order got there
		// for redis this would be serialized
		TimeSeries ticketBatch = queryGenerator.getTimeSeriesQueue().poll();		
		TestTools.verifyTestPrices(ticketBatch);
	}
}
