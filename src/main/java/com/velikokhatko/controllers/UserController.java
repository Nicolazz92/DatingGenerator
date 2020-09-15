package com.velikokhatko.controllers;

import com.velikokhatko.services.UserService;
import com.velikokhatko.view.dto.UserDTO;
import com.velikokhatko.view.dto.UserMatchSearchingFilterDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final String USER_HOME = "users/userHome";
    private static final String USER_VIEW = "users/userView";
    private static final String CREATE_OR_UPDATE_USER = "users/createOrUpdateUser";
    private static final String UPDATE_FILTER = "filters/updateFilter";

    private static final String REDIRECT_TO_LOGIN = "redirect:/login";
    private static final String REDIRECT_TO_HOME = "redirect:/users/home";
    private static final String REDIRECT_TO_LOGOUT = "redirect:/logout";

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
        ModelAndView mav = new ModelAndView(USER_VIEW);
        mav.addObject("user", userService.getUserDTOById(userId));
        mav.addObject("hasLoggedUser", userService.getAuthorizedUser().isPresent());
        return mav;
    }

    @GetMapping("/home")
    public ModelAndView getUserHome() {
        UserDTO authorizedUserDTO = userService.getAuthorizedUserDTO();
        ModelAndView mav;
        if (authorizedUserDTO != null) {
            mav = new ModelAndView(USER_HOME);
            mav.addObject("user", authorizedUserDTO);
            mav.addObject("hasLoggedUser", true);
        } else {
            mav = new ModelAndView(REDIRECT_TO_LOGIN);
        }
        return mav;
    }

    @GetMapping("/home/edit")
    public ModelAndView initCreateOrUpdateUserForm() {
        ModelAndView mav = new ModelAndView(CREATE_OR_UPDATE_USER);
        mav.addObject("user", userService.getAuthorizedUserDTO());
        return mav;
    }

    @PostMapping("/home/edit")
    public String processCreateOrUpdateUserForm(@ModelAttribute UserDTO userDTO) {
        userService.createOrUpdate(userDTO);
        return REDIRECT_TO_HOME;
    }

    @PostMapping("/home/delete")
    public String processDelete() {
        userService.delete();
        return REDIRECT_TO_LOGOUT;
    }

    @GetMapping("/home/filter/edit")
    public ModelAndView initUpdateFilterForm() {
        ModelAndView mav = new ModelAndView(UPDATE_FILTER);
        mav.addObject("filter", userService.getSearchFilterDTO());
        return mav;
    }

    @PostMapping("/home/filter/edit")
    public String processUpdateFilterForm(@ModelAttribute UserMatchSearchingFilterDTO userMatchSearchingFilterDTO) {
        userService.update(userMatchSearchingFilterDTO);
        return REDIRECT_TO_HOME;
    }
}
