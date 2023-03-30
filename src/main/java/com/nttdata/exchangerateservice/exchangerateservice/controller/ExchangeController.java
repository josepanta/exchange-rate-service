package com.nttdata.exchangerateservice.exchangerateservice.controller;

import com.nttdata.exchangerateservice.exchangerateservice.api.ExchangeRateApi;
import com.nttdata.exchangerateservice.exchangerateservice.entity.Exchange;
import com.nttdata.exchangerateservice.exchangerateservice.model.ExchangeRateListRequest;
import com.nttdata.exchangerateservice.exchangerateservice.model.ExchangeRateRequest;
import com.nttdata.exchangerateservice.exchangerateservice.model.ExchangeRateResponse;
import com.nttdata.exchangerateservice.exchangerateservice.service.ExchangeService;
import com.nttdata.exchangerateservice.exchangerateservice.utils.exceptions.Constant;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExchangeController implements ExchangeRateApi {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        configMapperExchange();
        configMapperExchangeRateRequest();
    }

    @Override
    public Maybe<ResponseEntity<List<ExchangeRateRequest>>> getAll() {
        return exchangeService.getAll()
                .map(exchanges -> ResponseEntity.status(HttpStatus.OK)
                        .body(exchanges.stream()
                                .map(this::convertExchangeToExchangeRateRequest)
                                .collect(Collectors.toList())))
                .onErrorResumeNext(this::buildErrorListExchangeRateRequest);
    }

    @Override
    public Maybe<ResponseEntity<ExchangeRateRequest>> getByCurrencyOriginIdAndCurrencyDestinyId(Long currencyOriginId, Long currencyDestinyId) {
        return exchangeService.getExchangeRateBycurrencyOriginIdAndcurrencyDestinyId(currencyOriginId.intValue(), currencyDestinyId.intValue())
                .map(exchange -> ResponseEntity.status(HttpStatus.OK)
                        .body(convertExchangeToExchangeRateRequest(exchange)))
                .toMaybe()
                .onErrorResumeNext(this::buildErrorExchangeRateRequest);
    }

    @Override
    public Maybe<ResponseEntity<String>> updateListOfExchangeRateByCurrencyIds(ExchangeRateListRequest exchangeRateListRequest) {
        return exchangeService.updateListExchangeRate(exchangeRateListRequest.getExchangeRateList()
                .stream().map(this::convertExchangeRateRequestToExchange)
                .collect(Collectors.toList()))
                .toSingle(() -> ResponseEntity.status(HttpStatus.OK).body("OK"))
                .toMaybe();
    }

    @Override
    public Maybe<ResponseEntity<ExchangeRateResponse>> applyExchangeRate(ExchangeRateRequest exchangeRateRequest) {
        return exchangeService.getExchangeRate(exchangeRateRequest.getCurrencyOriginId().intValue(),
                        exchangeRateRequest.getCurrencyDestinyId().intValue())
                .map(exchange -> ResponseEntity.status(HttpStatus.OK)
                        .body(this.buildExchangeRateResponse(exchangeRateRequest, exchange)))
                .onErrorResumeNext(this::buildErrorExchangeRateResponse)
                .toMaybe();
    }

    @Override
    public Maybe<ResponseEntity<String>> updateExchangeRateByCurrencyIds(ExchangeRateRequest exchangeRateRequest) {
        return exchangeService.updateExchangeRate(exchangeRateRequest.getCurrencyOriginId().intValue(),
                        exchangeRateRequest.getCurrencyDestinyId().intValue(), exchangeRateRequest.getAmount())
                .toSingle(()-> ResponseEntity.status(HttpStatus.OK).body("OK"))
                .toMaybe();

    }

    private Single<ResponseEntity<ExchangeRateResponse>> buildErrorExchangeRateResponse(Throwable error){
        return Single.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExchangeRateResponse()));
    }

    private Maybe<ResponseEntity<List<ExchangeRateRequest>>> buildErrorListExchangeRateRequest(Throwable error){
        return Maybe.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(new ExchangeRateRequest())));
    }

    private Maybe<ResponseEntity<ExchangeRateRequest>> buildErrorExchangeRateRequest(Throwable error){
        return Maybe.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExchangeRateRequest()));
    }

    private ExchangeRateResponse buildExchangeRateResponse(ExchangeRateRequest exchangeRateRequest, Exchange exchange){
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setCurrencyOriginId(exchangeRateRequest.getCurrencyOriginId());
        exchangeRateResponse.setCurrencyDestinyId(exchangeRateRequest.getCurrencyDestinyId());
        exchangeRateResponse.setAmount(exchangeRateRequest.getAmount());
        exchangeRateResponse.setAmountWithExchangeRate(exchangeRateRequest.getAmount().multiply(exchange.getExchangeRate()));
        return exchangeRateResponse;
    }

    private Exchange convertExchangeRateRequestToExchange(ExchangeRateRequest exchangeRateRequest){
        return modelMapper.map(exchangeRateRequest, Exchange.class);
    }

    private ExchangeRateRequest convertExchangeToExchangeRateRequest(Exchange exchange){
        return modelMapper.map(exchange, ExchangeRateRequest.class);
    }

    private void configMapperExchange(){
        TypeMap<Exchange, ExchangeRateRequest> propertyMapper = modelMapper.createTypeMap(Exchange.class, ExchangeRateRequest.class);
        propertyMapper.addMapping(Exchange::getCurrencyOrigin, ExchangeRateRequest::setCurrencyOriginId);
        propertyMapper.addMapping(Exchange::getCurrencyDestiny, ExchangeRateRequest::setCurrencyDestinyId);
        propertyMapper.addMapping(Exchange::getExchangeRate, ExchangeRateRequest::setAmount);
    }

    private void configMapperExchangeRateRequest(){
        TypeMap<ExchangeRateRequest, Exchange> propertyMapper = modelMapper.createTypeMap(ExchangeRateRequest.class, Exchange.class);
        propertyMapper.addMapping(ExchangeRateRequest::getCurrencyOriginId, Exchange::setCurrencyOrigin);
        propertyMapper.addMapping(ExchangeRateRequest::getCurrencyDestinyId, Exchange::setCurrencyDestiny);
        propertyMapper.addMapping(ExchangeRateRequest::getAmount, Exchange::setExchangeRate);
    }
}
