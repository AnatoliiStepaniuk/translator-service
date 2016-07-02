package in.ramanujam.service;

import in.ramanujam.model.ElasticSearchRecord;
import in.ramanujam.model.MongoDBRecord;
import in.ramanujam.model.RedisRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Denys Konakhevych
 * Date: 01.07.2016
 * Time: 19:57
 */
public class TestUtil // TODO: add automated tests!
{
  public static MongoDBRecord generateMongoRecord()
  {
    RedisRecord redisRecord = new RedisRecord( 1, "1PtEHHN1SLUACkRhyBADURZWQuTpX56HRo" );
    ElasticSearchRecord elasticSearchRecord = new ElasticSearchRecord( 1, "Female", "Louise", "Matthews", "lmatthews0@ning.com", "73.145.90.254" );
    return new MongoDBRecord( redisRecord, elasticSearchRecord );
  }

  public static List<MongoDBRecord> generateMongoRecords()
  {
    List<MongoDBRecord> records = new ArrayList<>( );
    for( int id = 1; id <= 1000; id++ )
    {
      RedisRecord redisRecord = new RedisRecord( id, randomString() );
      ElasticSearchRecord elasticSearchRecord = new ElasticSearchRecord( id, randomString(), randomString(), randomString(), randomEmail(), randomIP() );
      MongoDBRecord mongoDBRecord = new MongoDBRecord( redisRecord, elasticSearchRecord );
      records.add( mongoDBRecord );
    }
    return records;
  }

  public static List<RedisRecord> generateRedisRecords()
  {
    List<RedisRecord> records = new ArrayList<>( );
    for( int id = 1; id <= 1000; id++ )
    {
      RedisRecord redisRecord = new RedisRecord( id, randomString() );
      records.add( redisRecord );
    }
    return records;
  }

  public static List<ElasticSearchRecord> generateElasticSearchRecords()
  {
    List<ElasticSearchRecord> records = new ArrayList<>( );
    for( int id = 1; id <= 1000; id++ )
    {
      ElasticSearchRecord elasticSearchRecord = new ElasticSearchRecord( id, randomString(), randomString(), randomString(), randomEmail(), randomIP() );
      records.add( elasticSearchRecord );
    }
    return records;
  }

  private static String randomString()
  {
    return UUID.randomUUID().toString().replaceAll( "-", "" );
  }

  private static String randomEmail()
  {
    return UUID.randomUUID().toString().replaceAll( "-", "" ) + "@ema.il";
  }

  private static String randomIP()
  {
    Random random = new Random();
    return randInt(random, 0, 255) + "." + randInt(random, 0, 255) + "." + randInt(random, 0, 255) + "." + randInt(random, 0, 255);
  }

  public static int randInt(Random rand, int min, int max) {
    return rand.nextInt((max - min) + 1) + min;
  }
}
