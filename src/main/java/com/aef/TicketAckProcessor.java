/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketAckProcessor.java 33 2012-02-23 00:38:18Z matt $
 * $Author: matt $ 
 */
package com.aef;

import com.google.common.util.concurrent.Service;

/**
 * @author agile-development-group
 *
 */
public interface TicketAckProcessor extends Service {

	int getTicketAckCount();
}
