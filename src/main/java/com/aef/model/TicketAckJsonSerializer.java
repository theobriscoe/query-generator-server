/*
 * Copyright (c) 2012 Agile Development Group, LLC. - Automated Execution Framework, LLC.
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketAckJsonSerializer.java 29 2012-02-09 04:23:40Z gna $
 * $Author: gna $ 
 */
package com.aef.model;

import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;

/**
 * @author agile-development-group
 * 
 */
public class TicketAckJsonSerializer extends JacksonJsonRedisSerializer<TicketAck> {

	public TicketAckJsonSerializer() {
		super(TicketAck.class);
	}

}
