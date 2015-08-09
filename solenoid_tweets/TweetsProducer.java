// Solenoid tweets
// Under CC0 1.0 Universal (CC0 1.0) - Public Domain Dedication
//
// By:
// Miguel Colom - http://mcolom.info/
// Julia Puyo - http://juliapuyo.com/

// Twitter4J library
import twitter4j.*;
import processing.core.*;

public abstract class TweetsProducer {
    protected StatusListener listener = null;
    protected Config config;
    protected PApplet p_applet;
    
    public TweetsProducer(Config config,
                          StatusListener listener,
                          PApplet p_applet) {
        this.config = config;
        this.p_applet = p_applet;
        this.listener = listener;
    }    
 
    public abstract void start(String[] tags);
    public abstract void stop();

}

