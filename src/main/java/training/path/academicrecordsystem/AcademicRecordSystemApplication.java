package training.path.academicrecordsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({ @ComponentScan("training.path.academicrecordsystem.controllers.implementations"),
		@ComponentScan("training.path.academicrecordsystem.security.WebSecurityConfiguration") })
public class AcademicRecordSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademicRecordSystemApplication.class, args);
	}

}
