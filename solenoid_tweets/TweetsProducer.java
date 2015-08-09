// Solenoid tweets
// Under CC0 1.0 Universal (CC0 1.0) - Public Domain Dedication
//
// By:
// Miguel Colom - http://mcolom.info/
// Julia Puyo - http://juliapuyo.com/

// Twitter4J library
import twitter4j.*;

public class TweetsProducer {
    protected StatusListener listener;
 
    public void add_listener(StatusListener listener) {
        this.listener = listener;
    }
}
