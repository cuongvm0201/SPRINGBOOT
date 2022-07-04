package vn.techmaster.finalproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //https://stackoverflow.com/questions/3634853/how-to-create-a-directory-in-java
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry
                .addMapping("/**")
                .allowedMethods("GET","POST","PUT","DELETE");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File theDir = new File("file:/firstphotos");
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        registry.addResourceHandler("/firstphotos/**").addResourceLocations("file:firstphotos/");
    }
}