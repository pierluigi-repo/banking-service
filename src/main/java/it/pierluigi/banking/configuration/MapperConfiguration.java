package it.pierluigi.banking.configuration;

import it.pierluigi.banking.dto.TransactionDTO;
import it.pierluigi.banking.model.Transaction;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

/**
 * This class contains additional mapping configuration
 */
@Configuration
public class MapperConfiguration {

    @Bean
    public ModelMapper getModelMapper() {

        ModelMapper modelMapper =  new ModelMapper();

        // convert from string to LocalDate during Transaction -> TransactionDTO mapping
        Converter<String, LocalDate> toLocalDate =
                context -> context.getSource() == null ? null : LocalDate.parse(context.getSource());

        TypeMap<Transaction, TransactionDTO> typeMap = modelMapper.createTypeMap(Transaction.class, TransactionDTO.class);
        typeMap.addMappings(mapper -> mapper.using(toLocalDate).map(Transaction::getAccountingDate, TransactionDTO::setAccountingDate));
        typeMap.addMappings(mapper -> mapper.using(toLocalDate).map(Transaction::getValueDate, TransactionDTO::setValueDate));

        return modelMapper;
    }

}
