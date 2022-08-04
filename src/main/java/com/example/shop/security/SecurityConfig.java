package com.example.shop.security;

import com.example.shop.filter.CustomAuthenticationFilter;
import com.example.shop.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
       customAuthenticationFilter.setFilterProcessesUrl("/api/login");
       http.csrf().disable();
       http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
       http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh**", "/v3/api-docs/**","/swagger-resources/**","/swagger.json","/swagger-ui/**").permitAll();
       http.authorizeRequests().antMatchers(GET, "/api/user/**").authenticated();
       http.authorizeRequests().antMatchers(PUT, "/api/user/**").hasAnyAuthority("ADMIN");
       http.authorizeRequests().antMatchers(DELETE, "/api/user/**").hasAnyAuthority("ADMIN","MANAGER");
       http.authorizeRequests().antMatchers(POST, "/api/user/save/**").hasAnyAuthority("ADMIN");
       http.authorizeRequests().antMatchers(GET, "/api/department/users/**").authenticated();
       http.authorizeRequests().antMatchers(GET, "/api/departments/**").authenticated();
       http.authorizeRequests().antMatchers(POST, "/department/**").hasAnyAuthority("ADMIN");
       http.authorizeRequests().antMatchers(PUT, "/department/**").hasAnyAuthority("ADMIN");
       http.authorizeRequests().antMatchers(DELETE, "/department/**").hasAnyAuthority("ADMIN");
       http.authorizeRequests().anyRequest().authenticated();
       http.addFilter(customAuthenticationFilter);
       http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManager();
    }

}
