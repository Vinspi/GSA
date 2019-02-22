package fr.uniamu.ibdm.gsa_server.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

@Converter
public class BigDecimalAttributeConverter implements AttributeConverter<Integer, BigDecimal> {

  @Override
  public BigDecimal convertToDatabaseColumn(Integer integer) {
    return null;
  }

  @Override
  public Integer convertToEntityAttribute(BigDecimal bigDecimal) {
    return bigDecimal.intValue();
  }
}
