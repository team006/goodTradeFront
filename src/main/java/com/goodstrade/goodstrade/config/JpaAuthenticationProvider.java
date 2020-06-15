package com.goodstrade.goodstrade.config;

import com.goodstrade.goodstrade.entity.User;
import com.goodstrade.goodstrade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class JpaAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private UserRepository userRepository;

	/**
	 * Performs authentication with the same contract as
	 * {@link AuthenticationManager#authenticate(Authentication)}
	 * .
	 *
	 * @param authentication the authentication request object.
	 * @return a fully authenticated object including credentials. May return
	 * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
	 * authentication of the passed <code>Authentication</code> object. In such a case,
	 * the next <code>AuthenticationProvider</code> that supports the presented
	 * <code>Authentication</code> class will be tried.
	 * @throws AuthenticationException if authentication fails.
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		String username = String.valueOf(token.getPrincipal());
		String password = String.valueOf(token.getCredentials());

		User userAccount = userRepository.findByUsername(username);

		try {
			if (userAccount == null || (!BCrypt.checkpw(password, userAccount.getPassword()))){
				throw new BadCredentialsException("ผู้ใช้งานหรือรหัสผ่านผิดพลาด");
			}
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("The password in database is not encoded properly.");
		}

		return new UsernamePasswordAuthenticationToken(userAccount, userAccount.getPassword(), userAccount.getAuthorities());

	}

	/**
	 * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
	 * indicated <Code>Authentication</code> object.
	 * <p>
	 * Returning <code>true</code> does not guarantee an
	 * <code>AuthenticationProvider</code> will be able to authenticate the presented
	 * instance of the <code>Authentication</code> class. It simply indicates it can
	 * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
	 * return <code>null</code> from the {@link #authenticate(Authentication)} method to
	 * indicate another <code>AuthenticationProvider</code> should be tried.
	 * </p>
	 * <p>
	 * Selection of an <code>AuthenticationProvider</code> capable of performing
	 * authentication is conducted at runtime the <code>ProviderManager</code>.
	 * </p>
	 *
	 * @param authentication
	 * @return <code>true</code> if the implementation can more closely evaluate the
	 * <code>Authentication</code> class presented
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}
}
