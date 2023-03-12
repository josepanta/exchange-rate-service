package com.nttdata.exchangerateservice.exchangerateservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Data
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "currencyorigin")
    private Integer currencyOrigin;

    @Column(name = "currencydestiny")
    private Integer currencyDestiny;

    @Column(name = "exchangerate")
    private BigDecimal exchangeRate;
}
