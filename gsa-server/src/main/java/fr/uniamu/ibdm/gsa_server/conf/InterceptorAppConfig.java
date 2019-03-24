package fr.uniamu.ibdm.gsa_server.conf;

import fr.uniamu.ibdm.gsa_server.interceptors.AdminInterceptor;
import fr.uniamu.ibdm.gsa_server.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorAppConfig implements WebMvcConfigurer {

  @Autowired
  AdminInterceptor adminInterceptor;

  @Autowired
  LoginInterceptor loginInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/*");
    //registry.addInterceptor(loginInterceptor);
  }
}
