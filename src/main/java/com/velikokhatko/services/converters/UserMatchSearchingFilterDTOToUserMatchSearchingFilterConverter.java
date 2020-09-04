package com.velikokhatko.services.converters;

import com.velikokhatko.model.User;
import com.velikokhatko.model.UserMatchSearchingFilter;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.repository.UserRepository;
import com.velikokhatko.view.dto.UserMatchSearchingFilterDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Component
public class UserMatchSearchingFilterDTOToUserMatchSearchingFilterConverter implements Converter<UserMatchSearchingFilterDTO, UserMatchSearchingFilter> {

    private final UserRepository userRepository;

    public UserMatchSearchingFilterDTOToUserMatchSearchingFilterConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserMatchSearchingFilter convert(UserMatchSearchingFilterDTO dto) {
        UserMatchSearchingFilter userMatchSearchingFilter = new UserMatchSearchingFilter();
        if (dto.getUserId() != null) {
            Optional<User> byId = userRepository.findById(dto.getUserId());
            if (byId.isPresent()) {
                userMatchSearchingFilter = byId.get().getUserMatchSearchingFilter();
            }
        }

        userMatchSearchingFilter.setAgeMin(dto.getAgeMin());
        userMatchSearchingFilter.setAgeMax(dto.getAgeMax());
        userMatchSearchingFilter.setHeightMin(dto.getHeightMin());
        userMatchSearchingFilter.setHeightMax(dto.getHeightMax());
        userMatchSearchingFilter.setGender(dto.getGender());

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
