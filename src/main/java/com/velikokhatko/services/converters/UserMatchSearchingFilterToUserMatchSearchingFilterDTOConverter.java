package com.velikokhatko.services.converters;

import com.velikokhatko.model.UserMatchSearchingFilter;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.view.dto.UserMatchSearchingFilterDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserMatchSearchingFilterToUserMatchSearchingFilterDTOConverter implements Converter<UserMatchSearchingFilter, UserMatchSearchingFilterDTO> {

    @Override
    public UserMatchSearchingFilterDTO convert(UserMatchSearchingFilter entity) {
        ModelMapper modelMapper = new ModelMapper();
        UserMatchSearchingFilterDTO result = modelMapper.map(entity, UserMatchSearchingFilterDTO.class);
        result.setFindThin(entity.getBodyTypes().contains(BodyType.THIN));
        result.setFindAverage(entity.getBodyTypes().contains(BodyType.AVERAGE));
        result.setFindFat(entity.getBodyTypes().contains(BodyType.FAT));
        result.setFindAthletic(entity.getBodyTypes().contains(BodyType.ATHLETIC));
        return result;
    }
}
