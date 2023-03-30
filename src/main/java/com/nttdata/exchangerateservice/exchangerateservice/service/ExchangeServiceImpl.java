package com.nttdata.exchangerateservice.exchangerateservice.service;

import com.nttdata.exchangerateservice.exchangerateservice.entity.Exchange;
import com.nttdata.exchangerateservice.exchangerateservice.model.ExchangeRateRequest;
import com.nttdata.exchangerateservice.exchangerateservice.repository.ExchangeRepository;

import com.nttdata.exchangerateservice.exchangerateservice.utils.exceptions.NotFoundException;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Override
    public Maybe<List<Exchange>> getAll() {
        return Maybe.fromCallable(() -> Optional.of(exchangeRepository.findAll())
                .orElseThrow(() -> new NotFoundException("Not found")));
    }

    @Override
    public Single<Exchange> getExchangeRateBycurrencyOriginIdAndcurrencyDestinyId(Integer currencyOrigin, Integer currencyDestiny) {
        return Single.fromCallable(() -> exchangeRepository.findByCurrencyOriginAndCurrencyDestiny(currencyOrigin, currencyDestiny)
                .orElseThrow(() -> new NotFoundException("Not found")));
    }

    @Override
    public Completable updateListExchangeRate(List<Exchange> Exchange) {
        return Completable.fromCallable(() -> exchangeRepository.saveAll(Exchange));
    }

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
