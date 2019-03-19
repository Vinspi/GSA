package fr.uniamu.ibdm.gsa_server.conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class MaintenanceInterceptor extends HandlerInterceptorAdapter {
  @Autowired
  MaintenanceBean maintenanceBean;

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception exception) throws Exception {
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (maintenanceBean.isMaintenanceMode()) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Currently in maintenance");
      return false;
    }

    return true;
  }
  
}