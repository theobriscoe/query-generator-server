/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketServiceImpl.java 29 2012-02-09 04:23:40Z gna $
 * $Author: gna $ 
 */
package com.pva;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.aef.model.TicketAck;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.pva.model.Price;

/**
 * The default configuration is to go to the DB using a sqlMapClient.
 * 
 * @author agile-development-group
 * 
 */
@Component
public class TimeSeriesServiceImpl implements TimeSeriesService {
	private static final Log LOGGER = LogFactory.getLog(TimeSeriesServiceImpl.class);

	private SqlMapClient sqlMapClient;
	private String listName = "Price.getPrices";
	private String updateAckName = "TicketOrder.updateAck";

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	@SuppressWarnings("unchecked")
	public List<Price> getPrices() {
		List<Price> openTickets = null;
		try {
			openTickets = sqlMapClient.queryForList(listName);
			LOGGER.info(openTickets);
		} catch (SQLException e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}
		return openTickets;
	}

	/**
	 * @param listName
	 *            the listName to set
	 */
	public void setListName(String listName) {
		this.listName = listName;
	}

	public boolean recordTicketAck(TicketAck ticketAck) {
		boolean recorded = false;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderId", ticketAck.getOrderId());
			sqlMapClient.queryForList(updateAckName, map);
			LOGGER.info(ticketAck);
			recorded = true;
		} catch (SQLException e) {
			LOGGER.error(ExceptionUtils.getFullStackTrace(e));
		}
		return recorded;
	}

	/**
	 * @param updateAckName
	 *            the updateAckName to set
	 */
	public void setUpdateAckName(String updateAckName) {
		this.updateAckName = updateAckName;
	}

}
