package fr.uniamu.ibdm.gsa_server.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId;

  private String userName;
  private String userEmail;
  private byte[] userPassword;
  private byte[] salt;
  private boolean isAdmin;

  @OneToMany(mappedBy = "user")
  private Collection<Member> members;

  public User() {
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public byte[] getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(byte[] userPassword) {
    this.userPassword = userPassword;
  }

  public byte[] getSalt() {
    return salt;
  }

  public void setSalt(byte[] salt) {
    this.salt = salt;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  public Collection<Member> getMembers() {
    return members;
  }

  public void setMembers(Collection<Member> members) {
    this.members = members;
  }
}
