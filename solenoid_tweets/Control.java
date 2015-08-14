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
import java.util.*;


public class Control implements Runnable {
    private Thread th;
    private TweetsProducer tweets_producer;
    private boolean producer_running;

    // Start and end working times
    private Calendar start_cal, end_cal;

    private String[] tags;

    public void run() {
        while (true) { 
            try {
                System.out.println("Control thread running");

                // Get current time
                Calendar current_cal = Calendar.getInstance();

                // Check if within working time and start/stop the producer
                if (!this.producer_running && this.within(current_cal))
                    this.start_producer();
                else {
                    if (this.producer_running && !this.within(current_cal))
                        this.stop_producer();
                }

                Thread.sleep(2000); // ms
            }
            catch (InterruptedException e) {
                System.out.println("Control InterruptedException: " + e);
            }
        }
    }

    // Checks if the current time is within the configured working time
    private boolean within(Calendar cal) {
        int h1 = this.start_cal.get(Calendar.HOUR_OF_DAY);
        int m1 = this.start_cal.get(Calendar.MINUTE);
        int start = h1*60 + m1;

        int h2 = this.end_cal.get(Calendar.HOUR_OF_DAY);
        int m2 = this.end_cal.get(Calendar.MINUTE);
        int end = h2*60 + m2;

        Calendar current_cal = Calendar.getInstance();
        int h = current_cal.get(Calendar.HOUR_OF_DAY);
        int m = current_cal.get(Calendar.MINUTE);
        int current = h*60 + m;

        return current >= start && current <= end;
    }

    public Control(TweetsProducer tweets_producer, String[] tags,
                   Calendar start_cal, Calendar end_cal) {
        this.tweets_producer = tweets_producer;
        this.tags = tags;
        //
        this.start_cal = start_cal;
        this.end_cal = end_cal;
        //
        this.producer_running = false;
        
        this.th = new Thread(this, "control_thread");
        this.th.start();
    }

    public synchronized void start_producer() {
        System.out.println("Control starting producer thread");
        this.tweets_producer.start(this.tags);
        this.producer_running = true;
    }

    public synchronized void stop_producer() {
        System.out.println("Control stopping producer thread");
        this.tweets_producer.stop();
        this.producer_running = false;
    }
}

