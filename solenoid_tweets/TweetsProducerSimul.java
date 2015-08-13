// Solenoid tweets
// Under CC0 1.0 Universal (CC0 1.0) - Public Domain Dedication
//
// By:
// Miguel Colom - http://mcolom.info/
// Julia Puyo - http://juliapuyo.com/

// Twitter4J library
import twitter4j.*;
import twitter4j.conf.*;
import processing.core.*;

public class TweetsProducerSimul extends TweetsProducer implements Runnable {

    private Thread th = null;

    private boolean running;
    private Config config;
    
    public void run() {
        int counter = 0;
        while (this.running) {
            try {
                System.out.println("Simul thread running... (" + counter + ")");
                
                this.listener.onStatus(null);
                
                counter++;
                Thread.sleep(2000); // ms
            }
            catch (InterruptedException e) {
                System.out.println("Simul InterruptedException: " + e);
            }
        }
    }

    public TweetsProducerSimul(Config config,
                               StatusListener listener,
                               PApplet p_applet) {
        super(config, listener, p_applet);
        this.th = new Thread(this, "tweets_simul_thread");
        this.running = false;
    }

    public void start(String[] tags) {
        this.stop();

        this.th = new Thread(this, "tweets_simul_thread");
        this.running = true;
        this.th.start();
    }

    public void stop() {
        this.running = false;
        if (this.th == null)
            return;

        try {
            this.th.join();
        }
        catch (InterruptedException e) {
            System.out.println("Simul start InterruptedException: " + e);
        }
    }
}
