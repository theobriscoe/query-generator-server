/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketAckProcessorIntDbEmbeddedTest.java 33 2012-02-23 00:38:18Z matt $
 * $Author: matt $ 
 */
package com.aef;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
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

import com.aef.model.TicketAck;

/**
 * @author agile-development-group
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/applicationContext-ticket-generator-server.xml"})
@ActiveProfiles("int-db-embedded")
public class TicketAckProcessorIntDbEmbeddedTest {

	private static final Log LOGGER = LogFactory.getLog(TicketGeneratorServerIntNoDbTest.class);
	
	@Autowired
	@Qualifier("ticketAckProcessor")
	private TicketAckProcessorImpl ticketAckProcessor;

//  no longer need b/c it's set in the context	
//	@Autowired
//	private TicketService ticketService;
	
	@Autowired
	@Qualifier("testRedisTemplate")
	private StringRedisTemplate testRedisTemplate;
	private ListOperations<String, String> listOps;

	@Autowired
	@Qualifier("ticketAckQueueName")	
	private String testAckQueueName;

	// use for decoding result
	private ObjectMapper objectMapper;
	private JsonFactory jsonFactory;
	
	private TicketAck ticketAck;
	
	// NOTE: All test classes will use the ticket-generator-server.properties from test/resoures
	//      and the log4j.properties
	@Before
	public void setUp() {
		// set the style here so that we can see what's going on
		ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
		
	
		LOGGER.info("Using QUEUE: " + testAckQueueName);
		
		// bound the list operations, so we can use this to check what we put into 
		// redis
		listOps = testRedisTemplate.opsForList();
		
		// to turn JSON back to objects
		objectMapper = new ObjectMapper();
		
		jsonFactory = new JsonFactory();
		
		// create an ticket ack object
		ticketAck = new TicketAck();
		ticketAck.setOrderId("OrderId1");
	}
	
	protected String toJson(Object pojo) throws JsonMappingException, JsonGenerationException, IOException {
		StringWriter sw = new StringWriter();
		JsonGenerator jg = jsonFactory.createJsonGenerator(sw);
		objectMapper.writeValue(jg, pojo);
		return sw.toString();
	}
	
	@Test
	public void doTest() throws Exception {
		// clean up the queue to test
		listOps.getOperations().delete(testAckQueueName);
		
		// encode as JSON
		String tickAckAsJsonString = toJson(ticketAck);
		
		LOGGER.info("TICKET ACK: " + tickAckAsJsonString);		
		// push it onto the queue
		listOps.rightPush(testAckQueueName, tickAckAsJsonString);
				
		ticketAckProcessor.startAndWait();	
		
		// give the system some time to do something
		Thread.sleep(2 * 1000);
		
		ticketAckProcessor.stopAndWait();
		
		// CHECK THE RESULTS
		assertEquals(1, ticketAckProcessor.getTicketAckCount());
	}
}
