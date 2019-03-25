package fr.uniamu.ibdm.gsa_server.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:4200", "http://localhost",
    "http://51.77.147.140" })
public class AdminInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    User user = (User) request.getSession().getAttribute("user");


    if (request.getMethod().equals("OPTIONS")) {

      return true;
    }

    if (user == null || !user.isAdmin()){
      JsonResponse<Boolean> res = new JsonResponse<>("not allowed", RequestStatus.FAIL);
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
