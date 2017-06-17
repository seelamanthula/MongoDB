package com.week1;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class HelloWorldFreeMarker {

	public static void main(String[] args) {
		
		Configuration config = new Configuration();
		config.setClassForTemplateLoading(HelloWorldFreeMarker.class, "/");
		
		try {
			Template hTemplate = config.getTemplate("hello.ftl");
			StringWriter writer = new StringWriter();
			Map<String,Object> hMap = new HashMap<String, Object>();
			hMap.put("name", "Harish");
			hTemplate.process(hMap, writer);
			
			System.out.println(writer);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
