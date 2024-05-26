package org.example.datacenter.service;

import org.example.datacenter.mapper.DataCenterAdminMapper;
import org.example.datacenter.model.DataCenterAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private DataCenterAdminMapper dataCenterAdminMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DataCenterAdmin admin = dataCenterAdminMapper.findByName(username);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        String encryptedPassword = passwordEncoder.encode(admin.getPassword()); // 加密密码

        return User.builder()
                .username(admin.getName())
                .password(encryptedPassword) // 使用加密后的密码
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities("admin")
                .build();
    }
}