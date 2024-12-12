package tn.uma.isamm.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import tn.uma.isamm.dto.MenuDto;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Menu;


@Mapper
public interface MenuMapper {
	MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

    MenuDto toDTO(Menu menu);

    Menu toEntity(MenuDto menuDTO);



}
