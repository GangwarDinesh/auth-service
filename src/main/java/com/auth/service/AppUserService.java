package com.auth.service;

import com.auth.entity.User;

public interface AppUserService {

	public User getUserDetails(String username, String password);
}
