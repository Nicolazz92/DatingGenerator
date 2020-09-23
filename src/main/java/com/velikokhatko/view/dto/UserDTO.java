package com.velikokhatko.view.dto;

import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    @NotBlank
    private String name;
    private Gender gender;
    private MultipartFile photo;
    @Range(min = 18, max = 120)
    private Integer age;
    private BodyType bodyType;
    private String description;
    @Range(min = 100, max = 300)
    private Integer height;

    private String username;
    private String password;

    private String image64;
}
