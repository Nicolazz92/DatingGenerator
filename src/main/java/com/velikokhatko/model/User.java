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
    private UserMatchSearchingFilter userMatchSearchingFilter;
    private Set<Match> matches = new HashSet<>();

    @Override
    public void setId(Long id) {
        super.setId(id);
        if (userMatchSearchingFilter != null) {
            userMatchSearchingFilter.setUserId(id);
        }
    }

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    public UserMatchSearchingFilter getUserMatchSearchingFilter() {
        return userMatchSearchingFilter;
    }

    public void setUserMatchSearchingFilter(UserMatchSearchingFilter userMatchSearchingFilter) {
        this.userMatchSearchingFilter = userMatchSearchingFilter;
        this.userMatchSearchingFilter.setUserId(getId());
    }

    @ManyToMany(mappedBy = "persons")
    public Set<Match> getMatches() {
        return matches;
    }

    @PrePersist
    public void initSearchFilter() {
        if (userMatchSearchingFilter == null) {
            userMatchSearchingFilter = new UserMatchSearchingFilter();
            userMatchSearchingFilter.setUserId(getId());
        }
    }
}
