package com.velikokhatko.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Match extends BaseEntity {
    private static final Logger logger = LoggerFactory.getLogger(Match.class);

    private Set<User> persons = new HashSet<>();
    private LocalDateTime creationDateTime;

    public Match(User person1, User person2) {
        persons = Set.of(person1, person2);
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
        if (persons == null || persons.size() != 2) {
            logger.warn("Match with id={} is not populated by users", getId());
        }
        return persons;
    }

    public void setPersons(Set<User> persons) {
        throw new UnsupportedOperationException("You can set Users to Match only with a Constructor");
    }
}
