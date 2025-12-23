package com.br.estante_virtual.enums.converter;

import com.br.estante_virtual.enums.ReviewStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReviewStatusConverter implements AttributeConverter<ReviewStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ReviewStatus status) {
        if (status == null) {
            return null;
        }

        return status.getId();
    }

    @Override
    public ReviewStatus convertToEntityAttribute(Integer dbData) {
        return ReviewStatus.fromId(dbData);
    }

}
