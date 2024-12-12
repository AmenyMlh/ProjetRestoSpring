package tn.uma.isamm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import tn.uma.isamm.dto.MealDto;
import tn.uma.isamm.entities.Meal;

@Mapper
public interface MealMapper {
	MealMapper INSTANCE = Mappers.getMapper(MealMapper.class);

    MealDto toDTO(Meal meal);

    Meal toEntity(MealDto mealDTO);
}
