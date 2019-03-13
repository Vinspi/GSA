package fr.uniamu.ibdm.gsa_server.util;

import java.time.LocalDate;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

@Component
public class TimeFactory {
  
  /**
   * Gets current date using UTC timezone.
   * 
   * @return the current date using UTC timezone.
   */
  public LocalDate now() {
    return LocalDate.now(ZoneOffset.UTC);
  }
}