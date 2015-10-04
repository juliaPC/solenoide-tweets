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
        DataOutputStream os = new DataOutputStream(new FileOutputStream("/sys/class/gpio/export"));
        os.writeByte(18); // pin number
        os.close();

        os = new DataOutputStream(new FileOutputStream("/sys/class/gpio/gpio18/direction"));
        os.writeChars("out"); // pin mode
        os.close();
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
        DataOutputStream os = new DataOutputStream(new FileOutputStream("/sys/class/gpio/gpio18/value"));
        os.writeByte(1); // solenoid push 
        this.delay();
        os.writeByte(0); // solenoid pull
        os.close();
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

