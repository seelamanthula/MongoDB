package com.week1;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class HelloWorldSpark {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Spark.get(new Route("/") {

			@Override
			public Object handle(Request arg0, Response arg1) {
				
				return "Hello World from Spark";
			}
			
		});
	}

}
