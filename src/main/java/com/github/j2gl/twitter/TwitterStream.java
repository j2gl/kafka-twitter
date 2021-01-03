package com.github.j2gl.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TwitterStream {

    public static Logger logger = LoggerFactory.getLogger(TwitterStream.class.getName());

    public void readFromTwitter(String bearerToken) throws IOException {
        FilteredStream filteredStream = new FilteredStream();
        try {
            createRules(filteredStream, bearerToken);
            filteredStream.connectStream(bearerToken);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void createRules(FilteredStream filteredStream, String bearerToken) throws IOException, URISyntaxException {
        Map<String, String> rules = new HashMap<>();
        // rules.put("Albert Einstein lang:es", "Albert_Einstein_token");
        rules.put("Albert Einstein", "Albert_Einstein_token");
        filteredStream.setupRules(bearerToken, rules);
    }

    public static void main(String[] args) {
        try (InputStream in = TwitterStream.class.getResourceAsStream("/application.properties")) {
            if (in == null) {
                logger.info("No application.properties found");
                return;
            }
            Properties properties = new Properties();
            properties.load(in);
            final String token = properties.getProperty("twitter.token");
            final TwitterStream twitterStream = new TwitterStream();
            twitterStream.readFromTwitter(token);
        } catch (IOException ex) {
            logger.error("", ex);
        }
    }

}
