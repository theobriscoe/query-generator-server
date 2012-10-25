/*
 * Copyright (c) 2012 Agile Development Group, LLC. - Automated Execution Framework, LLC.
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: StoredProcedureStub.java 29 2012-02-09 04:23:40Z gna $
 * $Author: gna $ 
 */
package com.pva.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author agile-development-group
 * 
 * Used for H2
 */
public class StoredProcedureStub {

	private static final Log LOGGER = LogFactory.getLog(StoredProcedureStub.class);
	
	public static ResultSet query(Connection conn) throws SQLException {
		return conn.createStatement().executeQuery("SELECT * FROM test_table;");
	}
	
	public static ResultSet updateAck(Connection conn, String orderId) throws SQLException {
		LOGGER.info("ORDER ID: " + orderId);
		return null;
	}
}
