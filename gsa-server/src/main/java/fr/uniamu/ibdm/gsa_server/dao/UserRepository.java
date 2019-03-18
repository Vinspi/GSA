package fr.uniamu.ibdm.gsa_server.dao;

import fr.uniamu.ibdm.gsa_server.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  User findByUserEmail(String email);

  @Query("SELECT u.userEmail FROM User u WHERE isAdmin = true")
  List<String> findAllAdminEmail();

  User findByUserName(String userName);

}
