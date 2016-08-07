package in.ramanujam.elasticsearch.filler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ElasticSearchFillerScheduledTask {

    public static final int PAGE_SIZE = 100;

    private static final Logger log = LoggerFactory.getLogger(ElasticSearchFillerScheduledTask.class);

    @Autowired
    private ElasticSearchFiller elasticFiller;

    private int curPos = 0;

    @Scheduled(fixedDelay = 30_000)
    public void runWithDelay() throws IOException {
        if (elasticFiller.fillItems(curPos, PAGE_SIZE) == 0) {
            elasticFiller.writeIsFinished(true);
            log.info("ElasticSearchFiller :: Successfully finished!");
            ElasticSearchFillerStarter.shutdown();
        }
        curPos += PAGE_SIZE;
    }
}
