package com.velikokhatko.services;

import com.velikokhatko.model.User;
import com.velikokhatko.model.UserMatchSearchingFilter;
import com.velikokhatko.repository.UserRepository;
import com.velikokhatko.view.dto.UserDTO;
import com.velikokhatko.view.dto.UserMatchSearchingFilterDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;

    public UserService(UserRepository userRepository,
                       @Qualifier("dtoConverter") ConversionService conversionService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserDTO getAuthorizedUserDTO() {
        User authorizedUser = getAuthorizedUser();
        return conversionService.convert(authorizedUser, UserDTO.class);
    }

    public UserDTO getUserDTOById(Long userId) {
        Optional<User> byId = userRepository.findById(userId);
        return byId.map(user -> conversionService.convert(user, UserDTO.class)).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User save(User user) {
        return userRepository.save(Objects.requireNonNull(user));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createOrUpdate(UserDTO dto) {
        User authorizedUser = getAuthorizedUser();
        dto.setId(authorizedUser.getId());
        User user = conversionService.convert(dto, User.class);
        userRepository.save(Objects.requireNonNull(user));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete() {
        User authorizedUser = getAuthorizedUser();
        userRepository.deleteById(authorizedUser.getId());
    }

    public UserMatchSearchingFilterDTO getSearchFilterDTO() {
        User authorizedUser = getAuthorizedUser();
        return conversionService.convert(authorizedUser.getUserMatchSearchingFilter(), UserMatchSearchingFilterDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(UserMatchSearchingFilterDTO userMatchSearchingFilterDTO) {
        UserMatchSearchingFilter userMatchSearchingFilter = conversionService.convert(userMatchSearchingFilterDTO, UserMatchSearchingFilter.class);
        User authorizedUser = getAuthorizedUser();
        Optional<User> userOptional = userRepository.findById(authorizedUser.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUserMatchSearchingFilter(userMatchSearchingFilter);
            userRepository.save(user);
        } else {
            throw new EntityExistsException("userId=" + authorizedUser.getId());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User getAuthorizedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DefaultOAuth2User principal = (DefaultOidcUser) authentication.getPrincipal();
        String sub = principal.getAttribute("sub");

        Optional<User> byGoogleClientId = userRepository.findByGoogleClientId(sub);
        if (byGoogleClientId.isPresent()) {
            return byGoogleClientId.get();
        } else {
            User user = new User();
            user.setName(principal.getAttribute("name"));
            user.setGoogleClientId(sub);
            return userRepository.save(user);
        }
    }
}
