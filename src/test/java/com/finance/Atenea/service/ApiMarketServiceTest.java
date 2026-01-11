package com.finance.Atenea.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.finance.Atenea.config.RestTemplateConfig;
import com.finance.Atenea.model.Currency;
import com.finance.Atenea.model.assets.Money;

@SpringBootTest(classes = {
        ApiMarketService.class,
        RestTemplateConfig.class
})
@TestPropertySource(properties = {
        "alpha.vantage.api.key=LMAO!"
})
class ApiMarketServiceTest {

    @Autowired
    private ApiMarketService service;

    @Test
    void stockPriceReturnsUsdMoney() {
        Money price = service.stockPrice("IBM");

        assertEquals(Currency.USD, price.getCurrency());
        assertNotNull(price.getAmount());
        assertTrue(price.getAmount().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void fxRateReturnsTargetCurrency() throws InterruptedException {
        Thread.sleep(1100);
        Money rate = service.fxRate(Currency.EUR, Currency.USD);

        assertEquals(Currency.USD, rate.getCurrency());
        assertNotNull(rate.getAmount());
        assertTrue(rate.getAmount().compareTo(BigDecimal.ZERO) > 0);
    }
}
