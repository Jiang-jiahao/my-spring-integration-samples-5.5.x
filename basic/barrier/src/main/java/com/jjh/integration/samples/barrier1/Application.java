/*
 * Copyright 2015-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jjh.integration.samples.barrier1;

import java.util.Collections;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Gary Russell
 * @since 4.2
 */
@SpringBootApplication
@ImportResource("/META-INF/spring/integration/server-context.xml")
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext server = SpringApplication.run(Application.class, args);

		runClient(args);
		server.close();
	}

	static void runClient(String[] args) {
		SpringApplication application = new SpringApplicationBuilder()
				.web(WebApplicationType.NONE)
				.bannerMode(Mode.OFF)
				.application();

		application.setSources(Collections.singleton("/META-INF/spring/integration/client-context.xml"));

		ConfigurableApplicationContext client = application.run(args);

		RequestGateway requestGateway = client.getBean("requestGateway", RequestGateway.class);
		String request = "A,B,C";
		System.out.println("\n\n++++++++++++ Sending: " + request + " ++++++++++++\n");
		String reply = requestGateway.echo(request);
		System.out.println("\n\n++++++++++++ Replied with: " + reply + " ++++++++++++\n");
		client.close();
	}


}
