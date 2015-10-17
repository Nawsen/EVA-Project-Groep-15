package hogent.group15;


import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Frederik
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate attribute) {
	return Date.from(attribute.atStartOfDay().toInstant(ZoneOffset.UTC));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
	return LocalDate.from(dbData.toInstant());
    }

}
