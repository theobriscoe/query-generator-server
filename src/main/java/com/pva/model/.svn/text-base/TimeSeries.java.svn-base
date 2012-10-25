package com.pva.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TimeSeries {

	String symbol;
	List<Price> prices = new ArrayList<Price>();
	long timestamp = 0;
	
	// need for JSON
	public TimeSeries(){
		
	}
	
	public TimeSeries(List<Price> prices) {
		if (prices!=null) {
			if (prices.get(0)!=null) {
				timestamp = prices.get(0).getTimestamp();
				symbol = prices.get(0).getSymbol();
			}
		}
		//timestamp = System.currentTimeMillis();
		this.prices= prices;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public List<Price> getPrices() {
		return prices;
	}
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
	public String toString() {
		return new ToStringBuilder(this)
			.append("symbol", symbol)
			.append("prices", prices)
			.toString();
	}
}
