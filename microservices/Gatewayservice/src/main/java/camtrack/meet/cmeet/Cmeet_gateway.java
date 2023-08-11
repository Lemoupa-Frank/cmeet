package camtrack.meet.cmeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// Everything is as needed
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class Cmeet_gateway {

	public static void main(String[] args) {
		SpringApplication.run(Cmeet_gateway.class, args);
	}

}