package com.sda.mvc.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/",
                        "/index",
                        "/users",
                        "/adduser")
                    .hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll().and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("login")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login-process")
                    .defaultSuccessUrl("/index")
                .and()
                    .logout()
                    .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                    .password(passwordEncoder.encode("test"))
                    .roles("USER");

        auth.jdbcAuthentication()
                .usersByUsernameQuery(
                        "select u.login, u.password, 1 from user_dao u where u.login=?")
                .authoritiesByUsernameQuery(
                        "select u.login, 'ROLE_USER' from user_dao u where u.login=?")
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }
}
