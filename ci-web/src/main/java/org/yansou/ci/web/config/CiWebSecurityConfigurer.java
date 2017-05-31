package org.yansou.ci.web.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author liutiejun
 * @create 2017-04-14 14:30
 */
@EnableWebSecurity
public class CiWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
				.authorizeRequests()
					.antMatchers("/login/**").permitAll()
					.anyRequest().authenticated()
					.and()
				.formLogin()
					// The URL we submit our username and password to is the same URL as our login form (i.e. /login)
				    // but a POST instead of a GET
					.loginPage("/login/show").permitAll()
					.defaultSuccessUrl("/login/success")
					.failureUrl("/login/failure")
					.and()
				.logout().logoutUrl("/login/logout")
					.permitAll();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 配置无需安全检查的路径
		web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/font-awesome/**", "/fonts/**", "/**/favicon" +
				".ico");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.inMemoryAuthentication()
				.withUser("admin").password("admin").roles("ADMIN", "USER")
				.and().withUser("xiuleiliu").password("xiuleiliu").roles("USER").and().withUser("yinggao").password
				("yinggao").roles("USER").and().withUser("panshuwen").password("panshuwen").roles("USER").and()
				.withUser("user").password("user").roles("USER");
	}

}
