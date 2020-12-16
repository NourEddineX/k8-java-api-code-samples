package com.glasswal.api.configuration;

import org.glasswall.solutions.client.RebuildClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtherConfiguration {

	@Value("${rebuild.api.url}")
	private String rebuildApiURL;

	@Bean
	public RebuildClient rebuildClient() {
		return new RebuildClient(rebuildApiURL);
	}
}
