package br.com.bruno.ecommerce.service;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.bruno.ecommerce.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}