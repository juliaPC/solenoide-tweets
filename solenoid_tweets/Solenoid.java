// Solenoid tweets
// Under CC0 1.0 Universal (CC0 1.0) - Public Domain Dedication
//
// By:
// Miguel Colom - http://mcolom.info/
// Julia Puyo - http://juliapuyo.com/

import java.io.*; 
import java.util.*;

import processing.*;
import processing.core.*;
import processing.serial.*; // serial communication library


public class Solenoid implements Runnable {
  private String pin;
  private boolean do_hit = false;
  private List bytes_pin = new ArrayList<Byte>();
  private int pending = 0; // number of pending solenoid hits
  private Thread th = null;

  //public synchronized void add_hit() {
  public synchronized void add_hit() {
	  this.pending++;

	  // Avoid too many enqueued tweets
	  if (this.pending > 100)
	    this.pending = 10;
  }

  public synchronized void done_hit() {
	  this.pending--;
  }

  // Thread run
  public void run() {

    while (true) {
	  // Process pending tweets
	  if (this.pending > 0) {
        // Solenoid cycle

        int half_cycle_time_ms = 100;  // [ToDo]: read from config

        this.activate(true);
        this.delay(half_cycle_time_ms);
        this.activate(false);
        this.delay(half_cycle_time_ms);

        this.done_hit();
      }

      // Prevent CPU at 100%
	  this.delay(10);
	}
  }

  public Solenoid(PApplet p_applet, String pin, boolean do_hit) {
    this.do_hit = do_hit;
    this.pin = pin;
    
    //this.bytes_pin = new ArrayList<Byte>();
    this.bytes_pin.add(new Byte((byte)(pin.charAt(0))));
    
    if (pin.length() > 1)
      this.bytes_pin.add(new Byte((byte)(pin.charAt(1))));

    if (do_hit) {
      // GPIO config
      unexport_pin();
      export_pin();

      this.activate(false);

      this.th = new Thread(this, "solenoid_thread");
      this.th.start();
    }
  }

  private void export_pin() {
      try {
        RandomAccessFile f = new RandomAccessFile("/sys/class/gpio/export", "rw");
        
        byte b0 = (Byte)this.bytes_pin.get(0);
        f.writeByte(b0);

        if (this.bytes_pin.size() > 1) {
			byte b1 = (Byte)this.bytes_pin.get(1);
            f.writeByte(b1);
	    }
        f.close();
      }
      catch (FileNotFoundException e) {
        System.out.println("FileNotFoundException: " + e);
      }
      catch (IOException e) {
        System.out.println("IOException: " + e);
      }
  }

  private void unexport_pin() {
      try {
        RandomAccessFile f = new RandomAccessFile("/sys/class/gpio/unexport", "rw");
        
        byte b0 = (Byte)this.bytes_pin.get(0);
        f.writeByte(b0);
        
        if (this.bytes_pin.size() > 1) {
			byte b1 = (Byte)this.bytes_pin.get(1);
            f.writeByte(b1);
	    }

        f.close();
      }
      catch (FileNotFoundException e) {
        System.out.println("FileNotFoundException: " + e);
      }
      catch (IOException e) {
        System.out.println("IOException: " + e);
      }
  }

  public void delay(int ms) {
    try {
      Thread.sleep(ms);
    }
    catch (InterruptedException e) {
      System.out.println("solenoid delay: " + e);
    }
  }

  private void activate(boolean status) {    
    // Open file and write '0' or '1' to control solenoid device using the GPIO pin
    try {
      RandomAccessFile pin_file = new RandomAccessFile("/sys/class/gpio/gpio" + this.pin + "/value", "rw");
      if (status)
        pin_file.writeByte((byte)'1');
      else
        pin_file.writeByte((byte)'0');
      pin_file.close();
    }
    catch (FileNotFoundException e) {
      System.out.println("FileNotFoundException: " + e);
    }
    catch (IOException e) {
      System.out.println("IOException: " + e);
    }
  }

  // Hit notification
  public void hit() {
    if (this.do_hit) {
      System.out.println("Solenoid hit!");
      this.add_hit();
    }
    else
      System.out.println("Solenoid simulated hit!");
  }
  
  // Free sources before finishing
  public void finalize() throws IOException {
    // Unexport pin so it can be used by others
    if (do_hit)
	  this.unexport_pin();

	// Stop thread
	if (this.th != null)
	  this.th.stop();
  }
}

