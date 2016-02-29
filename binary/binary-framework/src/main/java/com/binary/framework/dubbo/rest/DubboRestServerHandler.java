package com.binary.framework.dubbo.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Path("/dubbo/rest")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface DubboRestServerHandler {
	
	
	
	
	@POST
    @Path("/post")
	public String post(String jsonParam) throws Throwable ;
	
	
	

}
