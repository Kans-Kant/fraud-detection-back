package com.isfa.fr.fraudDetection.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserDto {

    private String email;

    private String password;

    private String fullName;
}
