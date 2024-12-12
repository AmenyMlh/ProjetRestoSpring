package tn.uma.isamm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import tn.uma.isamm.dto.PaymentDto;
import tn.uma.isamm.entities.Payment;

@Mapper
public interface PaymentMapper {
	PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentDto toDTO(Payment payment);

    Payment toEntity(PaymentDto paymentDTO);
}
