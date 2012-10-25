package com.pva.model;

import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;

public class DoubleJsonSerializer extends JacksonJsonRedisSerializer<Double> {

	public DoubleJsonSerializer() {
		super(Double.class);
	}
}
