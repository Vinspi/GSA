package fr.uniamu.ibdm.gsa_server.conf;

import fr.uniamu.ibdm.gsa_server.interceptors.AdminInterceptor;
import fr.uniamu.ibdm.gsa_server.interceptors.CorsInterceptor;
import fr.uniamu.ibdm.gsa_server.interceptors.LoginInterceptor;
import fr.uniamu.ibdm.gsa_server.interceptors.MaintenanceInterceptor;
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

  @Autowired
  MaintenanceInterceptor maintenanceInterceptor;

  @Autowired
  CorsInterceptor corsInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(corsInterceptor);
    registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/*");
    registry.addInterceptor(maintenanceInterceptor)
        .excludePathPatterns("/admin/setupMaintenanceMode")
        .excludePathPatterns("/auth/*")
        .excludePathPatterns("/isMaintenanceMode");
    registry.addInterceptor(loginInterceptor)
        .addPathPatterns("/withdrawCart")
        .addPathPatterns("/history");
  }
}
