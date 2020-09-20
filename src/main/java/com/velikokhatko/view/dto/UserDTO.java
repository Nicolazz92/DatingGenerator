package com.velikokhatko.view.dto;

import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @Max(99)
    @Min(18)
    private Integer age;
    private BodyType bodyType;
    private String description;
    @Max(250)
    @Min(130)
    private Integer height;

    @NotBlank
    private String username;
    private String password;

    private String image64;
}
