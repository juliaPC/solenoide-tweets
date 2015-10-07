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
  private Serial arduinoPort = null; // serial port object
  private boolean do_hit;

  private void println(String[] strs) {
    for (String s : strs)
      System.out.println(s);
  }

  private void println(String str) {
    System.out.println(str);
  }

  public Solenoid(PApplet p_applet, String portName, boolean do_hit) {
    this.do_hit = do_hit;
    
    // serial communication with arduino
    //println(Serial.list());
    //String portName = Serial.list()[5];

    // GPIO config
    if (do_hit) {
      try {
        RandomAccessFile f = new RandomAccessFile("/sys/class/gpio/unexport", "rw");
        f.writeByte((byte)'1');
        f.writeByte((byte)'8');
        f.close();        

        f = new RandomAccessFile("/sys/class/gpio/export", "rw");
        f.writeByte((byte)'1');
        f.writeByte((byte)'8');
        f.close();        
      }
      catch (FileNotFoundException e) {
        System.out.println("FileNotFoundException: " + e);
      }
      catch (IOException e) {
        System.out.println("IOException: " + e);
      }              
      // this.arduinoPort = new Serial(p_applet, portName, 9600);
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

  public void hit() {
    if (this.do_hit) {
      System.out.println("Solenoid hit!");
      // this.arduinoPort.write(255);
      try {
        RandomAccessFile f = new RandomAccessFile("/sys/class/gpio/gpio18/value", "rw");
        f.writeByte((byte)'1');
        f.close();        

        this.delay();

        f = new RandomAccessFile("/sys/class/gpio/gpio18/value", "rw");
        f.writeByte((byte)'0');
        f.close();        
      }
      catch (FileNotFoundException e) {
        System.out.println("FileNotFoundException: " + e);
      }
      catch (IOException e) {
        System.out.println("IOException: " + e);
      }
    } else {
      System.out.println("Solenoid simulated hit!");
    }
  }
}

