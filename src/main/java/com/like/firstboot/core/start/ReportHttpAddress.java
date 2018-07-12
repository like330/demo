package com.like.firstboot.core.start;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ReportHttpAddress implements ApplicationListener<ServletWebServerInitializedEvent> {
	Integer port;
	String applicationNmae;

	@Override
	public void onApplicationEvent(ServletWebServerInitializedEvent event) {
		port = event.getWebServer().getPort();
		applicationNmae = event.getApplicationContext().getApplicationName();
		try {
			System.out.println(
					"登录地址：http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/" + applicationNmae);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
