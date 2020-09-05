package com.velikokhatko.services.converters;

import com.velikokhatko.model.UserMatchSearchingFilter;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.view.dto.UserMatchSearchingFilterDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Component
public class UserMatchSearchingFilterDTOToUserMatchSearchingFilterConverter implements Converter<UserMatchSearchingFilterDTO, UserMatchSearchingFilter> {

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserMatchSearchingFilter convert(UserMatchSearchingFilterDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        UserMatchSearchingFilter userMatchSearchingFilter = modelMapper.map(dto, UserMatchSearchingFilter.class);

        HashSet<BodyType> bodyTypes = new HashSet<>();
        if (dto.isFindThin()) {
            bodyTypes.add(BodyType.THIN);
        }
        if (dto.isFindAthletic()) {
            bodyTypes.add(BodyType.ATHLETIC);
        }
        if (dto.isFindAverage()) {
            bodyTypes.add(BodyType.AVERAGE);
        }
        if (dto.isFindFat()) {
            bodyTypes.add(BodyType.FAT);
        }
        userMatchSearchingFilter.setBodyTypes(bodyTypes);

        return userMatchSearchingFilter;
    }
}
