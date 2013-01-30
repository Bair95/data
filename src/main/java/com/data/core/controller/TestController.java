package com.data.core.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

@Path("/test")
@Produces({ "application/json" })
public final class TestController {

	@GET
	@Path("")
	public String get() {
		return "Hello World";
	}

}
