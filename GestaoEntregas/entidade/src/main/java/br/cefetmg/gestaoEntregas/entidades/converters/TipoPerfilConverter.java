package br.cefetmg.gestaoEntregas.entidades.converters;

import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoPerfilConverter implements AttributeConverter<TipoPerfil, String> {
    @Override
    public String convertToDatabaseColumn(TipoPerfil attribute) {
        return attribute.getDescricao();
    }

    @Override
    public TipoPerfil convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return TipoPerfil.valueOfDescricao(dbData);
    }
}