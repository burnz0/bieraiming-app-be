package net.gesundheitsforen.messageListener.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;



@Transactional
public interface UserRepository extends CrudRepository<User, Long> {


}
