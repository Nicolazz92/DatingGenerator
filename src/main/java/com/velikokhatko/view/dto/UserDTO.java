package com.velikokhatko.view.dto;

import com.velikokhatko.model.enums.BodyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private Byte[] image;
    private BodyType bodyType;
    private String name;
    private String description;
    private Double weight;
}
