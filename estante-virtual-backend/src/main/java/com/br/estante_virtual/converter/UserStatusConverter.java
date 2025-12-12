package com.br.estante_virtual.converter;

import com.br.estante_virtual.enums.UserStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserStatus status) {
        if (status == null) {
            return null;
        }
        return status.getId();
    }

    @Override
    public UserStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return UserStatus.fromId(dbData);
    }

}
