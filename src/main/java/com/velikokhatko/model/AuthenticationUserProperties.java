package com.velikokhatko.model;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class AuthenticationUserProperties {
    private String username;
    private String password;
    private boolean enabled;
}
