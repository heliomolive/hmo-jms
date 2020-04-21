package hmo.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HmoJmsApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(HmoJmsApplication.class, args);
		} catch (Throwable t) {
			new Exception(t).printStackTrace();
		}
	}

}
