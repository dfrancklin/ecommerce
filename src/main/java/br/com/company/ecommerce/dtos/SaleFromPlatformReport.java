package br.com.company.ecommerce.dtos;

import java.math.BigDecimal;

public interface SaleFromPlatformReport {

    String getMonth();

    Long getPlatformId();

    String getPlatformName();

    Integer getCount();

    BigDecimal getSum();

}
