package com.br.estante_virtual.converter;

import com.br.estante_virtual.enums.BookStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BookStatusConverter implements AttributeConverter<BookStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookStatus status) {
        if (status == null) {
            return null;
        }
        return status.getId();
    }

    @Override
    public BookStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return BookStatus.fromId(dbData);
    }
}
