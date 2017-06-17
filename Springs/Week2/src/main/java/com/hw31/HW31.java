package com.hw31;

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
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

public class HW31 {

	public static void main(String[] args) {

		MongoClient client = new MongoClient();
//		MongoDatabase database = client.getDatabase("students");
		
		MongoDatabase database = client.getDatabase("school");
		MongoCollection collection = database.getCollection("students");
		
		System.out.println("Count : "+collection.count());
		task1(collection);
		
//		tryTrial(database);
	}
	
	public static void task1(MongoCollection collection) {
		Bson filter = Filters.eq("scores.type", "homework");
		Bson sort = Sorts.descending("scores.score");
		Bson proj = Projections.include(Arrays.asList("scores.type","scores.score"));
	//	Bson proj2 = new Document("scores.type", 1).append("scores.score",1);
		
		MongoCursor<Document> cursor = collection.find().filter(filter).sort(sort).projection(proj).iterator();
		
		try {
			while(cursor.hasNext()) {
				Document d = cursor.next();
				List<Document> list = (List<Document>) d.get("scores");
			//	int dt = findHomework(list);
				Document dt = findHomework(list);
				
				if(dt != null) {
				//	System.out.println("Inside");
//					d.remove("scores.3");
					Bson del = Updates.pull("scores", dt);
					printJson(d);
					
					collection.updateOne(d, del);
				}				
			}
		} finally {
			cursor.close();
		}
		
	}

	public static Document findHomework(List<Document> list) {
		double k = 100; int pos = -1;
		for(int i = 0 ; i < list.size(); i++) {
			Document a = list.get(i);
			if((a.getString("type")).equals("homework")) {
				double t = a.getDouble("score");
				if(k > t) {k = t; pos = i;}
			}
		}
		
		Document doc = null;
		if(k != 100)
			doc = new Document("type", "homework").append("score", k);
		
		printJson(doc);
		return doc;
//		return pos;
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

	public static List<Document> removeMinimum(List<Document> list) {
		if(list.size() > 1)
			list.remove(list.size() - 1);
	
		return list;
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
