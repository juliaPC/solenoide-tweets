// Solenoid tweets
// Under CC0 1.0 Universal (CC0 1.0) - Public Domain Dedication
//
// By:
// Miguel Colom - http://mcolom.info/
// Julia Puyo - http://juliapuyo.com/

// Twitter4J library
import twitter4j.conf.*;
import processing.core.*;
import twitter4j.*;

public class TweetsFactory {
    static TweetsProducer get_tweets_producer(boolean is_production,
                                              StatusListener listener,
                                              Config config,
                                              PApplet p_applet) {
        if (is_production) {
            System.out.println("TweetsProducer creating stream producer");
            return new TweetsProducerStream(config, listener, p_applet);
        }
        else {
            System.out.println("TweetsProducer creating simulation producer");
            return new TweetsProducerSimul(config, listener, p_applet);
        }
    }
}
