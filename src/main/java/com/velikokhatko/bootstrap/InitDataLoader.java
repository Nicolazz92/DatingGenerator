package com.velikokhatko.bootstrap;

import com.velikokhatko.model.SearchFilter;
import com.velikokhatko.model.User;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.model.enums.Gender;
import com.velikokhatko.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Component
@Transactional
public class InitDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public InitDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        HashSet<BodyType> bodyTypes = new HashSet<>();
        bodyTypes.add(BodyType.AVERAGE);
        bodyTypes.add(BodyType.ATHLETIC);
        bodyTypes.add(BodyType.THIN);

        SearchFilter peterSearchFilter = SearchFilter.builder()
                .gender(Gender.FEMALE)
                .bodyTypes(bodyTypes)
                .build();

        User peter = User.builder()
                .age(30)
                .bodyType(BodyType.AVERAGE)
                .description("test desc")
                .gender(Gender.MALE)
                .height(180D)
                .name("Peter")
                .searchFilter(peterSearchFilter)
                .build();

        userRepository.save(peter);

        bodyTypes.add(BodyType.FAT);
        peter.getSearchFilter().setBodyTypes(bodyTypes);
    }
}
