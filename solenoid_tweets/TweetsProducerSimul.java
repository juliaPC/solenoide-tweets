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
import java.util.Random;

public class TweetsProducerSimul extends TweetsProducer implements Runnable {

    private Thread th = null;

    private boolean running;
    private Random rand;
    private int simul_max_random;
    
    public void run() {
        int counter = 0;
        while (this.running) {
            try {
                System.out.println("Simul thread running... (" + counter + ")");
                
                this.listener.onStatus(null);
                
                counter++;

                int time = rand.nextInt(this.simul_max_random);
                Thread.sleep(time); // ms
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
        this.rand = new Random();
                System.out.println("this.config: " + this.config);
        this.simul_max_random = Integer.parseInt(
                                  this.config.get("simul_max_random"));
        this.running = false;
    }

    public synchronized void start(String[] tags) {
        this.stop();

        this.th = new Thread(this, "tweets_simul_thread");
        this.running = true;
        this.th.start();
    }

    public synchronized void stop() {
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

