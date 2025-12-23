package com.br.estante_virtual.enums.converter;

import com.br.estante_virtual.enums.RatingLevel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RatingLevelConverter implements AttributeConverter<RatingLevel, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RatingLevel ratingLevel) {
        if (ratingLevel == null) {
            return null;
        }

        return ratingLevel.getCodigo();
    }

    @Override
    public RatingLevel convertToEntityAttribute(Integer dbData) {
        return RatingLevel.toEnum(dbData);
    }

}
