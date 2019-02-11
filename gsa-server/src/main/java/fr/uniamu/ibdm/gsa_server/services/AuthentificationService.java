package fr.uniamu.ibdm.gsa_server.services;

import fr.uniamu.ibdm.gsa_server.models.User;

import java.util.Map;

public interface AuthentificationService {

  User login(String email, byte password);

  User register(Map<String, String> userInfo);

}
