package org.yansou.ci.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.yansou.ci.core.db.model.system.Account;

import java.util.Collection;

/**
 * @author liutiejun
 * @create 2017-06-25 18:36
 */
public class CustomUserDetails implements UserDetails {

	private Account account;

	public CustomUserDetails() {
	}

	public CustomUserDetails(Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * 返回用户所拥有的权限
	 *
	 * @return
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("admin", "user");
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getUsername();
	}

	/**
	 * 帐号是否不过期
	 *
	 * @return
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 帐号是否不锁定
	 *
	 * @return
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 凭证是否不过期
	 *
	 * @return
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 帐号是否启用
	 *
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		Boolean disabled = account.getDisabled();// true：账号禁用，false：账号可用
		if (disabled == null) {
			return true;
		}

		return !disabled;
	}
}
