package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.ExchangeRateResponse;
import org.example.exception.ExchangeRateNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ExchangeRateService {
    private final WebClient.Builder webClientBuilder;

    @Value("${api.nbp.url.usd}")
    private final String nbpApiUrl = "http://api.nbp.pl/api/exchangerates/rates/c/usd/last/";

    public BigDecimal getPlnToUsdAskExchangeRate() {
        ExchangeRateResponse response = getExchangeRateFromApi(nbpApiUrl);

        if (response != null && !response.getRates().isEmpty()) {
            return response.getRates().get(0).getAsk();
        } else {
            throw new ExchangeRateNotFoundException("Unable to fetch exchange rate for PLN -> USD");
        }
    }

    public BigDecimal getPlnToUsdBidExchangeRate() {
        ExchangeRateResponse response = getExchangeRateFromApi(nbpApiUrl);

        if (response != null && !response.getRates().isEmpty()) {
            return response.getRates().get(0).getBid();
        } else {
            throw new ExchangeRateNotFoundException("Unable to fetch exchange rate for USD -> PLN");
        }
    }

    private ExchangeRateResponse getExchangeRateFromApi(String url) {
        return webClientBuilder.build()
                .get()
                .uri(UriComponentsBuilder.fromHttpUrl(url).toUriString())
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                .block();
    }
}
