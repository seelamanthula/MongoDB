package com.week1;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class HelloWorldSparkFreeMarker {

	public static void main(String[] args) {
		
		final Configuration config = new Configuration();
		config.setClassForTemplateLoading(HelloWorldSparkFreeMarker.class, "/");
		
		Spark.get(new Route("/") {
			
			StringWriter writer = new StringWriter();

			@Override
			public Object handle(Request arg0, Response arg1) {
				
				try {
					Template hTemplate = config.getTemplate("hello.ftl");
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("name", "Computer");
					hTemplate.process(map, writer);
					
					System.out.println(writer);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return writer;
			}
			
			
		});

	}

}
