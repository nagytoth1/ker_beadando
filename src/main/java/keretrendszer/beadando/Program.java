package keretrendszer.beadando;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class Program {

	public static void main(String[] args) {

		try{
			SpringApplication.run(Program.class, args);
		}catch(Exception exc){
			System.out.println("Hiba történt: " + exc.getLocalizedMessage());
		}
	}

}
