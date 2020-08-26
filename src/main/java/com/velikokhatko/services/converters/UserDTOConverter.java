package com.velikokhatko.services.converters;

import com.velikokhatko.model.User;
import com.velikokhatko.view.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, UserDTO.class);
    }
}
