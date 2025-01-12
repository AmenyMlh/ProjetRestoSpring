package tn.uma.isamm.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import tn.uma.isamm.dto.MenuDto;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Menu;


@Mapper
public interface MenuMapper {
	MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

    @Mapping(source = "meals", target = "meals")
    MenuDto toDTO(Menu menu);

    @Mapping(source = "meals", target = "meals")
    Menu toEntity(MenuDto menuDTO);

    List<MenuDto> toDTOList(List<Menu> menus);

    List<Menu> toEntityList(List<MenuDto> menuDTOs);



}
