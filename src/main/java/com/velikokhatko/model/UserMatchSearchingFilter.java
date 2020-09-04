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
public class UserMatchSearchingFilter {
    private Long userId;
    private Gender gender;
    private Integer ageMin;
    private Integer ageMax;
    private Integer heightMin;
    private Integer heightMax;
    private Set<BodyType> bodyTypes = new HashSet<>();

    @Id
    public Long getUserId() {
        return userId;
    }

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
