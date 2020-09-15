package com.velikokhatko.services.converters;

import com.velikokhatko.model.AuthenticationUserProperties;
import com.velikokhatko.model.User;
import com.velikokhatko.repository.UserRepository;
import com.velikokhatko.view.dto.UserDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Component
public class UserDTOToUserConverter implements Converter<UserDTO, User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTOToUserConverter(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User convert(UserDTO dto) {
        User user = new User();
        AuthenticationUserProperties authenticationUserProperties = new AuthenticationUserProperties();
        authenticationUserProperties.setEnabled(true);
        user.setAuthenticationUserProperties(authenticationUserProperties);

        if (dto.getId() != null) {
            Optional<User> byId = userRepository.findById(dto.getId());
            if (byId.isPresent()) {
                user = byId.get();
            }
        }
        user.setName(dto.getName());
        user.setGender(dto.getGender());
        setPhoto(dto, user);
        user.setAge(dto.getAge());
        user.setBodyType(dto.getBodyType());
        user.setDescription(dto.getDescription());
        user.setHeight(dto.getHeight());
        if (dto.getId() != null) {
            if (Strings.isNotBlank(dto.getPassword())) {
                user.getAuthenticationUserProperties().setPassword(passwordEncoder.encode(dto.getPassword()));
            }
        } else {
            if (Strings.isBlank(dto.getPassword())) {
                throw new RuntimeException("Empty Password");
            } else {
                user.getAuthenticationUserProperties().setPassword(passwordEncoder.encode(dto.getPassword()));
            }
        }
        return user;
    }

    private void setPhoto(UserDTO dto, User user) {
        if (dto.getPhoto() == null || dto.getPhoto().isEmpty()) {
            return;
        }
        try {
            user.setPhoto(dto.getPhoto().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
