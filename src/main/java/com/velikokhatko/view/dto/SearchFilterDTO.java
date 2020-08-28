package com.velikokhatko.view.dto;

import com.velikokhatko.model.enums.Gender;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchFilterDTO {
    private Long id;
    private Long userId;
    private Gender gender;
    private Integer ageMin;
    private Integer ageMax;
    private Integer heightMin;
    private Integer heightMax;

    private boolean findThin;
    private boolean findAverage;
    private boolean findFat;
    private boolean findAthletic;
}
