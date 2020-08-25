package com.velikokhatko.model;

import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.model.enums.Gender;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SearchFilter extends BaseEntity {
    private Gender gender;
    private Integer ageMin;
    private Integer ageMax;
    private Double heightMin;
    private Double heightMax;
    private Set<BodyType> bodyTypes = new HashSet<>();

    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    @ElementCollection(fetch = FetchType.EAGER, targetClass = BodyType.class)
    @CollectionTable(name = "BODY_TYPES", joinColumns = {@JoinColumn(name = "id")})
    @Enumerated(EnumType.STRING)
    public Set<BodyType> getBodyTypes() {
        return bodyTypes;
    }
}
