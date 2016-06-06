// Solenoid tweets
// Under CC0 1.0 Universal (CC0 1.0) - Public Domain Dedication
//
// By:
// Miguel Colom - http://mcolom.info/
// Julia Puyo - http://juliapuyo.com/

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private Properties prop = new Properties();
    private InputStream input = null;

    public Config(String filename) {

        try {
            input = new FileInputStream(filename);
            // load a properties file
            this.prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
	} // try/catch
    } // public Config()

    public String get(String prop) {
        return this.prop.getProperty(prop);
    }
}

