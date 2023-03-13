package com.upc.gessi.loganalytics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class LogAnalyticsApplication extends SpringBootServletInitializer {

	private static final Logger logger =
			LogManager.getLogger(LogAnalyticsApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LogAnalyticsApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(LogAnalyticsApplication.class, args);
	}

}
