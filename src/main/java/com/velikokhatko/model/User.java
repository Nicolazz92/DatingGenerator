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
    private byte[] photo;
    private Integer age;
    private BodyType bodyType;
    private String description;
    private Integer height;
    private SearchFilter searchFilter;
    private Set<Match> matches = new HashSet<>();

    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    @Lob
    @Column(columnDefinition = "BLOB")
    public byte[] getPhoto() {
        return photo;
    }

    @Enumerated(EnumType.STRING)
    public BodyType getBodyType() {
        return bodyType;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "search_filter_id")
    public SearchFilter getSearchFilter() {
        return searchFilter;
    }

    @ManyToMany(mappedBy = "persons")
    public Set<Match> getMatches() {
        return matches;
    }
}
