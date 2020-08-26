package com.velikokhatko.view.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleUserDTO {
    private Long id;
    private String name;
    private Byte[] image;
}
