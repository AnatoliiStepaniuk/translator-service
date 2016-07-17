package in.ramanujam.elasticsearch.aggregator;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import in.ramanujam.common.properties.MongoDBProperties;

import java.net.UnknownHostException;

public class MongoUtils
{
  private static DBCollection collection;

  private static DBCollection setupCollection()
  {
    MongoDBProperties properties = new MongoDBProperties();
    MongoClient mongo = new MongoClient( properties.getMongoHost(), properties.getMongoPort() );
    mongo.setWriteConcern( WriteConcern.SAFE );
    DB db = mongo.getDB( properties.getMongoDb() );

    return db.getCollection( properties.getMongoCollection() );
  }

  public static DBCollection getCollection()
  {
    if( collection != null )
      return collection;
    else return collection = setupCollection();
  }
}
