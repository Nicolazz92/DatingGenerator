package com.velikokhatko.services.converters;

import com.velikokhatko.model.User;
import com.velikokhatko.repository.UserRepository;
import com.velikokhatko.view.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Component
public class UserDTOToUserConverter implements Converter<UserDTO, User> {

    private final UserRepository userRepository;

    public UserDTOToUserConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User convert(UserDTO dto) {
        User user = new User();
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