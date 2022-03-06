package com.example.stocks.ui.market.securities;

public enum Currency {
    RUB("₽"),
    USD("$"),
    EUR("€"),
    GBP("£"), // Great Britain pound
    JPY("¥"), // Japanese yen
    CHF("₣");  // Swiss franc

    Currency(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    private String currencySymbol;

    public String getCurrencySymbol() {
        return currencySymbol;
    }
}
