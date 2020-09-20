package com.velikokhatko.view.dto;

import com.velikokhatko.model.enums.Gender;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMatchSearchingFilterDTO {
    @NotNull
    private Gender gender;
    @Max(99)
    @Min(18)
    private Integer ageMin;
    @Max(99)
    @Min(18)
    private Integer ageMax;
    @Max(250)
    @Min(130)
    private Integer heightMin;
    @Max(250)
    @Min(100)
    private Integer heightMax;

    private boolean findThin;
    private boolean findAverage;
    private boolean findFat;
    private boolean findAthletic;
}
