package com.example.stocks.ui.market.securities;

public enum Currency {
    RUB("₽", "RUB"),
    USD("$", "USD"),
    EUR("€", "EUR"),
    GBP("£", "GBR"), // Great Britain pound
    JPY("¥", "JPY"), // Japanese yen
    CHF("₣", "CHF");  // Swiss franc

    Currency(String currencySymbol, String currencyCode) {
        this.currencyCode = currencyCode;
    }

    private String currencySymbol;
    private String currencyCode;

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
