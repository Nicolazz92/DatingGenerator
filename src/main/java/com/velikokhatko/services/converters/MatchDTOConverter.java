package com.velikokhatko.services.converters;

import com.velikokhatko.model.Match;
import com.velikokhatko.view.dto.MatchDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class MatchDTOConverter implements Converter<Match, MatchDTO> {

    @Override
    public MatchDTO convert(Match entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, MatchDTO.class);
    }
}
