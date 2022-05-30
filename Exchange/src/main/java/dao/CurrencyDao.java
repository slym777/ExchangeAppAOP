package dao;

import model.Currency;
import model.ExchangeRate;

import java.util.List;
import java.util.Map;

public interface CurrencyDao {
    void insert(ExchangeRate exchangeRate);
    int selectByCode(String code);
    void update(int id, ExchangeRate exchangeRate);
    void update(Currency currency, Double quantity);
    void delete(int id);
    ExchangeRate selectCurrencyById(int id);
    Double selectQuantityById(int id);
    List<Currency> selectAllCurrencies();
    void insertAll(List<ExchangeRate> exchangeRates, Map<Currency, Double> quantity);
}
