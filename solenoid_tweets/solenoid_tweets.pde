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
int simul_counter = 0;

// Configuration
String[] tags = {"#greece", "#oxi", "#grexit", "#hello"};
String config_filename = "solenoid_tweets/resources/config.properties";
boolean is_production; // true: production. false: simulations

void sleep(int ms) {
    try {
        Thread.sleep(ms); // ms
    }
    catch (InterruptedException e) {
        System.out.println("sleep InterruptedException: " + e);
    }
}

void setup() {
    size(800,600);
    fill(255);

    Config config = new Config(config_filename);
    this.is_production = ("1".equals(config.get("production")));

    // Tweets listener
    StatusListener listener = new StatusListener() {
        public void onStatus(Status status) {
			
			String username;
			String text;

			if (status == null) {
			    username = "username simul";
			    text = "text simul (" + simul_counter + ")";
			    simul_counter++;
			}
			else {
			    username = status.getUser().getName();
			    text = status.getText();
			}
			
            System.out.println(username + " : " + text);
            
            background(0);
            
            text(username + " : " + text,
                 width/2, height/2,
                 300, 200);
          
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

    TweetsProducer tweets_producer =
      TweetsFactory.get_tweets_producer(is_production, listener, config, this);

    this.solenoid = new Solenoid(this,
                                 config.get("port"),
                                 "1".equals(config.get("hit")));
    
    Control control = new Control(tweets_producer, this.tags);
    
    // Main loop
    //while (true) {
	//	System.out.println("Main loop");
	//	this.sleep(1000);
	//}
}

void draw() {
}

