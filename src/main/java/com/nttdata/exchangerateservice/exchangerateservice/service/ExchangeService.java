package com.nttdata.exchangerateservice.exchangerateservice.service;

import com.nttdata.exchangerateservice.exchangerateservice.entity.Exchange;
import io.reactivex.Single;

import java.util.List;

public interface ExchangeService {
    public Single<Exchange> getExchangeRate(Integer currencyOrigin, Integer currencyDestiny);
}
