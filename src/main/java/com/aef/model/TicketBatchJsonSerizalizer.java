/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketBatchJsonSerizalizer.java 19 2012-02-04 08:13:31Z gna $
 * $Author: gna $ 
 */
package com.aef.model;

import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;

/**
 * @author agile-development-group
 * 
 */
public class TicketBatchJsonSerizalizer extends JacksonJsonRedisSerializer<TicketBatch> {

	public TicketBatchJsonSerizalizer() {
		super(TicketBatch.class);
	}

}
