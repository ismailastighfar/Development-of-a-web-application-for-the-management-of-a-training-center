package com.fst.trainingcenter.security.repositories;




import com.fst.trainingcenter.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository<T extends AppUser> extends JpaRepository<T, Long> {
    AppUser findAppUserByEmail(String email);
    AppUser findAppUserById(Long id);

}
