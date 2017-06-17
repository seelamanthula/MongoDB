package com.week7.ques7;

import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Question7 {

	public static void main(String[] args) {
		
		MongoClient client = new MongoClient(new ServerAddress("localhost",27017));
		MongoDatabase db = client.getDatabase("week7");
		MongoCollection albums = db.getCollection("albums");
		MongoCollection images = db.getCollection("images");
		
		System.out.println("Albums : "+albums.count());
		System.out.println("IMages : "+images.count());
	
	/*	ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(int i = 0; i < 100000; i++) {
			long c = albums.count(Filters.eq("images", i));
			if(c == 0) {list.add(i);}
		}
		
		System.out.println("Removed Docs : "+list.size());
		
		Iterator<Integer> iterate  = list.iterator();
		while(iterate.hasNext()) {
			images.deleteOne(new Document("_id",iterate.next()));
		}*/
		
		long c = images.count(Filters.eq("tags", "sunrises"));
		
	//	System.out.println("After Removal : "+(images.count() - list.size()));
		System.out.println("Sunrises Removed : "+c);
	}
	
}
