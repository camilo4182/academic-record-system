package training.path.academicrecordsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.FilterType;
import training.path.academicrecordsystem.config.ExcludeSecurityAutoConfigurationFilter;

@SpringBootApplication
/*@ComponentScan(excludeFilters = @ComponentScan.Filter(
		type = FilterType.CUSTOM, classes = {ExcludeSecurityAutoConfigurationFilter.class}
))*/
public class AcademicRecordSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademicRecordSystemApplication.class, args);
	}

}
