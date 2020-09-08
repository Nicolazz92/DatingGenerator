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
        Optional<User> authorizedUser = getAuthorizedUser();
        return authorizedUser
                .map(user -> conversionService.convert(user, UserDTO.class))
                .orElse(new UserDTO());
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
        Optional<User> authorizedUser = getAuthorizedUser();
        if (authorizedUser.isPresent()) {
            dto.setId(authorizedUser.get().getId());
            User user = conversionService.convert(dto, User.class);
            userRepository.save(Objects.requireNonNull(user));
        } else {
            throw new EntityExistsException("authorized user not found");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete() {
        Optional<User> authorizedUser = getAuthorizedUser();
        if (authorizedUser.isPresent()) {
            userRepository.delete(authorizedUser.get());
        } else {
            throw new EntityExistsException("authorized user not found");
        }
    }

    public UserMatchSearchingFilterDTO getSearchFilterDTO() {
        Optional<User> authorizedUser = getAuthorizedUser();
        return authorizedUser
                .map(user -> conversionService.convert(user.getUserMatchSearchingFilter(), UserMatchSearchingFilterDTO.class))
                .orElse(new UserMatchSearchingFilterDTO());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(UserMatchSearchingFilterDTO userMatchSearchingFilterDTO) {
        UserMatchSearchingFilter userMatchSearchingFilter = conversionService.convert(userMatchSearchingFilterDTO, UserMatchSearchingFilter.class);
        Optional<User> userOptional = getAuthorizedUser();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUserMatchSearchingFilter(userMatchSearchingFilter);
            userRepository.save(user);
        } else {
            throw new EntityExistsException("authorized user not found");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<User> getAuthorizedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if ("anonymousUser".equals(principal)) {
            return Optional.empty();
        }
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) principal;
        String sub = defaultOAuth2User.getAttribute("sub");

        Optional<User> byGoogleClientId = userRepository.findByGoogleClientId(sub);
        if (byGoogleClientId.isPresent()) {
            return byGoogleClientId;
        } else {
            User user = new User();
            user.setName(defaultOAuth2User.getAttribute("name"));
            user.setGoogleClientId(sub);
            return Optional.of(userRepository.save(user));
        }
    }
}
