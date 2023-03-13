package com.nttdata.exchangerateservice.exchangerateservice.service;

import com.nttdata.exchangerateservice.exchangerateservice.entity.Exchange;
import com.nttdata.exchangerateservice.exchangerateservice.repository.ExchangeRepository;

import com.nttdata.exchangerateservice.exchangerateservice.utils.exceptions.NotFoundException;
import com.nttdata.exchangerateservice.exchangerateservice.utils.exceptions.NotSaveException;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Override
    public Single<Exchange> getExchangeRate(Integer currencyOrigin, Integer currencyDestiny) {
        return Single.fromCallable(() -> exchangeRepository.getExchangeRate(currencyOrigin, currencyDestiny)
                .orElseThrow(() -> new NotFoundException("Not found")));
    }

    @Override
    public Completable updateExchangeRate(Integer currencyOrigin, Integer currencyDestiny, BigDecimal exchangeRate) {
        return Completable.fromCallable(() -> exchangeRepository.updateExchangeRate(currencyOrigin, currencyDestiny,exchangeRate));
    }
}
