package net.gesundheitsforen.messageListener.user;

public interface UserDetailsDao {
    User findUserByUsername(String username);
}
