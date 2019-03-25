package fr.uniamu.ibdm.gsa_server.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.requests.JsonResponse;
import fr.uniamu.ibdm.gsa_server.requests.RequestStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    User user = (User) request.getSession().getAttribute("user");

    if (request.getMethod().equals("OPTIONS")) {

      return true;
    }

    if (user == null){
      JsonResponse<Boolean> res = new JsonResponse<>("please log in", RequestStatus.FAIL);
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
