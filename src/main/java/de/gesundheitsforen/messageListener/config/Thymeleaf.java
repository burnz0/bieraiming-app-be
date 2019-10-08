package de.gesundheitsforen.messageListener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Configuration
public class Thymeleaf implements WebMvcConfigurer{
  
  @Bean
  @Description("Thymeleaf template resolver serving String")
  public StringTemplateResolver templateResolver() {
      
    StringTemplateResolver templateResolver = new StringTemplateResolver();
      
      templateResolver.setCacheable(false);
      templateResolver.setTemplateMode(TemplateMode.HTML);
      return templateResolver;
  }

  @Bean
  @Description("Thymeleaf template engine with Spring integration")
  public SpringTemplateEngine templateEngine() {
      
      SpringTemplateEngine templateEngine = new SpringTemplateEngine();
      templateEngine.setTemplateResolver(templateResolver());

      return templateEngine;
  }

}
