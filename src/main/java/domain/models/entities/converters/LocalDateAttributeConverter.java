package domain.models.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.util.Objects;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<java.util.Date, Date> {

    @Override
    public Date convertToDatabaseColumn(java.util.Date utilDate) {
        return utilDate == null ? null : new Date(utilDate.getTime());
    }

    @Override
    public java.util.Date convertToEntityAttribute(Date sqlDate) {
        return sqlDate == null ? null : new java.util.Date(sqlDate.getTime());
    }
}
