package com.hw25;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

public class MongoDBHW5 {

	public static void main(String[] args) {
		
		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase("video");
		MongoCollection collection = database.getCollection("movieDetails");
		
		System.out.println(collection.count());
		
		//Document dd1 = new Document("countries", new Document(Filters.size(fieldName, size)));
		
		
		Bson filter2 = Filters.gt("counties",2);
		
		Bson filter = Filters.eq("countries.1", "Sweden");
		Bson proj = Projections.fields(Projections.include("countries"));
		
		ArrayList<Document> list = (ArrayList<Document>) collection.find().filter(filter2)
				.projection(proj).into(new ArrayList<Document>());
		
		for(int i  =0 ; i < list.size(); i++) {
			printJson(list.get(i));
		}
		System.out.println("Size : "+list.size());
	}
	
	public static void task1(MongoCollection collection) {
		Bson filter = Filters.and(Filters.eq("year", 2013), Filters.eq("rated", "PG-13"), Filters.eq("awards.wins", 0));		
		Bson proj = Projections.fields(Projections.include(Arrays.asList("title", "awards")),
									Projections.excludeId());
		
		ArrayList<Document> list = (ArrayList<Document>) collection.find().filter(filter).projection(proj).into(new ArrayList<Document>());
		
	//	printJson(list.get(0));
		
		MongoCursor<Document> cursor = collection.find().filter(filter).projection(proj).iterator();
		
		try {
			while(cursor.hasNext()) {
				printJson(cursor.next());
			}
		} finally {
			cursor.close();
		}

		System.out.println("List Size : "+list.size());

	}
	public static void printJson(Document document) {
		
		JsonWriter writer = new JsonWriter(new StringWriter(),
				new JsonWriterSettings(JsonMode.SHELL, false));
		
		new DocumentCodec().encode(writer, document, 
				EncoderContext.builder().isEncodingCollectibleDocument(true)
				.build());
		
		System.out.println(writer.getWriter());
		System.out.flush();
	}
}
