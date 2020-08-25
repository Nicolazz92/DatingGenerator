package com.velikokhatko.repository;

import com.velikokhatko.model.SearchFilter;
import com.velikokhatko.model.User;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.model.enums.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@DataJpaTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveTest() {
        SearchFilter peterSearchFilter = SearchFilter.builder()
                .gender(Gender.FEMALE)
                .bodyTypes(Set.of(BodyType.AVERAGE, BodyType.ATHLETIC, BodyType.THIN))
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

        User next = userRepository.findAll().iterator().next();

        System.out.printf("");
    }
}