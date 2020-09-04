package com.velikokhatko.view.dto;

import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private Gender gender;
    private MultipartFile photo;
    private Integer age;
    private BodyType bodyType;
    private String description;
    private Integer height;

    private String image64;
}
