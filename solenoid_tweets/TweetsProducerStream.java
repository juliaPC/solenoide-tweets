// Solenoid tweets
// Under CC0 1.0 Universal (CC0 1.0) - Public Domain Dedication
//
// By:
// Miguel Colom - http://mcolom.info/
// Julia Puyo - http://juliapuyo.com/

import twitter4j.conf.*;
import twitter4j.*;
import twitter4j.auth.*;
import twitter4j.api.*;
//
import processing.core.*;

public class TweetsProducerStream extends TweetsProducer {

    private ConfigurationBuilder cb;

    // Put the OAuth data into the ConfigurationBuilder
    private static void configure_cb(ConfigurationBuilder cb) {
        cb.setOAuthConsumerKey(TweeterAccountData.OAuthConsumerKey);
        cb.setOAuthConsumerSecret(TweeterAccountData.OAuthConsumerSecret);
        cb.setOAuthAccessToken(TweeterAccountData.OAuthAccessToken);
        cb.setOAuthAccessTokenSecret(TweeterAccountData.OAuthAccessTokenSecret);
    }

    public TweetsProducerStream(PApplet p_applet) {
       // authenticate our application with the API application keys with
       // ConfigurationBuilder object
       this.cb = new ConfigurationBuilder();
       this.configure_cb(this.cb);  
       
       // obtener objeto twitterstream
       TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
       // añadir listener
       twitterStream.addListener(this.listener);
    
       FilterQuery query = new FilterQuery();
       String tweetsDiana[] = {"#greece", "#oxi", "#grexit" };
       query.track(tweetsDiana);
    
       twitterStream.filter(query);
    }
}
