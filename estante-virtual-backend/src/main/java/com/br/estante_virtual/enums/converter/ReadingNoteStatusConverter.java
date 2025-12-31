package com.br.estante_virtual.enums.converter;

import com.br.estante_virtual.enums.ReadingNoteStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReadingNoteStatusConverter implements AttributeConverter<ReadingNoteStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReadingNoteStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getId();
    }

    @Override
    public ReadingNoteStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return ReadingNoteStatus.fromId(dbData);
    }
}