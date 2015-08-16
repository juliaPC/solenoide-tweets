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
String config_filename = "solenoid_tweets/resources/config.properties";
boolean is_production; // true: production. false: simulations


// Create a new Calender object with the current date and the
// given 24h time string (for example, "13:45")
Calendar create_calendar(String time_str) {
    // Parse string
    String[] hour_minute = time_str.split(":");
    int hour = Integer.parseInt(hour_minute[0]);
    int minute = Integer.parseInt(hour_minute[1]);

    // Create calendar
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, hour);
    cal.set(Calendar.MINUTE, minute);

    return cal;
}

// Gets an array of Strings with the configured target tags
String[] get_tags(Config config) {
    String str = config.get("tags");

    String[] tags = str.split(",");

    for (int i = 0; i < tags.length; i++)
        tags[i] = tags[i].trim();

    return tags;
}

void do_background() {
    background(0);
}


void setup() {
    size(800,600);
    do_background();
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
            
            do_background();
            
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
    
    // Configure start and end working times
    Calendar start_cal = this.create_calendar(config.get("start_time"));
    Calendar end_cal = this.create_calendar(config.get("end_time"));

    // Get the tags
    String[] tags = this.get_tags(config);

    // Create and start control object
    Control control = new Control(tweets_producer, tags,
                      start_cal, end_cal);    
}

void draw() {
}

