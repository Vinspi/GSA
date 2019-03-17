package fr.uniamu.ibdm.gsa_server.conf;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;

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
    System.out.println("interceptor#afterConcurrentHandlingStarted called. " +
        "Thread: " +
        Thread.currentThread()
              .getName());
    if (maintenanceBean.isMaintenanceMode()) {
      System.out.println("Blocking");
      /*response.setContentType("application/json");
      PrintWriter out = response.getWriter();
      ObjectMapper mapper = new ObjectMapper();
      JsonResponse<Void> jsonRes = new JsonResponse<>(RequestStatus.FAIL);
      String jsonString = mapper.writeValueAsString(jsonRes);
      out.print(new JSONObject(jsonString));
      out.flush();*/
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Currently in maintenance");
      return false;
    }

    return true;
  }
  
  @Override
  public void afterConcurrentHandlingStarted (HttpServletRequest request,
                      HttpServletResponse response,
                      Object handler) throws Exception {

      System.out.println("interceptor#afterConcurrentHandlingStarted called. " +
                                             "Thread: " +
                                             Thread.currentThread()
                                                   .getName());
  }

}