package team.univ.magic_conch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Value("${custom.file.profile-path}")
    private String profileImagePath;

    @Value("${custom.file.profile-location}")
    private String profileImageLocation;

    @Value("${custom.file.tag-image-path}")
    private String tagImagePath;

    @Value("${custom.file.tag-image-location}")
    private String tagImageLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(profileImagePath + "/**")
                .addResourceLocations("file:///" + profileImageLocation + "/")
                .setCachePeriod(3600);

        registry.addResourceHandler(tagImagePath + "/**")
                .addResourceLocations("file:///" + tagImageLocation + "/")
                .setCachePeriod(3600);
    }

}
