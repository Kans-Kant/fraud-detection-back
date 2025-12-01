package com.isfa.fr.fraudDetection.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private Long id;

    private String fullName;

    private String email;
    private boolean enabled;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
