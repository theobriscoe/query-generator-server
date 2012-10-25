/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TicketOrder.java 17 2012-02-04 07:51:26Z gna $
 * $Author: gna $ 
 */
package com.aef.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author agile-development-group
 *
 */
public class TicketOrder {

	String basketId;
	String orderId;
	String symbol;
	long quantity;
	String clearingFirm;
	String side;

	public String getSymbol() {
		return symbol;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getClearingFirm() {
		return clearingFirm;
	}

	public void setClearingFirm(String clearingFirm) {
		this.clearingFirm = clearingFirm;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}
	
	public String getBasketId() {
		return basketId;
	}

	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}

	public String toString() {
//		return "Ticket: " + symbol + ": " + side + ": " + quantity + ": " + clearingFirm;
		return new ToStringBuilder(this)
			.append("symbol", symbol)
			.append("side", side)
			.append("quantity", quantity)
			.append("clearingFirm", clearingFirm)
			.toString();
	}
}
