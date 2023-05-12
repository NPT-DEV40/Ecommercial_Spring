package com.ecommerce.library.service;

import com.ecommerce.library.dto.UserDto;
import com.ecommerce.library.model.User;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserDto save(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        userRepository.save(user);
        return tranferToUserDto(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public UserDto tranferToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
