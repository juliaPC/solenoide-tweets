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
Serial arduinoPort; // serial port object

// Put the OAuth data into the ConfigurationBuilder
void configure_cb(ConfigurationBuilder cb) {
    cb.setOAuthConsumerKey(TweeterAccountData.OAuthConsumerKey);
    cb.setOAuthConsumerSecret(TweeterAccountData.OAuthConsumerSecret);
    cb.setOAuthAccessToken(TweeterAccountData.OAuthAccessToken);
    cb.setOAuthAccessTokenSecret(TweeterAccountData.OAuthAccessTokenSecret);
}

void setup() {
    size(800,600);
    fill(255);
                
    // serial communication with arduino
    println(Serial.list());
    String portName = Serial.list()[5];
    arduinoPort = new Serial(this, portName, 9600);
  
    // authenticate our application with the API application keys zith ConfigurationBuilder object
    ConfigurationBuilder cb = new ConfigurationBuilder();
    configure_cb(cb);  
    
    StatusListener listener = new StatusListener(){
        public void onStatus(Status status) {
            System.out.println(status.getUser().getName() + " : " + status.getText());
            
            background(0);
            
            text(status.getUser().getName() + " : " + status.getText(), width/2, height/2, 300, 200);
          
            // notify new tweet to Arduino via serial
            arduinoPort.write(255);
        }
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
        public void onStallWarning(StallWarning sw) {}
        public void onScrubGeo(long a, long b) {}
        public void onException(Exception ex) {
            ex.printStackTrace();
        }
    };
    
    // obtener objeto twitterstream
    TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    // añadir listener
    twitterStream.addListener(listener);
    
    FilterQuery query = new FilterQuery();
    String tweetsDiana[] = {"#greece", "#oxi", "#grexit" };
    query.track(tweetsDiana);
    
    twitterStream.filter(query);    
}

void draw() {
}

