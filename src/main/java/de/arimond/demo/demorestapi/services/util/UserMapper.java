package de.arimond.demo.demorestapi.services.util;

import de.arimond.demo.demorestapi.persistence.dto.UserDto;
import de.arimond.demo.demorestapi.persistence.entity.ApiUser;
import de.arimond.demo.demorestapi.persistence.entity.Rolle;

public class UserMapper {

    public static ApiUser mapUserEntityFromDto(UserDto dto){
        ApiUser user = new ApiUser();
        user.setRolle(Rolle.ROLE_USER);
        user.setName(dto.getEmail());
        user.setPasswort(dto.getPassword());
        user.setVersion(0);
        return user;
    }
}
