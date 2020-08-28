package com.velikokhatko.bootstrap;

import com.velikokhatko.model.SearchFilter;
import com.velikokhatko.model.User;
import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.model.enums.Gender;
import com.velikokhatko.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
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

    public InitDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        HashSet<BodyType> bodyTypes = new HashSet<>();
        bodyTypes.add(BodyType.AVERAGE);
        bodyTypes.add(BodyType.ATHLETIC);

        SearchFilter peterSearchFilter = SearchFilter.builder()
                .gender(Gender.MALE)
                .heightMin(175)
                .ageMin(27)
                .bodyTypes(bodyTypes)
                .build();

        User peter = User.builder()
                .name("Kate")
                .age(25)
                .photo(getBytes("photos/kate.png"))
                .bodyType(BodyType.ATHLETIC)
                .description("Kate is a good girl")
                .gender(Gender.FEMALE)
                .height(165)
                .searchFilter(peterSearchFilter)
                .build();

        userRepository.save(peter);
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
