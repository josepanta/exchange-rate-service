package com.nttdata.exchangerateservice.exchangerateservice.entity;

import com.nttdata.exchangerateservice.exchangerateservice.repository.ExchangePK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(ExchangePK.class)
public class Exchange {

    @Id
    @Column(name = "currencyorigin")
    private Integer currencyOrigin;

    @Id
    @Column(name = "currencydestiny")
    private Integer currencyDestiny;

    @Column(name = "exchangerate")
    private BigDecimal exchangeRate;
}
