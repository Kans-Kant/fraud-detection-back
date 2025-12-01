package com.isfa.fr.fraudDetection.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDto {

    private String email;
    private String password;

}
