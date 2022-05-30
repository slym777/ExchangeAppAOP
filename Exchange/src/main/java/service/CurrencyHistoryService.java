package service;

public class CurrencyHistoryService {
    private static CurrencyHistoryService ourInstance = new CurrencyHistoryService();

    public static CurrencyHistoryService getInstance() {
        return ourInstance;
    }

    private CurrencyHistoryService() {
    }
}
