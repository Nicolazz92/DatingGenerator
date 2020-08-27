package com.velikokhatko.services;

import com.velikokhatko.model.User;
import com.velikokhatko.repository.UserRepository;
import com.velikokhatko.view.dto.UserDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserDTOById(Long userId) {
        Optional<User> byId = userRepository.findById(userId);
        return byId.map(user -> conversionService.convert(user, UserDTO.class)).orElse(null);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User save(User user) {
        return userRepository.save(Objects.requireNonNull(user));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User createOrUpdate(UserDTO dto) {
        User user = conversionService.convert(dto, User.class);
        return userRepository.save(Objects.requireNonNull(user));
    }
}