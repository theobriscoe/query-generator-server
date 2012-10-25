/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketGeneratorServerIntDbEmbeddedTest.java 19 2012-02-04 08:13:31Z gna $
 * $Author: gna $ 
 */
package com.pva;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aef.TestTools;
import com.aef.model.TicketBatch;
import com.pva.model.TimeSeries;

/**
 * @author agile-development-group
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/applicationContext-query-generator-server.xml"})
@ActiveProfiles("int-db-embedded")
public class QueryGeneratorServerIntDbEmbeddedTest {

	private static final Log LOGGER = LogFactory.getLog(QueryGeneratorServerIntDbEmbeddedTest.class);
	
	@Autowired
	@Qualifier("queryGenerator")
	private QueryGeneratorImpl queryGenerator;

//  no longer need b/c it's set in the context	
//	@Autowired
//	private TicketService ticketService;
	
	@Autowired
	@Qualifier("testRedisTemplate")
	private StringRedisTemplate testRedisTemplate;
	private ListOperations<String, String> listOps;

	@Autowired
	@Qualifier("timeSeriesQueueName")	
	private String testQueueName;

	// use for decoding result
	private ObjectMapper objectMapper;
	
	// NOTE: All test classes will use the ticket-generator-server.properties from test/resoures
	//      and the log4j.properties
	@Before
	public void setUp() {
		// set the style here so that we can see what's going on
		ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
		
		queryGenerator.setSleepInterval(2);
	
		LOGGER.info("Using QUEUE: " + testQueueName);
		
		// bound the list operations, so we can use this to check what we put into 
		// redis
		listOps = testRedisTemplate.opsForList();
		
		// to turn JSON back to objects
		objectMapper = new ObjectMapper();
	}
	
	@Test
	public void doTest() throws Exception {
		// clean up the queue to test
		listOps.getOperations().delete(testQueueName);
		
		queryGenerator.startAndWait();	
		
		// give the system some time to do something
		Thread.sleep(2 * 1000);
		
		queryGenerator.stopAndWait();
		
		//-- CHECK YOUR RESULTS
		// now check to see if it did anything
		assertEquals(1, queryGenerator.getTimeSeriesSendCount());

		// pop the the ticket batch off
		String ticketBatchAsJson = listOps.leftPop(testQueueName);
		
		assertNotNull(ticketBatchAsJson);
		
		LOGGER.info(ticketBatchAsJson);
		
		// verify your json here by converting back to an object
		// and checking the values
		TimeSeries ticketBatch = (TimeSeries) objectMapper.readValue(ticketBatchAsJson, TimeSeries.class);
		
		TestTools.verifyTestPrices(ticketBatch);
	}
}
