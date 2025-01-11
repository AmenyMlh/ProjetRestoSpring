package tn.uma.isamm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto extends UserDto {
	private String major;
	private String university;
	private String year;

}
