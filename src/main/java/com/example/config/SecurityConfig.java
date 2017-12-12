package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/", "/login", "/login-error").permitAll()
		.antMatchers("/**").hasRole("USER")
		.and()
		.formLogin()
		.loginPage("/login").failureUrl("/login-error");
		http.formLogin().loginProcessingUrl("/login").loginPage("/signin")
		.failureUrl("?error").defaultSuccessUrl("/", false)
		.usernameParameter("loginId").passwordParameter("password")
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("signout"))
		.logoutSuccessUrl("/signin")
		.deleteCookies("JSESSIONID")
		.invalidateHttpSession(true).permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.inMemoryAuthentication()
		.withUser("user").password("password").roles("USER");
	}

}