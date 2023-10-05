package camtrack.meet.cmeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Everything is as needed
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@ComponentScan
public class Cmeet_gateway {
	private static final String SignatureFolder = "cmeetSignatures";
	private static Path SignaturePAth;
	public static void createSignatureFile() {
		String fullPath =   File.pathSeparator + SignatureFolder;
		Path folderPath = Paths.get(fullPath);
		SignaturePAth = folderPath;
		try {
			Files.createDirectories(folderPath); //if a folder along the path is absent it creates from there
			System.out.println("Folder created successfully.");
		} catch (IOException e) {
			System.out.println("An error occurred while creating the folder: " + e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		SpringApplication.run(Cmeet_gateway.class, args);
		createSignatureFile();
	}

	public static String getSignatureFolder()
	{
		return SignatureFolder;
	}

	public static Path getSignaturePAth() {
		return SignaturePAth;
	}
}