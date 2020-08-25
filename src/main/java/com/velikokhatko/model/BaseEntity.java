package com.velikokhatko.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@MappedSuperclass
@Access(AccessType.PROPERTY)
public class BaseEntity {

    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "hibernate_sequence", allocationSize = 1)
    @Column(name = "id")
    public Long getId() {
        return id;
    }
}
