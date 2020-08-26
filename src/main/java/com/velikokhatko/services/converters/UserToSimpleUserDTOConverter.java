package com.velikokhatko.services.converters;

import com.velikokhatko.model.User;
import com.velikokhatko.view.dto.SimpleUserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToSimpleUserDTOConverter implements Converter<User, SimpleUserDTO> {

    @Override
    public SimpleUserDTO convert(User entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, SimpleUserDTO.class);
    }
}
