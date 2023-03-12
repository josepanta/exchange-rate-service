package com.nttdata.exchangerateservice.exchangerateservice.controller;

import com.nttdata.exchangerateservice.exchangerateservice.api.ExchangeRateApi;
import com.nttdata.exchangerateservice.exchangerateservice.entity.Exchange;
import com.nttdata.exchangerateservice.exchangerateservice.model.ExchangeRateRequest;
import com.nttdata.exchangerateservice.exchangerateservice.model.ExchangeRateResponse;
import com.nttdata.exchangerateservice.exchangerateservice.service.ExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExchangeController implements ExchangeRateApi {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<ExchangeRateResponse>> applyExchangeRate(ExchangeRateRequest exchangeRateRequest) {
        return exchangeService.getExchangeRate(exchangeRateRequest.getCurrencyOriginId().intValue()
                        , exchangeRateRequest.getCurrencyDestinyId().intValue())
                .map(exchange -> ResponseEntity.status(HttpStatus.OK)
                        .body(List.of(this.buildExchangeRateResponse(exchangeRateRequest, exchange))))
                .blockingGet();
    }

    private ExchangeRateResponse buildExchangeRateResponse(ExchangeRateRequest exchangeRateRequest, Exchange exchange){
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setCurrencyOriginId(exchangeRateRequest.getCurrencyOriginId());
        exchangeRateResponse.setCurrencyDestinyId(exchangeRateRequest.getCurrencyDestinyId());
        exchangeRateResponse.setAmount(exchangeRateRequest.getAmount().doubleValue());
        exchangeRateResponse.setAmountWithExchangeRate(exchangeRateRequest.getAmount().multiply(exchange.getExchangeRate()).doubleValue());
        return exchangeRateResponse;
    }
}
