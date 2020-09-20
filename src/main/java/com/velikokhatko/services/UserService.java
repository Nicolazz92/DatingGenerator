package com.velikokhatko.services;

import com.velikokhatko.model.User;
import com.velikokhatko.model.UserMatchSearchingFilter;
import com.velikokhatko.repository.UserRepository;
import com.velikokhatko.view.dto.UserDTO;
import com.velikokhatko.view.dto.UserMatchSearchingFilterDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    public static final String INSERT_AUTHORITIES = "insert into AUTHORITIES(USERNAME, AUTHORITY) values ('%username%',  'ROLE_USER')";

    private final UserRepository userRepository;
    private final ConversionService conversionService;
    private final JdbcTemplate jdbcTemplate;

    public UserService(UserRepository userRepository,
                       @Qualifier("dtoConverter") ConversionService conversionService,
                       JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserDTO getAuthorizedUserDTO() {
        Optional<User> authorizedUser = getAuthorizedUser();
        return authorizedUser
                .map(user -> conversionService.convert(user, UserDTO.class))
                .orElse(null);
    }

    public UserDTO getUserDTOById(Long userId) {
        Optional<User> byId = userRepository.findById(userId);
        return byId.map(user -> conversionService.convert(user, UserDTO.class))
                .orElseThrow(() -> new EntityExistsException("User not exists with id=" + userId));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User save(User user) {
        return userRepository.save(Objects.requireNonNull(user));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(UserDTO dto) {
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
    public void create(UserDTO dto) {
        Optional<User> authorizedUser = getAuthorizedUser();
        if (authorizedUser.isPresent()) {
            throw new EntityExistsException("already authorized");
        } else {
            User user = conversionService.convert(dto, User.class);
            User savedNewUser = userRepository.save(Objects.requireNonNull(user));
            jdbcTemplate.execute(INSERT_AUTHORITIES.replace("%username%", savedNewUser.getAuthenticationUserProperties().getUsername()));
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
        org.springframework.security.core.userdetails.User authenticatedUser = (org.springframework.security.core.userdetails.User) principal;
        String username = authenticatedUser.getUsername();
        return userRepository.findByAuthenticationUserPropertiesUsername(username);
    }
}
