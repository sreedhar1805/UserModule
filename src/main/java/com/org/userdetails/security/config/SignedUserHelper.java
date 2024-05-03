package com.org.userdetails.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SignedUserHelper {

	public static SignedUser user() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		try {

			return ((SignedUser) auth.getPrincipal());

		} catch (ClassCastException e) {
			SignedUser testUser = new SignedUser();
			return testUser;
		}

	}

	public static Long userId() {
		return user().getUserId();
	}
}
