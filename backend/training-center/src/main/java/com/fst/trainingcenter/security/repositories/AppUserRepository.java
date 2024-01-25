package com.fst.trainingcenter.security.repositories;




import com.fst.trainingcenter.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository<T extends AppUser> extends JpaRepository<T, Long> {
    AppUser findAppUserByEmail(String email);
    AppUser findAppUserById(Long id);
    List<AppUser> findByUserRoles_RoleName(String roleName);

}
