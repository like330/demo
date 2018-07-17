package com.like.firstboot.core.netty.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "netty")
public class NettyConfig {
	
	private int port;
	
	public void setPort(int port) {
		this.port = port;
	}

	public	int getPort() {
		return port;
	}
	
}
