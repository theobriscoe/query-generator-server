/*
 * Copyright (c) 2012 Automated Execution Framework, LLC. 
 * See the LICENSE file for redistribution and use restrictions.
 * 
 * $Id: TestTools.java 43 2012-07-24 20:35:02Z matt $
 * $Author: matt $ 
 */
package com.aef;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.aef.model.TicketBatch;
import com.aef.model.TicketOrder;
import com.pva.model.Price;
import com.pva.model.TimeSeries;

/**
 * @author agile-development-group
 * 
 */
public final class TestTools {

	private TestTools() {

	}

	public static TicketOrder createTicketOrder(String basketId, String orderId, String symbol, String side,
			long quantity, String clearingFirm) {
		TicketOrder ticket = new TicketOrder();
		ticket.setBasketId(basketId);
		ticket.setOrderId(orderId);
		ticket.setSymbol(symbol);
		ticket.setSide(side);
		ticket.setQuantity(quantity);
		ticket.setClearingFirm(clearingFirm);
		return ticket;
	}

	public static List<TicketOrder> createTestTicketOrders() {
		List<TicketOrder> ticketList = new ArrayList<TicketOrder>();
		TicketOrder ticket1 = createTicketOrder("Basket1", "Order1", "QQQQ", "BUY", 100, "GSEC");
		TicketOrder ticket2 = createTicketOrder("Basket1", "Order2", "GOOG", "SELL", 200, "LEK");
		TicketOrder ticket3 = createTicketOrder("Basket1", "Order3", "FB", "BUY", 300, "LIME");

		ticketList.add(ticket1);
		ticketList.add(ticket2);
		ticketList.add(ticket3);

		return ticketList;
	}

	public static Price createPrice(String symbol, long timestamp, double value) {
		Price price = new Price();
		price.setSymbol(symbol);
		price.setTimestamp(timestamp);
		price.setPrice(value);
		return price;
	}

	public static List<Price> createTestPrices() {
		List<Price> priceList = new ArrayList<Price>();
		Price ticket1 = createPrice("SPY", 1926100, 133.74);
		priceList.add(ticket1);
		return priceList;
	}

	public static void verifyTestPrices(TimeSeries timeSeries) {
		assertNotNull(timeSeries);
		assertEquals("SPY", timeSeries.getSymbol());

		verifyTestPrices(timeSeries.getPrices());
	}

	public static void verifyTestPrices(List<Price> prices) {
		assertEquals(3, prices.size());

		Price price = prices.get(0);
		assertEquals("SPY", price.getSymbol());

		price = prices.get(1);
		assertEquals("SPY", price.getSymbol());

	}

	public static void verifyTestTicketBatch(TicketBatch ticketBatch) {
		assertNotNull(ticketBatch);
		assertEquals("Basket1", ticketBatch.getId());

		verifyTestTicketOrders(ticketBatch.getOrders());
	}

	public static void verifyTestTicketOrders(List<TicketOrder> ticketOrders) {
		assertEquals(3, ticketOrders.size());

		TicketOrder ticketOrder = ticketOrders.get(0);
		assertEquals("Order1", ticketOrder.getOrderId());
		assertEquals("QQQQ", ticketOrder.getSymbol());
		assertEquals("BUY", ticketOrder.getSide());
		assertEquals(100L, ticketOrder.getQuantity());
		assertEquals("GSEC", ticketOrder.getClearingFirm());

		ticketOrder = ticketOrders.get(1);
		assertEquals("Order2", ticketOrder.getOrderId());
		assertEquals("GOOG", ticketOrder.getSymbol());
		assertEquals("SELL", ticketOrder.getSide());
		assertEquals(200L, ticketOrder.getQuantity());
		assertEquals("LEK", ticketOrder.getClearingFirm());

		ticketOrder = ticketOrders.get(2);
		assertEquals("Order3", ticketOrder.getOrderId());
		assertEquals("FB", ticketOrder.getSymbol());
		assertEquals("BUY", ticketOrder.getSide());
		assertEquals(300L, ticketOrder.getQuantity());
		assertEquals("LIME", ticketOrder.getClearingFirm());
	}
}
