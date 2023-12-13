package br.com.company.ecommerce.dtos;

import java.math.BigDecimal;

public interface SaleGeneralReport {

    String getMonth();

    Integer getCount();

    BigDecimal getSum();

}
