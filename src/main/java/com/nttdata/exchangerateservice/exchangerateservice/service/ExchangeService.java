package com.nttdata.exchangerateservice.exchangerateservice.service;

import com.nttdata.exchangerateservice.exchangerateservice.entity.Exchange;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeService {

    public Maybe<List<Exchange>> getAll();

    public Completable updateListExchangeRate(List<Exchange> Exchange);

    public Single<Exchange> getExchangeRate(Integer currencyOrigin, Integer currencyDestiny);

    public Single<Exchange> getExchangeRateBycurrencyOriginIdAndcurrencyDestinyId(Integer currencyOrigin, Integer currencyDestiny);

    public Completable updateExchangeRate(Integer currencyOrigin, Integer currencyDestiny, BigDecimal exchangeRate);

}
