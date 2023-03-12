package com.nttdata.exchangerateservice.exchangerateservice.repository;

import com.nttdata.exchangerateservice.exchangerateservice.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Integer> {
    @Query("SELECT e FROM Exchange e WHERE e.currencyOrigin = :currencyOrigin AND e.currencyDestiny = :currencyDestiny")
    public Optional<Exchange> getExchangeRate(@Param("currencyOrigin") Integer currencyOrigin, @Param("currencyDestiny") Integer currencyDestiny);
}
