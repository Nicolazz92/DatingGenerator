package com.velikokhatko.view.dto;

import com.velikokhatko.model.enums.Gender;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMatchSearchingFilterDTO {
    @NotNull
    private Gender gender;
    @Range(min = 18, max = 120)
    @NotBlank
    private Integer ageMin;
    @Range(min = 18, max = 120)
    @NotBlank
    private Integer ageMax;
    @Range(min = 100, max = 300)
    private Integer heightMin;
    @Range(min = 100, max = 300)
    private Integer heightMax;

    private boolean findThin;
    private boolean findAverage;
    private boolean findFat;
    private boolean findAthletic;
}
