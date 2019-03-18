package fr.uniamu.ibdm.gsa_server.services.impl;

import fr.uniamu.ibdm.gsa_server.conf.CustomConfig;
import fr.uniamu.ibdm.gsa_server.dao.QueryObjects.TriggeredAlertsQuery;
import fr.uniamu.ibdm.gsa_server.dao.UserRepository;
import fr.uniamu.ibdm.gsa_server.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

  private JavaMailSender emailSender;
  private AdminServiceImpl adminService;
  private UserRepository userRepository;

  /**
   * Constructor for the EmailServiceImpl service.
   *
   * @param emailSender  autowired property.
   * @param adminService autowired property.
   * @param userRepository autowired property.
   */
  @Autowired
  public EmailServiceImpl(JavaMailSender emailSender, AdminServiceImpl adminService, UserRepository userRepository) {
    this.emailSender = emailSender;
    this.adminService = adminService;
    this.userRepository = userRepository;
  }

  @Override
  @Scheduled(cron = "0 0 0 ? * MON *")
  public void sendAlertMessage() {



    StringBuilder sb = new StringBuilder();

    sb.append("Hello,\r");
    sb.append("\r");
    sb.append("here is all the triggered alerts : \r\r");
    List<TriggeredAlertsQuery> alertList = adminService.getTriggeredAlerts();

    alertList.forEach(alert -> {
      sb.append("Alert type : " + alert.getAlertType() + ",   product : " + alert.getSource() + "_ANTI_" + alert.getTarget() + "\r");
      sb.append("quantity left is " + alert.getQte() + " but alert limit is " + alert.getSeuil() + "\n");
      sb.append("All aliquots concerned by this alert are (nlot, quantity, exp date) : \n");
      sb.append("\n");
      alert.getAliquots().forEach(aliquot -> {
        sb.append("    " + aliquot.getNlot() + "   " + aliquot.getQte() + "   " + aliquot.getExpirationDate() + "\n");
      });
      sb.append("\r\r");
    });

    for (String address : userRepository.findAllAdminEmail()) {
      SimpleMailMessage message = new SimpleMailMessage();

      message.setTo(address);
      message.setSubject("[GSA] Alerts ");
      message.setText(sb.toString());

      if (!alertList.isEmpty()) {
        emailSender.send(message);
      }
    }

  }
}
