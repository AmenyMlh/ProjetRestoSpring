package tn.uma.isamm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import tn.uma.isamm.dto.CardDto;
import tn.uma.isamm.entities.Card;

@Mapper
public interface CardMapper {
	 CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

	    CardDto toDTO(Card card);

	    Card toEntity(CardDto cardDTO);
}
