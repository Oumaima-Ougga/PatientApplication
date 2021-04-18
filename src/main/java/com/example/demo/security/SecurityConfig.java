package com.example.demo.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
	private DataSource dataSource ;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder pwdEncoder=passwordEncoder();
		System.out.println("***********************");
		System.out.println(pwdEncoder.encode("1234"));
		System.out.println("***********************");
		
		
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("SELECT username as principal, password as credentials, active from users where username=?")
		.authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
		.passwordEncoder(pwdEncoder).rolePrefix("ROLE_");
		
		
	/*	auth.inMemoryAuthentication().withUser("user1").password(pwdEncoder.encode("1234")).roles("USER");
		auth.inMemoryAuthentication().withUser("user2").password(pwdEncoder.encode("1234")).roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password(pwdEncoder.encode("1234")).roles("USER","ADMIN");
*/
		}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login");
		http.authorizeRequests().antMatchers("/save**/**","/delete**/**","/form**/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/patients**/**").hasRole("USER");
		http.authorizeRequests().antMatchers("/user/**","/login","/webjars/**").permitAll();
		
		http.authorizeRequests().anyRequest().authenticated();
		http.csrf();
		http.exceptionHandling().accessDeniedPage("/notAuthorized");
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
