package com.velikokhatko.services;

import com.velikokhatko.model.SearchFilter;
import com.velikokhatko.repository.SearchFilterRepository;
import com.velikokhatko.view.dto.SearchFilterDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class SearchFilterService {

    private final SearchFilterRepository searchFilterRepository;
    private final ConversionService conversionService;

    public SearchFilterService(SearchFilterRepository searchFilterRepository,
                               @Qualifier("dtoConverter") ConversionService conversionService) {
        this.searchFilterRepository = searchFilterRepository;
        this.conversionService = conversionService;
    }

    @Transactional(readOnly = true)
    public SearchFilterDTO getSearchFilterDTOById(Long id) {
        Optional<SearchFilter> byId = searchFilterRepository.findById(id);
        return byId.map(filter -> conversionService.convert(filter, SearchFilterDTO.class)).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(SearchFilterDTO searchFilterDTO) {
        SearchFilter searchFilter = conversionService.convert(searchFilterDTO, SearchFilter.class);
        searchFilterRepository.save(Objects.requireNonNull(searchFilter));
    }
}
