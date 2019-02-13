package fr.uniamu.ibdm.gsa_server.services.impl;

import fr.uniamu.ibdm.gsa_server.dao.TeamRepository;
import fr.uniamu.ibdm.gsa_server.dao.UserRepository;
import fr.uniamu.ibdm.gsa_server.models.Team;
import fr.uniamu.ibdm.gsa_server.models.User;
import fr.uniamu.ibdm.gsa_server.services.UserService;
import fr.uniamu.ibdm.gsa_server.util.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private TeamRepository teamRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, TeamRepository teamRepository) {
    this.userRepository = userRepository;
    this.teamRepository = teamRepository;
  }

  @Override
  public User registerAccount(String name, String email, String password, String teamName, boolean isAdmin) {

    Team team = teamRepository.findByTeamName(teamName);
    User u = userRepository.findByUserEmail(email);

    if (team == null || u != null) {
      /* the team does not exists or the email is already used */
      return null;
    }

    byte[] seed = SecureRandom.getSeed(128);
    byte[] passwordHash = Crypto.hashPassword(seed, password.getBytes());

    User user = new User();
    user.setUserPassword(passwordHash);
    user.setSalt(seed);
    user.setUserName(name);
    user.setUserEmail(email);
    user.setAdmin(isAdmin);
    user.setUserTeam(team);

    return userRepository.save(user);
  }

  @Override
  public User login(String email, String password) {

    User user = userRepository.findByUserEmail(email);

    if (user == null) {
      return null;
    }

    byte[] passHash = Crypto.hashPassword(user.getSalt(), password.getBytes());

    if (Arrays.equals(passHash, user.getUserPassword())) {
      return user;
    } else {
      return null;
    }
  }
}
