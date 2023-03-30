package com.nttdata.exchangerateservice.exchangerateservice.controller;

import com.nttdata.exchangerateservice.exchangerateservice.entity.Exchange;
import com.nttdata.exchangerateservice.exchangerateservice.model.ExchangeRateRequest;
import com.nttdata.exchangerateservice.exchangerateservice.service.ExchangeServiceImpl;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

class ExchangeControllerTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    ExchangeServiceImpl exchangeService;

    @InjectMocks
    ExchangeController exchangeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Method: applyExchangeRate")
    class applyExchangeRate {

        @Nested
        @DisplayName("Happy Path")
        class happyPath {

            @Test
            void test_applyExchangeRate_WhenServiceReturnExchangeEntity() {
                Mockito.when(exchangeService.getExchangeRate(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
                        .thenReturn(Single.just(buildExchange()));
                exchangeController.applyExchangeRate(buildExchangeRateRequest())
                        .test()
                        .assertComplete()
                        .assertNoErrors()
                        .assertValue(responseEntity -> responseEntity.getStatusCode() == HttpStatus.OK);
            }
        }

        @Nested
        @DisplayName("Unhappy Path")
        class unHappyPath {

            @Test
            void test_applyExchangeRate_WhenServiceReturnError(){
                Mockito.when(exchangeService.getExchangeRate(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
                        .thenReturn(Single.error(new Exception()));
                exchangeController.applyExchangeRate(buildExchangeRateRequest())
                        .test()
                        .assertComplete()
                        .assertNoErrors()
                        .assertValue(responseEntity -> responseEntity.getStatusCode() == HttpStatus.NOT_FOUND);
            }
        }
    }

    @Nested
    @DisplayName("Method: getAll")
    class getAll {

        @Nested
        @DisplayName("Happy Path")
        class happyPath {

            @Test
            void test_getAll_WhenServiceReturnListExchange() {
                Mockito.when(exchangeService.getAll())
                        .thenReturn(Maybe.just(List.of(buildExchange(), buildExchange())));
                exchangeController.getAll()
                        .test()
                        .assertComplete()
                        .assertNoErrors()
                        .assertValue(responseEntity -> responseEntity.getStatusCode() == HttpStatus.OK);
            }
        }

        @Nested
        @DisplayName("Unhappy Path")
        class unHappyPath {

            @Test
            void test_getAll_WhenServiceReturnError(){
                Mockito.when(exchangeService.getAll())
                        .thenReturn(Maybe.error(new Exception()));
                exchangeController.getAll()
                        .test()
                        .assertComplete()
                        .assertNoErrors()
                        .assertValue(responseEntity -> responseEntity.getStatusCode() == HttpStatus.NOT_FOUND);
            }
        }
    }

    private ExchangeRateRequest buildExchangeRateRequest(){
        ExchangeRateRequest exchangeRateRequest = new ExchangeRateRequest();
        exchangeRateRequest.setCurrencyOriginId(1L);
        exchangeRateRequest.setCurrencyDestinyId(2L);
        exchangeRateRequest.setAmount(new BigDecimal("2"));
        return exchangeRateRequest;
    }
    private Exchange buildExchange(){
        return Exchange.builder().currencyOrigin(1).currencyDestiny(2).exchangeRate(new BigDecimal("3.77")).build();
    }
}
