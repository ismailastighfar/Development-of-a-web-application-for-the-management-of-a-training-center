package com.fst.trainingcenter.security.Configurations;



import com.fst.trainingcenter.security.entities.AppUser;
import com.fst.trainingcenter.security.filters.JwtAuthenticationFilter;
import com.fst.trainingcenter.security.filters.JwtAuthorizationFilter;
import com.fst.trainingcenter.security.repositories.AppUserRepository;
import com.fst.trainingcenter.security.services.ISecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@CrossOrigin("*")
@Slf4j
public class SecurityConfig {
    private ISecurityService securityService;
    private AppUserRepository<AppUser> appUserRepository;


    public SecurityConfig( ISecurityService securityService,AppUserRepository<AppUser> appUserRepository) {
        this.appUserRepository = appUserRepository;
        this.securityService = securityService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(Customizer.withDefaults());
        httpSecurity.csrf(csrf->csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));
        httpSecurity.csrf(csrf->csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/login")));
        httpSecurity.csrf(csrf->csrf.disable());

        //  httpSecurity.headers(headers -> headers.frameOptions().disable());
        httpSecurity.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll());
        httpSecurity.authorizeHttpRequests(auth->auth.requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
        );
        httpSecurity.addFilter(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration()), securityService,appUserRepository));
        httpSecurity.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                AppUser appUser = securityService.findUserByEmail(email);
                log.info("user = "+appUser);
                log.info("roles "+appUser.getUserRoles());
                return new User(
                        appUser.getEmail(),
                        appUser.getPassword(),
                        appUser.getUserRoles()
                                .stream()
                                .map(gr -> new SimpleGrantedAuthority(gr.getRoleName()))
                                .collect(Collectors.toList())
                );
            }
        };
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @Primary
    AuthenticationConfiguration authenticationConfiguration(){
        return new AuthenticationConfiguration();
    }



}
