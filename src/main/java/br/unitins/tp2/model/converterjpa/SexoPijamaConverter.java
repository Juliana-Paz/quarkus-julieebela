package br.unitins.tp2.model.converterjpa;

import br.unitins.tp2.model.SexoPijama;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SexoPijamaConverter implements AttributeConverter<SexoPijama, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SexoPijama sexoPijama) {
        return sexoPijama == null ? null : sexoPijama.getId();
    }

    @Override
    public SexoPijama convertToEntityAttribute(Integer id) {
        return SexoPijama.valueOf(id);
    }

}
