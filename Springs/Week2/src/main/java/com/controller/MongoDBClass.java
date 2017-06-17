package com.controller;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class MongoDBClass {

	public static void main(String[] args) {

		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase("students");	
//		tryTrial(database);
		
		MongoCollection collection2 = database.getCollection("grades");
		System.out.println("Grades Collection : "+collection2.count());
		
		MongoCollection collection = collection2;
		
		Bson sort = Sorts.orderBy(Sorts.ascending("student_id"),
								  Sorts.ascending("score"));
	
		Bson proj = Projections.fields(Projections.include(Arrays.asList("student_id", "type", "score")),
									   Projections.excludeId()); 
		
		MongoCursor<Document> cursor = collection.find().sort(sort).projection(proj).limit(5).iterator();
		
		while(cursor.hasNext()) {
			printJson(cursor.next());
		}
		
		System.out.println("Collection Count : "+collection.count());
	}

	public static void task3(MongoCollection collection) {
	
		Bson sort = Sorts.descending("score");
		MongoCursor<Document> cursor = collection.find().sort(sort)
				.skip(100).limit(1).iterator();
		
		while(cursor.hasNext()) {
			printJson(cursor.next());
		}
	}
	
	public static void task2(MongoCollection collection) {
	
		Bson filter = new Document("type","homework");
		ArrayList<Document> collect = (ArrayList<Document>) collection.find()
				.filter(filter).into(new ArrayList<Document>());

		System.out.println("Size : "+collect.size());

		Bson proj = new Document("student_id", 1).append("score",1);
		Bson proj2 = Projections.fields(Projections.include(Arrays.asList("score", "student_id")), 
										Projections.excludeId());
		
		Bson sort = Sorts.orderBy(Sorts.ascending("student_id"), Sorts.descending("score"));
		ArrayList<Document> cursor = (ArrayList<Document>) collection.find().filter(filter)
												.sort(sort)
												.projection(proj2)
												.into(new ArrayList<Document>());
		
		Document d1, d2;
		int key2 = 0, key1 = 0;
		for(int i = 0; i < cursor.size() -1; i++) {
			d1 = cursor.get(i);
			d2 = cursor.get(i + 1);
			key1 = d1.getInteger("student_id");
			key2 = d2.getInteger("student_id");
			
			if(key2 > key1) {
//				String s  = d1.getString("_id");
				collection.deleteOne(d1);
				cursor.remove(i); i--;
			}				
		}
		
		d1 = cursor.get(cursor.size() - 2);
		d2 = cursor.get(cursor.size() - 1);
		key1 = d1.getInteger("student_id");
		key2 = d2.getInteger("student_id");
		
		if(key2 == key1) {
			collection.deleteOne(d1);
			cursor.remove(cursor.size() - 1);
		}
		System.out.println("Count : "+cursor.size());

	}
	
	public static void tryTrial(MongoDatabase database) {
		
		MongoCollection collection = database.getCollection("trial");
		
		collection.drop();
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				Document d = new Document("x",i).append("y",j);
				collection.insertOne(d);
			}
		}
		
		Bson sort = new Document("x", 1).append("y", -1);
		Bson sort2 = Sorts.orderBy(Sorts.ascending("x"), Sorts.descending("y"));
		
		MongoCursor<Document> cursor = collection.find().sort(sort).iterator();
		
		try {
			while(cursor.hasNext()) {
				printJson(cursor.next());
			}
		} finally {
			cursor.close();
		}
	
		System.out.println("Count : "+collection.count());
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
