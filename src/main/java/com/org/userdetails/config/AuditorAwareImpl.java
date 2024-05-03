package com.org.userdetails.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.org.userdetails.security.config.SignedUser;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}

		try {
			SignedUser user = (SignedUser) authentication.getPrincipal();

			return Optional.of(user.getUserId().toString());

		} catch (ClassCastException e) {

			try {
				User user = (User) authentication.getPrincipal();

				if (user.getUsername().equals("spring")) {
					return Optional.of("testUser");
				}

				return Optional.of("testUser");

			} catch (ClassCastException e1) {
				return Optional.of("anonymousUser");
			}

		}
	}
}
