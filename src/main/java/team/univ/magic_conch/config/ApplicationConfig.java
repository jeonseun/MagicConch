package team.univ.magic_conch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Value("${custom.file.path}")
    private String path;

    @Value("${custom.file.location}")
    private String location;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(path + "/**")
                .addResourceLocations("file:///" + location + "/")
                .setCachePeriod(3600);
    }

}
