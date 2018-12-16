package com.bojan.teamvote.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring security implementation. Uses BCrypt bean to encode passwords
 * @author Bojan
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(
						"select email as principal, password as credentails, true from user where email=?")
				.authoritiesByUsernameQuery(
						"select user_email as principal, role_email as role from user_roles where user_email=?")
				.passwordEncoder(passwordEncoder()).rolePrefix("ROLE_");
	}

	@Override 
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
			.antMatchers("/register", "/", "/about", "/login", "/css/**", "/webjars/**").permitAll()
			.antMatchers("/vote","/vote/**").hasAnyRole("USER,ADMIN")
			.antMatchers("/profile/**", "/profile/test").hasAnyRole("USER,ADMIN")
			.and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/profile").and().logout().logoutUrl("/logout")  
				.logoutSuccessUrl("/login");;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
