package com.nttdata.exchangerateservice.exchangerateservice.repository;

import com.nttdata.exchangerateservice.exchangerateservice.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
@Transactional
public interface ExchangeRepository extends JpaRepository<Exchange, Integer> {
    @Query("SELECT e FROM Exchange e WHERE e.currencyOrigin = :currencyOrigin AND e.currencyDestiny = :currencyDestiny")
    public Optional<Exchange> getExchangeRate(@Param("currencyOrigin") Integer currencyOrigin, @Param("currencyDestiny") Integer currencyDestiny);

    @Modifying
    @Query("UPDATE Exchange e SET e.exchangeRate = :exchangeRate WHERE e.currencyOrigin = :currencyOrigin AND e.currencyDestiny = :currencyDestiny")
    public Integer updateExchangeRate(@Param("currencyOrigin") Integer currencyOrigin, @Param("currencyDestiny") Integer currencyDestiny, @Param("exchangeRate") BigDecimal exchangeRate);
}
