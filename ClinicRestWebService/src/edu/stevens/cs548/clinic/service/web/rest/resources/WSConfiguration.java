package edu.stevens.cs548.clinic.service.web.rest.resources;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/resources")
public class WSConfiguration extends ResourceConfig {

	public WSConfiguration() {
		packages("edu.stevens.cs548.clinic.service.web.rest.resources");
	}

}