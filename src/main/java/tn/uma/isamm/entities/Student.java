package tn.uma.isamm.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("stud")
public class Student extends User{
	private String major;
	private String university;
	private String year;
}
