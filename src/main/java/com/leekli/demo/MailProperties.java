package com.leekli.demo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(locations = "classpath:mail.properties", ignoreUnknownFields = false, prefix = "mail")
public class MailProperties {

	public static class Smtp {

		private boolean auth;
		private boolean starttlsEnable;

		// ... getters and setters
	}

	@NotBlank
	private String host;
	private int port;  
	private String from;
	private String username;
	private String password;
	@NotNull
	private Smtp smtp;

	// ... getters and setters

}