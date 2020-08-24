package com.velikokhatko.model;

import com.velikokhatko.model.enums.BodyType;
import com.velikokhatko.model.enums.Sex;
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
public class User extends BaseEntity {

    private String name;
    private Sex sex;
    private Byte[] image;
    private BodyType bodyType;
    private String description;
    private Double height;
    private Set<Match> matches = new HashSet<>();

    @Enumerated(EnumType.STRING)
    public Sex getSex() {
        return sex;
    }

    @Lob
    @Column(columnDefinition = "BLOB")
    public Byte[] getImage() {
        return image;
    }

    @Enumerated(EnumType.STRING)
    public BodyType getBodyType() {
        return bodyType;
    }

    @ManyToMany(mappedBy = "persons")
    public Set<Match> getMatches() {
        return matches;
    }
}
