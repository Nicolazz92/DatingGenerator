package com.velikokhatko.services;

import com.velikokhatko.model.SearchFilter;
import com.velikokhatko.model.User;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.model.enums.Gender;
import com.velikokhatko.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@SpringBootTest
@Transactional
class FilterSpecificationBuilderTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FilterSpecificationBuilder filterSpecificationBuilder;
    private SearchFilter peterSearchFilter;
    private User peter;

    @BeforeEach
    public void setUp() {
        peterSearchFilter = SearchFilter.builder()
                .gender(Gender.MALE)
                .ageMin(29)
                .ageMax(31)
                .heightMin(170D)
                .heightMax(190D)
                .bodyTypes(Set.of(BodyType.AVERAGE, BodyType.ATHLETIC, BodyType.THIN))
                .build();

        peter = User.builder()
                .age(30)
                .bodyType(BodyType.AVERAGE)
                .description("test desc")
                .gender(Gender.MALE)
                .height(180D)
                .name("Peter")
                .searchFilter(peterSearchFilter)
                .build();

        userRepository.save(peter);
    }

    @Test
    public void test() {
        List<User> all = userRepository.findAll(filterSpecificationBuilder.buildSpecification(peterSearchFilter));
        Assert.isTrue(all.contains(peter));
    }
}