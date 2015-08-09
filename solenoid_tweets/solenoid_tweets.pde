// Solenoid tweets
// Under CC0 1.0 Universal (CC0 1.0) - Public Domain Dedication
//
// By:
// Miguel Colom - http://mcolom.info/
// Julia Puyo - http://juliapuyo.com/

// Twitter4J library
import twitter4j.conf.*;
import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.api.*;

// java.util package
import java.util.*;
import processing.serial.*; // serial communication library

Solenoid solenoid = null;

// Configuration
String[] tags = {"#greece", "#oxi", "#grexit"};
String config_filename = "solenoid_tweets/resources/config.properties";
boolean is_production = true; // True: production. False: simulations

void setup() {
    size(800,600);
    fill(255);

    Config config = new Config(config_filename);

    // Tweets listener
    StatusListener listener = new StatusListener() {
        public void onStatus(Status status) {
            System.out.println(status.getUser().getName() + " : " + status.getText());
            
            background(0);
            
            text(status.getUser().getName() + " : " + status.getText(), width/2, height/2, 300, 200);
          
            // notify new tweet to Arduino via serial
            solenoid.hit();
        }
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
        public void onStallWarning(StallWarning sw) {}
        public void onScrubGeo(long a, long b) {}
        public void onException(Exception ex) {
            ex.printStackTrace();
        }
    };

    TweetsProducer tweets_producer = TweetsFactory.get_tweets_producer(is_production, config, this);
    tweets_producer.add_listener(listener);

    this.solenoid = new Solenoid(this,
                                 config.get("port"),
                                 config.get("hit") == "1");

    tweets_producer.start(this.tags);
}

void draw() {
}

