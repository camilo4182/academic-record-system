package training.path.academicrecordsystem.config;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Objects;

public class ExcludeSecurityAutoConfigurationFilter implements TypeFilter, EnvironmentAware {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        boolean match = false;
        for (String activeProfile : env.getActiveProfiles()) {
            if (Objects.equals(activeProfile, "test")) {
                match = Objects.equals(metadataReader.getClassMetadata().getClassName(), SecurityAutoConfiguration.class.getName());
            }
        }
        return match;
    }

}
