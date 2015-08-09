// Solenoid tweets
// Under CC0 1.0 Universal (CC0 1.0) - Public Domain Dedication
//
// By:
// Miguel Colom - http://mcolom.info/
// Julia Puyo - http://juliapuyo.com/

import processing.*;
import processing.core.*;
import processing.serial.*; // serial communication library

public class Solenoid {
    private Serial arduinoPort; // serial port object
    private boolean do_hit;

    private void println(String[] strs) {
        for (String s: strs)
            System.out.println(s);
    }

    private void println(String str) {
        System.out.println(str);
    }

    public Solenoid(PApplet p_applet, boolean do_hit) {
        this.do_hit = do_hit;

        // serial communication with arduino
        println(Serial.list());
        String portName = Serial.list()[5];
        this.arduinoPort = new Serial(p_applet, portName, 9600);
    }

    public void hit() {
        if (this.do_hit) {
            System.out.println("Solenoid hit!");
            this.arduinoPort.write(255);
        }
        else {
            System.out.println("Solenoid simulated hit!");
        }
    }
}
