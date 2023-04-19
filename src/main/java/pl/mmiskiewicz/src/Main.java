package pl.mmiskiewicz.src;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;


public class Main {
    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("vehicles"); //DB
        MongoCollection mongoCollection = mongoDatabase.getCollection("cars"); //Collection - 'table' in relational db

        //save(mongoCollection);
        //read(mongoCollection);
        //readByParam(mongoCollection, "Brand", "BMW");
        //delete(mongoCollection, "Brand", "Audi");
        //update(mongoCollection);

        MongoCollection mongoCollection2 = mongoDatabase.getCollection("bikes");
        saveBike(mongoCollection2);
    }

    private static void saveBike(MongoCollection mongoCollection) {
        Document document = new Document();
        document.put("name", "Mike's Bike");

        Document documentPerson = new Document();
        documentPerson.put("Name", "Michał");
        documentPerson.put("Surname", "Miśkiewicz");

        document.put("owner", documentPerson);
        mongoCollection.insertOne(document);
    }

    private static void update(MongoCollection mongoCollection) {
        Bson eq = Filters.eq("Brand", "Audi");
        Bson query = combine(set("Model", "A2"), set("Color", "Black"));

        mongoCollection.updateOne(eq, query);
    }

    private static void delete(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document();
        document.put(param, value);
        mongoCollection.deleteMany(document);
    }

    private static void readByParam(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document();
        document.put(param, value);
        MongoCursor iterator = mongoCollection.find(document).iterator();
        iterator.forEachRemaining(x -> System.out.println(x));
    }

    private static void read(MongoCollection mongoCollection) {
        Document first = (Document) mongoCollection.find().first();
        System.out.println(first.toJson());
    }

    private static void save(MongoCollection mongoCollection) {
        Document document1 = new Document();
        document1.put("Brand", "Fiat");
        document1.put("Model", "126p");

        Document document2 = new Document();
        document2.put("Brand", "BMW");
        document2.put("Model", "One");
        document2.put("Color", "red");

        Document document3 = new Document();
        document3.put("Brand", "Audi");
        document3.put("Model", "A1");
        document3.put("Color", List.of("red", "black", "grey"));

        mongoCollection.insertMany(List.of(document1, document2, document3));
    }
}