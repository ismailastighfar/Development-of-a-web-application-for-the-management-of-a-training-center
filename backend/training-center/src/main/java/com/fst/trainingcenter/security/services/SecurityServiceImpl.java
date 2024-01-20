package com.fst.trainingcenter.security.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fst.trainingcenter.security.dtos.LoginRequest;
import com.fst.trainingcenter.security.dtos.LoginResponse;
import com.fst.trainingcenter.security.entities.AppRole;
import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.security.exceptions.IncorrectCredentialsException;
import com.fst.trainingcenter.security.repositories.AppRoleRepository;
import com.fst.trainingcenter.security.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.fst.trainingcenter.security.Util.JwtUtil.*;


@Service
@Transactional
public class SecurityServiceImpl implements ISecurityService {
    private AppRoleRepository appRoleRepository;
    private AppUserRepository<AppUser> appUserRepository;
    private PasswordEncoder passwordEncoder;

    public SecurityServiceImpl(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appRoleRepository = appRoleRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser addNewUser(AppUser appUser) {
        String password = appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(password));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser getAppUserById(long id) {
        return appUserRepository.findAppUserById(id);
    }

    @Override
    public AppUser findUserByEmail(String email){
        return appUserRepository.findAppUserByEmail(email);
    }
    @Override
    public AppRole findRoleByRoleName(String role){
        return appRoleRepository.findAppRoleByRoleName(role);
    }

    @Override
    public AppUser addRoleToUser(String roleName, String email) {
        AppUser user = appUserRepository.findAppUserByEmail(email);
        AppRole role  = appRoleRepository.findAppRoleByRoleName(roleName);
        user.getUserRoles().add(role);
        return user;
    }
    @Override
    public AppUser updateRoleUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public List<AppUser> userList() {
        return appUserRepository.findAll();
    }
    @Override
    public List<AppRole> roleList(){
        return appRoleRepository.findAll();
    }

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        System.out.println("authenticate...");
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        AppUser user = appUserRepository.findAppUserByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectCredentialsException();
        }
        String accessToken = AccessToken(user);
        String refreshToken = RefreshToken(user);
        return new LoginResponse(accessToken, refreshToken);
    }
    @Override
    public LoginResponse refreshToken(String refreshToken) {
        System.out.println("refreshToken1...");
        String userId = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(refreshToken).getSubject();
        AppUser user = appUserRepository.findById(Long.valueOf(userId)).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        String newAccessToken = AccessToken(user);
        return new LoginResponse(newAccessToken, refreshToken);
    }

    @Override
    public Collection<AppRole> getRolesUser(long id) {

        Optional<AppUser> user = this.appUserRepository.findById(id);

        if (user.isPresent()) {
            Collection<AppRole> userRoles = user.get().getUserRoles();
            return userRoles;
        } else {
            Collection<AppRole> roles= new ArrayList<>();
            return roles;
        }

    }


    public String AccessToken(AppUser user) {
        System.out.println("AccessToken....");
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_ACCESS_TOKEN))
                .withClaim("roles", user.getUserRoles()
                        .stream()
                        .map(
                                AppRole::getRoleName
                        ).toList())
                .sign(Algorithm.HMAC256(SECRET));
    }

    public String RefreshToken(AppUser user) {
        System.out.println("refreshToken2..");
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_REFRESH_TOKEN))
                .sign(Algorithm.HMAC256(SECRET));
    }

    @Override
    public void deleteUser(AppUser appUser) {
        appUserRepository.delete(appUser);
    }
}
