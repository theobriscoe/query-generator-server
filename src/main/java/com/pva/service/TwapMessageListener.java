package com.pva.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * An example of a simple message listner
 * 
 * @author theo.briscoe@gmail.com
 * 
 */
public class TwapMessageListener implements MessageListener {

	public void onMessage(final Message message, final byte[] pattern) {
		// 1. Process the message
		System.out.println("Message received: Twap Key is" + message.toString());
	}
}