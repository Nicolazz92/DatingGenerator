package com.velikokhatko.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Match extends BaseEntity {
    private Set<User> persons;
    private LocalDateTime creationDateTime;

    public Match(User person1, User person2) {
        HashSet<User> users = new HashSet<>();
        users.add(person1);
        users.add(person2);
        persons = Collections.unmodifiableSet(users);
    }

    @CreationTimestamp
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    @ManyToMany
    @JoinTable(name = "PERSON_JOIN_TABLE",
            joinColumns = {@JoinColumn(name = "PERSON_ID")},
            inverseJoinColumns = {@JoinColumn(name = "MATCH_ID")}
    )
    public Set<User> getPersons() {
        if (persons == null) {
            throw new PersistenceException("Match in not populated by users");
        }
        return persons;
    }

    public void setPersons(Set<User> persons) {
        throw new UnsupportedOperationException("You can set Users to Match only with a Constructor");
    }
}
