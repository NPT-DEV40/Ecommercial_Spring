package com.npt.ecommerce_full.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class jwtResponseDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String accessToken;
}
