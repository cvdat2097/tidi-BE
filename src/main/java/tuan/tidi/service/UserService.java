package tuan.tidi.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tuan.tidi.entity.Accounts;
import tuan.tidi.repository.account.AccountsRepository;

@Service(value = "userService")
public class UserService implements UserDetailsService{
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Accounts account = accountsRepository.findByUsernameLike(username);
		if(account == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), getAuthority(account));
	}

	private Set<SimpleGrantedAuthority> getAuthority(Accounts account) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + account.getPermission()));
		return authorities;
	}
}
