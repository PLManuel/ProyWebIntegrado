package com.OrderNet.ProyWebIntegrado.dto.user;

import com.OrderNet.ProyWebIntegrado.persistence.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Boolean active;
    private Role role;
}
