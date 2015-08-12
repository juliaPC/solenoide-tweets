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

public class Control implements Runnable {
    private Thread th;
    private TweetsProducer tweets_producer;
    private String[] tags;

    public void run() {
		int counter = 0;
		boolean status = true;
		
		while (true) {
			try {
		        System.out.println("Control thread running (" + counter + ")...");
		        
		        if (counter % 10 == 0) {
					if (status)
					    this.start_producer();
					else
					    this.stop_producer();					
					status = !status;
				}
                counter++;
				
		        
		        
		        Thread.sleep(2000); // ms
			}
			catch (InterruptedException e) {
				System.out.println("Control InterruptedException: " + e);
			}
	    }
    }

    public Control(TweetsProducer tweets_producer, String[] tags) {
		this.tweets_producer = tweets_producer;
		this.tags = tags;
		
        this.th = new Thread(this, "control_thread");
        this.th.start();
    }

    public void start_producer() {
		System.out.println("Control starting producer thread");
                this.tweets_producer.start(this.tags);
    }

    public void stop_producer() {
		System.out.println("Control stopping producer thread");
		this.tweets_producer.stop();
    }
}

/*

// check time with the clock on your computer
  int s = second();  // Values from 0 - 59
  int m = minute();  // Values from 0 - 59
  int h = hour();    // Values from 0 - 23

// time schedule
  if ((h > 9) && (h < 22)) {
    public void start_producer() {
                System.out.println("Control starting producer thread");
                this.tweets_producer.start(this.tags);
    }  
  }
  
  else { 
    public void stop_producer() {
               System.out.println("Control stopping producer thread");
               this.tweets_producer.stop();
    }
  }
  
*/
