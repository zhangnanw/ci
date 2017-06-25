package org.yansou.ci.web.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.yansou.ci.core.db.model.system.Account;
import org.yansou.ci.web.business.system.AccountBusiness;

import java.util.Collection;

/**
 * 自定义安全验证
 *
 * @author liutiejun
 * @create 2017-06-25 18:04
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger LOG = LogManager.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private AccountBusiness accountBusiness;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		Object credentials = authentication.getCredentials();

		LOG.info("username: {}, password: {}", username, credentials);

		Account account = accountBusiness.findByUsername(username);

		if (account == null) {
			throw new UsernameNotFoundException("非法的用户名：" + username);
		}

		LOG.info("account: {}", account);

		UserDetails userDetails = new CustomUserDetails(account);
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

		return new UsernamePasswordAuthenticationToken(userDetails, credentials, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
