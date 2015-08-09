// Solenoid tweets
// Under CC0 1.0 Universal (CC0 1.0) - Public Domain Dedication
//
// By:
// Miguel Colom - http://mcolom.info/
// Julia Puyo - http://juliapuyo.com/

// Twitter4J library
import twitter4j.conf.*;
import processing.core.*;

public class TweetsProducerSimul extends TweetsProducer {

    private boolean running;

    public TweetsProducerSimul(PApplet p_applet) {
        this.running = true;
    }

    public void start(String[] tags) {
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }
}
