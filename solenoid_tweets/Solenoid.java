// Solenoid tweets
// Under CC0 1.0 Universal (CC0 1.0) - Public Domain Dedication
//
// By:
// Miguel Colom - http://mcolom.info/
// Julia Puyo - http://juliapuyo.com/

import java.io.*; 

import processing.*;
import processing.core.*;
import processing.serial.*; // serial communication library


public class Solenoid {
  private boolean do_hit = false;
  private RandomAccessFile pin_file = null;
  private byte[] bytes_pin = null;

  public Solenoid(PApplet p_applet, String pin, boolean do_hit) {
    this.do_hit = do_hit;

    if (do_hit) {
      // GPIO config
      unexport_pin();
      export_pin();

      // Open file to control solenoid device using the GPIO pin
      try {
        this.pin_file = new RandomAccessFile("/sys/class/gpio/gpio" + pin + "/value", "rw");
        this.activate(false);
      }
      catch (FileNotFoundException e) {
        System.out.println("FileNotFoundException: " + e);
      }
      catch (IOException e) {
        System.out.println("IOException: " + e);
      }
    }
  }

  private void export_pin() {
      try {
        RandomAccessFile f = new RandomAccessFile("/sys/class/gpio/export", "rw");
        f.writeByte(this.bytes_pin[0]);
        if (this.bytes_pin.length > 1)
          f.writeByte(this.bytes_pin[1]);
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
        f.writeByte(this.bytes_pin[0]);
        if (this.bytes_pin.length > 1)
          f.writeByte(this.bytes_pin[1]);
        f.close();
      }
      catch (FileNotFoundException e) {
        System.out.println("FileNotFoundException: " + e);
      }
      catch (IOException e) {
        System.out.println("IOException: " + e);
      }
  }

  public void delay() {
    try {
      Thread.sleep(200); // ms
    }
    catch (InterruptedException e) {
      System.out.println("solenoid delay: " + e);
    }
  }

  private void activate(boolean status) {
    try {
      if (status)
        this.pin_file.writeByte((byte)'1');
      else
        this.pin_file.writeByte((byte)'0');
    }
    catch (FileNotFoundException e) {
      System.out.println("FileNotFoundException: " + e);
    }
    catch (IOException e) {
      System.out.println("IOException: " + e);
    }
  }

  public void hit() {
    if (this.do_hit) {
      System.out.println("Solenoid hit!");

      this.activate(true);
      this.delay();
      this.activate(false);
    } else {
      System.out.println("Solenoid simulated hit!");
    }
  }
  
  // Free sources before finishing
  public void finalize() throws IOException {
	// Close pin file
	if (this.pin_file != null)
        this.pin_file.close();

	// Unexport pin so it can be used by others
    if (do_hit)
	  this.unexport_pin();
  }
}
