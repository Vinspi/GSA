package fr.uniamu.ibdm.gsa_server.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uniamu.ibdm.gsa_server.conf.MaintenanceBean;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MaintenanceInterceptor implements HandlerInterceptor {

  @Autowired
  MaintenanceBean maintenanceBean;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    if (maintenanceBean.isMaintenanceMode()){
      JsonResponse<Boolean> res = new JsonResponse<>(RequestStatus.MAINTENANCE);
      ObjectMapper mapper = new ObjectMapper();
      response.getWriter().write(mapper.writeValueAsString(res));
      return false;
    }

    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

  }
}
