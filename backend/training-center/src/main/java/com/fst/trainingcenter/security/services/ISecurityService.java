package com.fst.trainingcenter.security.services;




import com.fst.trainingcenter.security.dtos.LoginRequest;
import com.fst.trainingcenter.security.dtos.LoginResponse;
import com.fst.trainingcenter.security.entities.AppRole;
import com.fst.trainingcenter.security.entities.AppUser;

import java.util.Collection;
import java.util.List;

public interface ISecurityService {
    AppUser addNewUser(AppUser appUser);
    AppUser getAppUserById(long id);

    AppUser findUserByEmail(String email);

    AppRole findRoleByRoleName(String role);
    AppUser updateRoleUser(AppUser appUser);
    AppUser addRoleToUser(String roleName, String email);
    AppRole addNewRole(AppRole appRole);
    List<AppUser> userList();

    List<AppRole> roleList();


    LoginResponse authenticate(LoginRequest loginRequest);

    LoginResponse refreshToken(String refreshToken);

    Collection<AppRole> getRolesUser(long id);
    void deleteUser(AppUser appUser);
}
