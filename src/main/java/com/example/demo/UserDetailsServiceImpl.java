package com.example.demo;

import com.example.demo.infraestructure.persistence.jpa.UserRepositoryJpa;
import com.example.demo.infraestructure.persistence.model.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepositoryJpa userRepositoryJpa;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepositoryJpa.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No existe usuario"));

        Set<GrantedAuthority> grantList = new HashSet<GrantedAuthority>();

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
        grantList.add(grantedAuthority);

        UserDetails userDetails = (UserDetails) new User(username, user.getPassword(), grantList);

        return userDetails;
    }
}
