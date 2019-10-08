package de.gesundheitsforen.messageListener.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.java.Log;


@Service
@Log
public class UserService{

  @Autowired
  private UserRepository userRepository;

  public User update(User user){
    return userRepository.save(user);
  }

  public void deleteUser(User user){
    userRepository.delete(user);
  }

  public UserRepository getUserRepository(){
    return this.userRepository;
  }

}