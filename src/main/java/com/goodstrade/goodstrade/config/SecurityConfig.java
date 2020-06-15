package com.goodstrade.goodstrade.config;

import com.goodstrade.goodstrade.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JpaAuthenticationProvider jpaAuthenticationProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(jpaAuthenticationProvider);
	}


	@Override
	public  void configure(WebSecurity http) throws Exception {
		http.ignoring().antMatchers("/filedownload/**","/plugins/**","/styles/**","/js/**","/images/**","/fonts/**","/img/**","/css/**","/sass/**","/Source/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.formLogin().permitAll().defaultSuccessUrl("/", true)
				.loginPage("/login").permitAll()
				.and()
				.authorizeRequests()
				.antMatchers("/createadmin1", "/createuser1","/createseller1", "/test1", "/all","/register").permitAll()
				.antMatchers("/guestonly").anonymous()
				.antMatchers("/adminonly").hasAnyAuthority(Role.ROLE_ADMIN.getVal())
				.anyRequest().authenticated()
				.and()
				.csrf().disable()
				.httpBasic().disable()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}