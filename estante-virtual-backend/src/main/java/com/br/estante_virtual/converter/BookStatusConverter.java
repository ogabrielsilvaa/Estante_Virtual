package com.br.estante_virtual.converter;

import com.br.estante_virtual.enums.BookReadingStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BookStatusConverter implements AttributeConverter<BookReadingStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookReadingStatus status) {
        if (status == null) {
            return null;
        }
        return status.getId();
    }

    @Override
    public BookReadingStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return BookReadingStatus.fromId(dbData);
    }
}
