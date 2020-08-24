package com.velikokhatko.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Match extends BaseEntity {
    private User personOne;
//    private User personTwo;
    private LocalDateTime creationDateTime;

    @CreationTimestamp
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    @ManyToOne
    @JoinTable(name = "PERSON_JOIN_TABLE",
            joinColumns = {@JoinColumn(name = "PERSON_ONE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "MATCH_ID")}
    )
    public User getPersonOne() {
        return personOne;
    }

//    @ManyToOne
//    @JoinTable(name = "PERSON_JOIN_TABLE",
//            joinColumns = {@JoinColumn(name = "PERSON_TWO_ID")},
//            inverseJoinColumns = {@JoinColumn(name = "MATCH_ID")}
//    )
//    public User getPersonTwo() {
//        return personTwo;
//    }
}
