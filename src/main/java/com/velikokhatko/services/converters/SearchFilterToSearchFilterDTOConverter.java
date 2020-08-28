package com.velikokhatko.services.converters;

import com.velikokhatko.model.SearchFilter;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.view.dto.SearchFilterDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SearchFilterToSearchFilterDTOConverter implements Converter<SearchFilter, SearchFilterDTO> {

    @Override
    public SearchFilterDTO convert(SearchFilter entity) {
        ModelMapper modelMapper = new ModelMapper();
        SearchFilterDTO result = modelMapper.map(entity, SearchFilterDTO.class);
        result.setFindThin(entity.getBodyTypes().contains(BodyType.THIN));
        result.setFindAverage(entity.getBodyTypes().contains(BodyType.AVERAGE));
        result.setFindFat(entity.getBodyTypes().contains(BodyType.FAT));
        result.setFindAthletic(entity.getBodyTypes().contains(BodyType.ATHLETIC));
        return result;
    }
}
