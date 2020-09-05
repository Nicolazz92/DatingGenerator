package com.velikokhatko.controllers;

import com.velikokhatko.model.User;
import com.velikokhatko.services.UserService;
import com.velikokhatko.view.dto.UserDTO;
import com.velikokhatko.view.dto.UserMatchSearchingFilterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final String VIEW_USER = "users/user";
    private static final String CREATE_OR_UPDATE_USER = "users/createOrUpdateUser";
    private static final String UPDATE_FILTER = "filters/updateFilter";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/{userId}")
    public ModelAndView getUserByUserId(@PathVariable Long userId) {
        ModelAndView mav = new ModelAndView(VIEW_USER);
        mav.addObject("user", userService.getUserDTOById(userId));
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView initCreationForm() {
        ModelAndView mav = new ModelAndView(CREATE_OR_UPDATE_USER);
        mav.addObject("user", UserDTO.builder().build());
        return mav;
    }

    @PostMapping("/new")
    public String processCreateUserForm(@ModelAttribute UserDTO userDTO) {
        if (userDTO.getId() != null) {
            logger.error("new User cannot has populated id: id={}", userDTO.getId());
            return "redirect:/users/" + userDTO.getId();
        }
        User savedUser = userService.createOrUpdate(userDTO);
        return "redirect:/users/" + savedUser.getId();
    }

    @GetMapping("/{userId}/edit")
    public ModelAndView initCreateOrUpdateUserForm(@PathVariable Long userId) {
        ModelAndView mav = new ModelAndView(CREATE_OR_UPDATE_USER);
        mav.addObject("user", userService.getUserDTOById(userId));
        return mav;
    }

    @PostMapping("/{userId}/edit")
    public String processCreateOrUpdateUserForm(@ModelAttribute UserDTO userDTO, @PathVariable Long userId) {
        userDTO.setId(userId);
        User user = userService.createOrUpdate(userDTO);
        return "redirect:/users/" + user.getId();
    }

    @PostMapping("/{userId}/delete")
    public String processDelete(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/";
    }

    @GetMapping("/{userId}/filter/edit")
    public ModelAndView initUpdateFilterForm(@PathVariable Long userId) {
        ModelAndView mav = new ModelAndView(UPDATE_FILTER);
        mav.addObject("filter", userService.getSearchFilterDTOById(userId));
        return mav;
    }

    @PostMapping("/{userId}/filter/edit")
    public String processUpdateFilterForm(@ModelAttribute UserMatchSearchingFilterDTO userMatchSearchingFilterDTO, @PathVariable Long userId) {
        userService.update(userId, userMatchSearchingFilterDTO);
        return "redirect:/users/" + userId;
    }
}
