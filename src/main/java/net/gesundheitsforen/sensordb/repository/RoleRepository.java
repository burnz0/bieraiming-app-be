package net.gesundheitsforen.sensordb.repository;

import net.gesundheitsforen.sensordb.model.Role;
import net.gesundheitsforen.sensordb.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
