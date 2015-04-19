package launch;

import java.io.File;
import java.util.logging.*;
import java.io.*;
import org.apache.catalina.startup.Tomcat;

public class Main {

    public static void main(String[] args) throws Exception {
    	
 

        String webappDirLocation = "/src/main/webapp/";
        Tomcat tomcat = new Tomcat();

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
        
    	Logger logger = Logger.getLogger("/src/com/scheduler/logging/Project4.log");  
        FileHandler fh;  
        
        try {  

            // This block configure the logger with handler and formatter  
            fh = new FileHandler("/src/com/scheduler/logging/Project4.log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  

            // the following statement is used to log any messages  
            logger.info("Main log for Project 4 - CS6310 - Class Scheduler");  
            logger.log(Level.SEVERE,"Program started");
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
    }
}
