package takred.weightcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class WeightControlApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
//		SpringApplication.run(WeightControlApplication.class, args);
		SpringApplication springApplication = new SpringApplication(WeightControlApplication.class);
		springApplication.addListeners(new ApplicationPidFileWriter());
		springApplication.run();
	}

}
