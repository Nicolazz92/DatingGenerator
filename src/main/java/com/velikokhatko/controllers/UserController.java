package com.velikokhatko.controllers;

import com.velikokhatko.services.UserService;
import com.velikokhatko.view.dto.UserDTO;
import com.velikokhatko.view.dto.UserMatchSearchingFilterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String USER_HOME = "users/userHome";
    private static final String USER_VIEW = "users/userView";
    private static final String UPDATE_USER = "users/updateUser";
    private static final String UPDATE_FILTER = "filters/updateFilter";

    private static final String CREATE_USER = "users/createUser";

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
    public ModelAndView initUpdateUserForm() {
        ModelAndView mav = new ModelAndView(UPDATE_USER);
        mav.addObject("user", userService.getAuthorizedUserDTO());
        return mav;
    }

    @PostMapping("/home/edit")
    public ModelAndView processUpdateUserForm(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fe -> logger.error("field='{}', rejectedValue='{}' message='{}'",
                    fe.getField(), fe.getRejectedValue(), fe.getDefaultMessage()));

            ModelAndView mav = new ModelAndView(UPDATE_USER);
            mav.addObject("user", userDTO);
            return mav;
        }
        userService.update(userDTO);
        return new ModelAndView(REDIRECT_TO_HOME);
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
    public ModelAndView processUpdateFilterForm(@ModelAttribute @Valid UserMatchSearchingFilterDTO userMatchSearchingFilterDTO,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fe -> logger.error("field='{}', rejectedValue='{}' message='{}'",
                    fe.getField(), fe.getRejectedValue(), fe.getDefaultMessage()));

            ModelAndView mav = new ModelAndView(UPDATE_FILTER);
            mav.addObject("filter", userMatchSearchingFilterDTO);
            return mav;
        }
        userService.update(userMatchSearchingFilterDTO);
        return new ModelAndView(REDIRECT_TO_HOME);
    }

    @GetMapping("/register")
    public ModelAndView initCreateUserForm() {
        ModelAndView mav = new ModelAndView(CREATE_USER);
        mav.addObject("user", new UserDTO());
        return mav;
    }

    @PostMapping("/register")
    public String processCreateUserForm(UserDTO dto) {
        userService.create(dto);
        return REDIRECT_TO_HOME;
    }
}
