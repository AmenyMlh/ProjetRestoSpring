package tn.uma.isamm.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import tn.uma.isamm.enums.UserRole;

@Data
public class UserDto {
	private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String phone;
}
