package com.pva.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Goal is to test the Spring XML configuration
 * 
 * @author theo.briscoe@gmail.com, agile-development-group
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext-query-generator-server.xml" })
public class ConfigurationTest {

	@Autowired
	private TwapMessageListener twapMessageListener;

	@Autowired
	private RedisMessageListenerContainer redisMessageListenerContainer;

	@Autowired
	private ChannelTopic channelTopic;

	@Autowired
	private List<ChannelTopic> channelTopicList;

	@Test
	public void testTwapMessageListener() {
		assertNotNull(twapMessageListener);
	}

	@Test
	public void testChannelTopic() {
		assertNotNull(channelTopic);
		assertEquals("twap:key:topic", channelTopic.getTopic());

		assertNotNull(channelTopicList);
		assertEquals(1, channelTopicList.size());
		assertEquals("twap:key:topic", channelTopicList.get(0).getTopic());

		// TODO Topic name should be property
	}

	@Test
	public void testMessageContainer() {
		assertNotNull(redisMessageListenerContainer);

		String result = redisMessageListenerContainer.getConnectionFactory().getConnection().ping();
		assertEquals("PONG", result);
	}

}
