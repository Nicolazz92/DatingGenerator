package com.velikokhatko.bootstrap;

import com.velikokhatko.model.AuthenticationUserProperties;
import com.velikokhatko.model.User;
import com.velikokhatko.model.UserMatchSearchingFilter;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.model.enums.Gender;
import com.velikokhatko.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

@Component
@Transactional
public class InitDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    public InitDataLoader(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        HashSet<BodyType> bodyTypes = new HashSet<>();
        bodyTypes.add(BodyType.AVERAGE);
        bodyTypes.add(BodyType.ATHLETIC);

        UserMatchSearchingFilter kateUserMatchSearchingFilter = UserMatchSearchingFilter.builder()
                .gender(Gender.MALE)
                .heightMin(175)
                .ageMin(27)
                .bodyTypes(bodyTypes)
                .build();

        AuthenticationUserProperties kateAuthProps = AuthenticationUserProperties.builder()
                .username("kate")
                .password(passwordEncoder.encode("password"))
                .enabled(true)
                .build();

        User kate = User.builder()
                .name("Kate")
                .age(25)
//                .photo(getBytes("photos/kate.png"))
                .bodyType(BodyType.ATHLETIC)
                .description("Kate is a good girl")
                .gender(Gender.FEMALE)
                .height(165)
                .userMatchSearchingFilter(kateUserMatchSearchingFilter)
                .authenticationUserProperties(kateAuthProps)
                .build();

        userRepository.save(kate);

        jdbcTemplate.execute("insert into AUTHORITIES(USERNAME, AUTHORITY) values ('"
                + kate.getAuthenticationUserProperties().getUsername()
                + "',  'ROLE_USER')");
    }

    public byte[] getBytes(String picturePath) throws IOException {
        File file = new ClassPathResource(picturePath).getFile();
        byte[] bytes = FileCopyUtils.copyToByteArray(file);
        byte[] objectBytes = new byte[bytes.length];
        for (int i = 0, bytesLength = bytes.length; i < bytesLength; i++) {
            objectBytes[i] = bytes[i];
        }
        return objectBytes;
    }
}
