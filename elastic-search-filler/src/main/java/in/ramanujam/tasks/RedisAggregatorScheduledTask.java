package in.ramanujam.tasks;

import in.ramanujam.services.MongoUtils;
import in.ramanujam.common.messaging.MessageBus;
import in.ramanujam.common.model.BitcoinRecord;
import in.ramanujam.common.properties.RedisProperties;
import in.ramanujam.services.aggregators.RedisAggregator;
import in.ramanujam.starters.RedisAggregatorStarter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RedisAggregatorScheduledTask
{
    // TODO: how can we stop it from running after all records are persisted?
    @Scheduled(fixedDelay = 100)
    public void runWithDelay() throws IOException
    {
        List<BitcoinRecord> records = RedisAggregator.retrieveAllRecords();
        for( BitcoinRecord bitcoinRecord : records )
        {
            RedisAggregator.moveRecordFromRedisToMongo( bitcoinRecord, MongoUtils.getCollection() );
        }

        if( records.size() == 0 && RedisAggregator.isRedisFillerFinished() )
        {
            MessageBus.getInstance().sendMessage( RedisProperties.getInstance().getRedisToMongoIsFinishedKey() );
            System.out.println( "RedisToMongo :: Successfully finished!");
            RedisAggregatorStarter.shutdown();
        }
    }
}
