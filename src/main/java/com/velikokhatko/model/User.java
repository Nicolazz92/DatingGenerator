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

    @Override
    public void setId(Long id) {
        super.setId(id);
        if (searchFilter != null) {
            searchFilter.setUserId(id);
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
    public SearchFilter getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(SearchFilter searchFilter) {
        this.searchFilter = searchFilter;
        this.searchFilter.setUserId(getId());
    }

    @ManyToMany(mappedBy = "persons")
    public Set<Match> getMatches() {
        return matches;
    }

    @PrePersist
    public void initSearchFilter() {
        if (searchFilter == null) {
            searchFilter = new SearchFilter();
            searchFilter.setUserId(getId());
        }
    }
}
