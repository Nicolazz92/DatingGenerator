package com.velikokhatko.services.converters;

import com.velikokhatko.model.SearchFilter;
import com.velikokhatko.model.User;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.repository.UserRepository;
import com.velikokhatko.view.dto.SearchFilterDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Component
public class SearchFilterDTOToSearchFilterConverter implements Converter<SearchFilterDTO, SearchFilter> {

    private final UserRepository userRepository;

    public SearchFilterDTOToSearchFilterConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SearchFilter convert(SearchFilterDTO dto) {
        SearchFilter searchFilter = new SearchFilter();
        if (dto.getUserId() != null) {
            Optional<User> byId = userRepository.findById(dto.getUserId());
            if (byId.isPresent()) {
                searchFilter = byId.get().getSearchFilter();
            }
        }

        searchFilter.setAgeMin(dto.getAgeMin());
        searchFilter.setAgeMax(dto.getAgeMax());
        searchFilter.setHeightMin(dto.getHeightMin());
        searchFilter.setHeightMax(dto.getHeightMax());
        searchFilter.setGender(dto.getGender());

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
        searchFilter.setBodyTypes(bodyTypes);
        return searchFilter;
    }
}
