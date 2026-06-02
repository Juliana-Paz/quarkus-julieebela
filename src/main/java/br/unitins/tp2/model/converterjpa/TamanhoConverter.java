package br.unitins.tp2.model.converterjpa;

import br.unitins.tp2.model.Tamanho;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TamanhoConverter implements AttributeConverter<Tamanho, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Tamanho tamanho) {
        return tamanho == null ? null : tamanho.getId();
    }

    @Override
    public Tamanho convertToEntityAttribute(Integer id) {
        return Tamanho.valueOf(id);
    }

}
