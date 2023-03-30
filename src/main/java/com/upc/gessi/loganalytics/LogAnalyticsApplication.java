package com.upc.gessi.loganalytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LogAnalyticsApplication extends SpringBootServletInitializer {

	//private static final Logger logger =
			//LogManager.getLogger(LogAnalyticsApplication.class);

	private static final Logger logger =
			LoggerFactory.getLogger("ActionLogger");

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LogAnalyticsApplication.class);
	}

	static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		context = SpringApplication.run(LogAnalyticsApplication.class, args);
	}

}
