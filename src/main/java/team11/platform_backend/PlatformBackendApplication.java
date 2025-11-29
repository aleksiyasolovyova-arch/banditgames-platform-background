package team11.platform_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.modulith.Modulith;
import org.springframework.scheduling.annotation.EnableScheduling;

@Modulith
@EnableScheduling
public class PlatformBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatformBackendApplication.class, args);
	}

}
