/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketGeneratorImplTest.java 18 2012-02-04 07:57:21Z gna $
 * $Author: gna $ 
 */
package com.pva;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.aef.TestTools;
import com.pva.TimeSeriesService;
import com.pva.model.Price;
import com.pva.model.TimeSeries;

/**
 * For this test you do not need to use Spring.
 * 
 * Use the interface to mock other objects that are not ticket generator.
 * 
 * It's easier just to use the model or domain objects b/c they are just POJOs.
 * 
 * @author agile-development-group
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class QueryGeneratorImplTest {

	private QueryGeneratorImpl ticketGenerator;

	private List<Price> ticketList;
	private TimeSeries ticketBatch;

	// just mock out the ticket service so we won't need Spring.
	// we can use annotations for mocks
	@Mock
	private TimeSeriesService ticketService;
	
	@Mock
	private Queue<TimeSeries> timeSeriesQueue;

	@Before
	public void setUp() {
		ticketGenerator = new QueryGeneratorImpl();

		// ticketService was created by the annotation
		ticketGenerator.setTimeSeriesService(ticketService);
		// ticketQueue was created by the annotation
		ticketGenerator.setTimeSeriesQueue(timeSeriesQueue);
		ticketList = TestTools.createTestPrices();
		ticketBatch = new TimeSeries(ticketList);
	}

	@Test
	public void testAddToQueue() {
		// this is the method under test
		ticketGenerator.addToTimeSeriesQueue(ticketBatch);
		
		// you expect the queue to do an add here so you mock that
		// operation, so verify that it was called. do this for
		// void method returns.
		verify(timeSeriesQueue).add(ticketBatch);
		
		//-- CHECK YOUR RESULTS
		assertEquals(1, ticketGenerator.getTimeSeriesSendCount());
		
		// verify that you got what you expected
		TestTools.verifyTestPrices(ticketBatch);
	}
}
