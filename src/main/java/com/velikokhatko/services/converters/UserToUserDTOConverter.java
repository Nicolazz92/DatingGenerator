package com.velikokhatko.services.converters;

import com.velikokhatko.model.User;
import com.velikokhatko.view.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UserToUserDTOConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User entity) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO result = modelMapper.map(entity, UserDTO.class);
        if (entity.getPhoto() != null && entity.getPhoto().length > 0) {
            result.setImage64(Base64.getEncoder().encodeToString(entity.getPhoto()));
        }
        return result;
    }
}
