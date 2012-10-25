package com.pva.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pva.model.Twap;
import com.pva.service.TwapService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext-query-generator-server.xml" })
public class TwapServiceTest {

	@Autowired
	private TwapService service;

	@Test
	public void testConfiguration() {
		assertNotNull(service);
		assertNotNull(service.redisTemplate);
		assertEquals("PONG", service.redisTemplate.getConnectionFactory().getConnection().ping());
	}

	@Test
	public void testBuildKeyCurrentDate() {
		Twap twap = new Twap();
		String key = TwapService.generateKey(twap);
		assertNotNull(key);

		twap = new Twap();
		twap.setSymbol("INTC");
		twap.setTwap1(0d);
		twap.setTwap5(0d);
		twap.setTwap30(0d);
		twap.setTwap60(0d);
		twap.setTimestamp(new Date().getTime());
		key = TwapService.generateKey(twap);

		assertNotNull(key);
	}

	@Test
	public void testBuildKey() {
		Twap twap = new Twap();
		String key = TwapService.generateKey(twap);
		assertNotNull(key);

		twap = new Twap();
		twap.setSymbol("INTC");
		twap.setTwap1(0d);
		twap.setTwap5(0d);
		twap.setTwap30(0d);
		twap.setTwap60(0d);

		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, 10, 1, 3, 30, 0);

		twap.setTimestamp(calendar.getTime().getTime());
		key = TwapService.generateKey(twap);

		long time = calendar.getTimeInMillis();
		assertEquals("TWAP:INTC:" + time, key);
	}

	@Test
	public void testCreate1() {
		Twap twap = new Twap("INTC", 0d, 0d, 0d, 0d, new Date().getTime());
		String key = service.create(twap);

		Twap twap2 = service.read(key);
		assertEquals(twap, twap2);
	}

	@Test
	public void testCreate2() {
		Twap twap = new Twap("INTC", 21.45d, 0d, 0d, 0d, new Date().getTime());
		String key = service.create(twap);

		Twap twap2 = service.read(key);
		assertEquals(twap, twap2);
	}

	@Test
	public void testDelete0() {
		Twap twap = new Twap("INTC", 21.45d, 0d, 0d, 0d, new Date().getTime());

		String key = service.create(twap);
		service.delete(twap);

		Twap twap2 = service.read(key);
		assertEquals("", twap2.symbol);
	}

	@Test
	public void testPublish() {
		Twap twap = new Twap("INTC", 21.45d, 0d, 0d, 0d, new Date().getTime());

		service.publishKey(twap);
		// TODO Assert message received
	}

	@Test
	public void testCreateAndPublish() {
		Twap twap = new Twap("INTC", 21.45d, 0d, 0d, 0d, new Date().getTime());
		String key = service.create(twap);

		service.publishKey(twap);

		Twap twap2 = service.read(key);
		assertEquals(twap, twap2);
		// TODO Assert message received
	}

}
