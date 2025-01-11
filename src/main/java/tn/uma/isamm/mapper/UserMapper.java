package tn.uma.isamm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import tn.uma.isamm.dto.UserDto;
import tn.uma.isamm.entities.User;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

   /* @Mapping(target = "id", source = "id")
    UserDto toDTO(User user);

    @Mapping(target = "id", source = "id")
    User toEntity(UserDto userDTO);*/

}
