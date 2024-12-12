package tn.uma.isamm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import tn.uma.isamm.dto.IngredientDto;
import tn.uma.isamm.entities.Ingredient;

@Mapper
public interface IngredientMapper {
	IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientDto toDTO(Ingredient ingredient);

    Ingredient toEntity(IngredientDto ingredientDTO);
}
