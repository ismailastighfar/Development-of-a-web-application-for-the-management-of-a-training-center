package com.fst.trainingcenter.security.web;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fst.trainingcenter.security.Util.JwtUtil;
import com.fst.trainingcenter.security.entities.AppRole;
import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.security.exceptions.InvalidJwtTokenException;
import com.fst.trainingcenter.security.services.ISecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static com.fst.trainingcenter.security.Util.JwtUtil.AUTH_HEADER;


@RestController
@CrossOrigin("*")
public class SecurityRestController {
    private ISecurityService securityService;

    public SecurityRestController(ISecurityService securityService) {
        this.securityService = securityService;
    }


    @GetMapping(path = "/users")
    public List<AppUser> getAllUsers(){
        return securityService.userList();
    }

    @GetMapping("/roles")
    public List<AppRole> getAllRoles(){
        return securityService.roleList();
    }

   /* @GetMapping("/users/{email}")
    public AppUser getUser(@PathVariable String email){
        return securityService.findUserByEmail(email);
    }
*/
    @GetMapping("/roleUser/{id}")
    public Collection<AppRole > getRoleUser(@PathVariable long id){
        return securityService.getRolesUser(id);
    }

    @PostMapping("/users")
    public AppUser addUser(@RequestBody AppUser user){
        return securityService.addNewUser(user);
    }

    @PostMapping("/roles")
    public AppRole addRole(@RequestBody AppRole role){
        return securityService.addNewRole(role);
    }

    @PostMapping("/formUserRole")
    public AppUser addRoleUser(@RequestBody RoleUserForm roleUserForm){
        return securityService.addRoleToUser(roleUserForm.getRoleName(),roleUserForm.getEmail());
    }
    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("refreh token ...");
        String authorizationToken = request.getHeader(AUTH_HEADER);
        if(authorizationToken!=null && authorizationToken.startsWith(JwtUtil.PREFIX)) {
            try {
                String jwt = authorizationToken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(JwtUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String userEmail = decodedJWT.getSubject();
                AppUser appUser = securityService.findUserByEmail(userEmail);
                String jwtAccessToken = JWT.create()
                        .withSubject(appUser.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", appUser.getUserRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> idToken = new HashMap<>();
                idToken.put("access-token", jwtAccessToken);
                idToken.put("refresh-token", jwt);
                response.setContentType("application/json");
                response.setHeader(AUTH_HEADER, jwtAccessToken);
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);

            } catch (Exception e) {
                throw new InvalidJwtTokenException("Invalid jwt token");
            }
        }else {
            throw new RuntimeException("refresh token required");
        }
    }
    @GetMapping(path = "/profile")
    public AppUser profile(Principal principal){
        AppUser user=securityService.findUserByEmail(principal.getName());
        return  user;
    }
    @GetMapping(path = "/users/{id}")
    public AppUser getUserById(@PathVariable("id") long id){

        return securityService.getAppUserById(id);
    }




}
@Data
class RoleUserForm{
    private String email;
    private String roleName;
}
