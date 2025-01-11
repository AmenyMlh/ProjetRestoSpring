package tn.uma.isamm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import tn.uma.isamm.enums.UserRole;

@Entity
@Data
@DiscriminatorValue("ROLE_EMPLOYEE")
public class Employee extends User {
	private String position;
}
