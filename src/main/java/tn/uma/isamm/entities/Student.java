package tn.uma.isamm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import tn.uma.isamm.enums.UserRole;

@Entity
@Data
@DiscriminatorValue("STUDENT")
public class Student extends User{
	private String major;
	private String university;
	private String year;
}
