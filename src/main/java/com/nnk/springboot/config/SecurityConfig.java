package com.nnk.springboot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nnk.springboot.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	UserService userService;

	@Autowired
	protected void configAuthetication(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder pe = new BCryptPasswordEncoder();

		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(pe)
				.usersByUsernameQuery("select username, password, enabled FROM users WHERE username=?")
				.authoritiesByUsernameQuery("select username, role from users where username=?");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/home").permitAll().and().authorizeRequests().anyRequest().authenticated()
				.and().formLogin().defaultSuccessUrl("/admin/home", true).failureUrl("/login-error").and().oauth2Login()
				.defaultSuccessUrl("/admin/home", true);
//		http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
//				.defaultSuccessUrl("/admin/home", true).failureUrl("/login-error").and().logout().permitAll().and().oauth2Login();
		http.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
