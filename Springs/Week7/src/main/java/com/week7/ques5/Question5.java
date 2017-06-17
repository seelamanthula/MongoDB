package com.week7.ques5;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Question5 {

	public static void main(String[] args) {
		
		MongoClient client = new MongoClient(new ServerAddress("localhost",27017));
		MongoDatabase db = client.getDatabase("week7");
		MongoCollection collect = db.getCollection("ques5");
		
		for(int i = 1; i <= 10; i++) {
			for(int j = 1; j <= 10; j++) {
				for(int k = 1; k <= 10; k++) {
					Document doc = new Document("a",i).append("b", j).append("c", k);
					collect.insertOne(doc);
				}	
			}
		}
		
	}
}
