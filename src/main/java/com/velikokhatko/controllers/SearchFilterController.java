package com.velikokhatko.controllers;

import com.velikokhatko.model.SearchFilter;
import com.velikokhatko.services.SearchFilterService;
import com.velikokhatko.view.dto.SearchFilterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/filters")
public class SearchFilterController {
    private static final String UPDATE_FILTER = "filters/updateFilter";
    private static final Logger logger = LoggerFactory.getLogger(SearchFilterController.class);

    private final SearchFilterService searchFilterService;

    public SearchFilterController(SearchFilterService searchFilterService) {
        this.searchFilterService = searchFilterService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.setDisallowedFields("userId");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView initCreateOrUpdateUserForm(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView(UPDATE_FILTER);
        mav.addObject("filter", searchFilterService.getSearchFilterDTOById(id));
        return mav;
    }

    @PostMapping("/{id}/edit")
    public String processCreateOrUpdateUserForm(@ModelAttribute SearchFilterDTO searchFilterDTO,
                                                @PathVariable Long id) {
        searchFilterDTO.setId(id);
        SearchFilter savedFilter = searchFilterService.update(searchFilterDTO);
        return "redirect:/users/" + savedFilter.getUser().getId();
    }
}
