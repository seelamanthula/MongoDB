package com.week7.ques8;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Question8 {

	  public static void main(String[] args) {
			MongoClient client = new MongoClient(new ServerAddress("localhost",27017));
			MongoDatabase db = client.getDatabase("week7");
          MongoCollection<Document> animals = db.getCollection("ques8");

          System.out.println("Before : "+animals.count());
         Document animal = new Document("animal", "monkey");

          animals.insertOne(animal);
          animal.remove("animal");
          animal.append("animal", "cat");
          animals.insertOne(animal);
          animal.remove("animal");
          animal.append("animal", "lion");
          animals.insertOne(animal);
          
          System.out.println("After : "+animals.count());
      }
	  
}
