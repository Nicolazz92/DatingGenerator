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
public class User extends BaseEntity {

    private String name;
    private Gender gender;
    private Byte[] image;
    private Integer age;
    private BodyType bodyType;
    private String description;
    private Double height;
    private SearchFilter searchFilter;
    private Set<Match> matches = new HashSet<>();

    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
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

    @OneToOne
    @JoinColumn(name = "search_filter_id")
    public SearchFilter getSearchFilter() {
        return searchFilter;
    }

    @ManyToMany(mappedBy = "persons")
    public Set<Match> getMatches() {
        return matches;
    }
}
