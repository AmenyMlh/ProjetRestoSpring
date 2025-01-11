package tn.uma.isamm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import tn.uma.isamm.dto.MealDto;
import tn.uma.isamm.entities.Meal;

@Mapper
public interface MealMapper {
	 MealMapper INSTANCE = Mappers.getMapper(MealMapper.class);

	 @Mapping(target = "ingredients", source = "mealIngredients")
	    MealDto toDto(Meal meal);

	    @Mapping(target = "mealIngredients", source = "ingredients")
	    Meal toEntity(MealDto mealDto);
}
