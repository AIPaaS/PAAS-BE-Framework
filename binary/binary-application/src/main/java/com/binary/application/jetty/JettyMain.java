package com.binary.application.jetty;



public class JettyMain {
	
	
	

	public static void main(String[] args) {
		JettyServer server = new JettyServer("classpath:jetty.properties");
		server.start();
	}
	
	
}
