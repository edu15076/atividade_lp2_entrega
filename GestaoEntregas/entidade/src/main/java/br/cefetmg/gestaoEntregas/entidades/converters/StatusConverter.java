package br.cefetmg.gestaoEntregas.entidades.converters;

import br.cefetmg.gestaoEntregas.entidades.enums.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status attribute) {
        return attribute.getDescricao();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;

        return Status.valueOfDescricao(dbData);
    }
}
