package com.example.JMS.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JMSRoutes extends RouteBuilder {

	static final Logger Log = LoggerFactory.getLogger(JMSRoutes.class);
	
	@Override
	public void configure() throws Exception {
		from("{{inbound.endpoint}}")
		.transacted()
		.log(LoggingLevel.INFO, Log, "Message Received")
		.process(new Processor() {
			
			//Displays where the exchange of inputs and outputs occur
			@Override
			public void process(Exchange exchange) throws Exception {
				log.info("Exchanged: {}", exchange);
			}
		})
		//Sends the message 'x' amount of times, but in this case 5 times since in application.properties
		.loop()
		.simple("{{outbound.loop.count}}")
		.to("{{outbound.endpoint}}")
		.log(LoggingLevel.INFO, log, "Message Sent. Iteration: ${property.CamelLoopIndex}")
		.end();

	}

}
