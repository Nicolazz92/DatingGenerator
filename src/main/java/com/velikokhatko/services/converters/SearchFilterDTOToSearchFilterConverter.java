package com.velikokhatko.services.converters;

import com.velikokhatko.model.SearchFilter;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.view.dto.SearchFilterDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class SearchFilterDTOToSearchFilterConverter implements Converter<SearchFilterDTO, SearchFilter> {

    @Override
    public SearchFilter convert(SearchFilterDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        SearchFilter result = modelMapper.map(dto, SearchFilter.class);
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
        result.setBodyTypes(bodyTypes);
        return result;
    }
}
