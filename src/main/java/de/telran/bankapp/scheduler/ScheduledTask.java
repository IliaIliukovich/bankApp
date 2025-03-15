package de.telran.bankapp.scheduler;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class ScheduledTask {

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS, initialDelay = 5)
    public void taskAtFixedRate() {
       log.info("Doing task at fixed rate: {}", new Date());
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    @Async
    public void taskAtFixedDelay() {
        log.error("Doing task at fixed delay - start: {}", new Date());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException ignore) {}
        log.error("Doing task at fixed delay - end: {}", new Date());
    }

    @Scheduled(cron = "*/15 * * * * *")
    public void tasWithCron() {
        log.warn("Doing task with cron: {}", new Date());
    }

    @Scheduled(cron = "0 * * * * *")
    public void printWeatherForecast() {
        try {
            String url = "http://rss.accuweather.com/rss/liveweather_rss.asp?metric=1&locCode=EUR|DE|GM003|BERLIN";

            try (XmlReader reader = new XmlReader(new URL(url))) {
                SyndFeed feed = new SyndFeedInput().build(reader);
                log.info("Forecast info: {} : {}",
                        feed.getTitle(),
                        feed.getEntries().stream().findFirst().get().getTitle());
            }
        }  catch (Exception ignore) {
        }

    }

}
