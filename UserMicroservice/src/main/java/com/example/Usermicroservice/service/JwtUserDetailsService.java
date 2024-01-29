package com.example.Usermicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Usermicroservice.model.UserDao;
import com.example.Usermicroservice.model.UserDto;
import com.example.Usermicroservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;
 
@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userDao;
 
	@Autowired
	private PasswordEncoder bcryptEncoder;
 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDao user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
 
	public UserDao save(UserDto user) {
		System.out.println(user);
		UserDao newUser = new UserDao();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setFirstname(user.getFirstname());
		newUser.setLastname(user.getLastname());
		newUser.setEmail(user.getEmail());
		newUser.setContact(user.getContact());
		newUser.setRole(user.getRole());
		return userDao.save(newUser);
	}
	public String getrole(String user) {
		UserDao usd = new UserDao();
		usd = userDao.findByUsername(user);
		return usd.getRole();
	}
	public Optional<UserDao> getUser(long id)
	{
		Optional<UserDao> user= Optional.ofNullable(userDao.findByid(id));
		if(!user.isPresent())
		{
			throw new UsernameNotFoundException("User not found with username: " + id);
		}
		return user;
	}

	public UserRepository getUserDao() {
		return userDao;
	}

	public void setUserDao(UserRepository userDao) {
		this.userDao = userDao;
	}
}
