package com.velikokhatko.services.converters;

import com.velikokhatko.model.SearchFilter;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.repository.SearchFilterRepository;
import com.velikokhatko.view.dto.SearchFilterDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Component
public class SearchFilterDTOToSearchFilterConverter implements Converter<SearchFilterDTO, SearchFilter> {

    private final SearchFilterRepository searchFilterRepository;

    public SearchFilterDTOToSearchFilterConverter(SearchFilterRepository searchFilterRepository) {
        this.searchFilterRepository = searchFilterRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SearchFilter convert(SearchFilterDTO dto) {
        SearchFilter searchFilter = new SearchFilter();
        if (dto.getId() != null) {
            Optional<SearchFilter> byId = searchFilterRepository.findById(dto.getId());
            if (byId.isPresent()) {
                searchFilter = byId.get();
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
