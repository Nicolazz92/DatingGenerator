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
@Embeddable
public class UserMatchSearchingFilter {
    private Gender gender;
    private Integer ageMin;
    private Integer ageMax;
    private Integer heightMin;
    private Integer heightMax;
    private Set<BodyType> bodyTypes = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "filter_gender")
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
