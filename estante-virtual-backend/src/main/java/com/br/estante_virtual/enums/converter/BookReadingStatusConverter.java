package com.br.estante_virtual.enums.converter;

import com.br.estante_virtual.enums.BookReadingStatus;
import jakarta.persistence.AttributeConverter;

public class BookReadingStatusConverter implements AttributeConverter<BookReadingStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookReadingStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getId();
    }

    @Override
    public BookReadingStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return BookReadingStatus.fromId(dbData);
    }

}
