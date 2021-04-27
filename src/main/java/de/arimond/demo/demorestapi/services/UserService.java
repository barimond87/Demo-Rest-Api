package de.arimond.demo.demorestapi.services;

import de.arimond.demo.demorestapi.persistence.dao.IApiUserDao;
import de.arimond.demo.demorestapi.persistence.dao.impl.ApiUserDao;
import de.arimond.demo.demorestapi.persistence.dto.UserDto;
import de.arimond.demo.demorestapi.persistence.entity.ApiUser;
import de.arimond.demo.demorestapi.persistence.entity.Rolle;
import de.arimond.demo.demorestapi.services.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@Transactional
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  IApiUserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        List<ApiUser> userList = userDao.loadAll();
        Optional<ApiUser> optionalUser = userList.stream().filter(userEntity -> userEntity.getName().equals(name)).findFirst();

        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException(MessageFormat.format("User with name {0} cannot be found.", name));
        }
        return getUserDetails(optionalUser.get());
    }

    private UserDetails getUserDetails(ApiUser user) {
        return new UserDetails() {

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                final SimpleGrantedAuthority simpleGrantedAuthority =
                        new SimpleGrantedAuthority(user.getRolle().toString());
                return Collections.singletonList(simpleGrantedAuthority);
            }

            @Override
            public String getPassword() {
                return user.getPasswort();
            }

            @Override
            public String getUsername() {
                return user.getName();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }

    public void createUser(UserDto userDto){
        ApiUser userEntity = UserMapper.mapUserEntityFromDto(userDto);
        userDao.save(userEntity);
    }
}
