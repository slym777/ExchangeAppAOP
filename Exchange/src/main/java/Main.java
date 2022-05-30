//import DAO.CurrencyDAO;
//import DAO.DAOImplementation.CurrencyDAOImpl;
//import DAO.DAOImplementation.UserDAOImpl;
//import model.*;
//import model.Currency;
//import service.*;
//import utils.ConnectionUtils;
//
//import java.io.*;
//import java.sql.Connection;
//import java.sql.Statement;
//import java.util.*;
//import java.util.logging.Logger;
//
//public class Main {
//    private static Logger logger = null;
//    public static void main(String[] args) throws IOException, FileNotFoundException {
//
//        // --------------------- access to services ----------------------
//        UserService US = UserService.getInstance();
//        CurrencyService CS = CurrencyService.getInstance();
//        TransactionService TS = TransactionService.getInstance();
//        CurrencyHistoryService CHS = CurrencyHistoryService.getInstance();
//        ExchangeRateService ERS = ExchangeRateService.getInstance();
//        ExchangeOffice EO = ExchangeOffice.getInstance();
//        // ---------------------------------------------------------------
//
//        List<ExchangeRate> exchangeRates = ExchangeRateService.getListOfExchangeRates();
//        Map<Currency, Double> quantity = EO.getQuantity();
//        List<User> users = UserService.getListOfUsers();
//
//
//        CurrencyDAOImpl dbC = new CurrencyDAOImpl();
//        dbC.createCurrencyTable();
////        dbC.insertAll(exchangeRates, quantity);
//
////        // dbC.delete(1);
////
////        System.out.println(dbC.selectCurrencyById(1).getRate() + " " + dbC.selectCurrencyById(1).getCurrencyCode());
////        System.out.println(dbC.selectQuantityById(1));
//
//        UserDAOImpl dbU = new UserDAOImpl();
//        dbU.createUserTable();
////        dbU.insertAll(users);
//
//        // dbU.delete(1);
//        // dbU.insert(US.getUserByUsername("godzilla"));
//
//
//        try {
//            logger = CSVLogger.getInstance();
//        } catch (Exception ex) {
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            ex.printStackTrace(pw);
//            String stackTrace = sw.toString();
//
//            logger.severe("Program termination with stack trace:\n" + stackTrace);
//            ex.printStackTrace();
//        }
//
//        logger.info("PROGRAM STARTED");
//
//        try {
//            Scanner obj = new Scanner(System.in);
//
//            logger.info("Give access to services");
//
//            // ---------------------- create CSV's ---------------------------
////            logger.info("Creating CSV's...");
////            CSVService csvService = CSVService.getInstance();
////            csvService.createCSVExchangeOffice();
////            csvService.createCSVUsers();
////            csvService.createCSVCurrency();
//            // ---------------------------------------------------------------
//
//
//            logger.info("Get all available currencies");
//            // -------------------- find available currencies ----------------
//            System.out.println("The current available currencies:");
//            List<Currency> currencies = CurrencyService.getListOfCurrencies();
//            for (Currency currency : currencies) {
//                System.out.printf("%s (%s, %s)\n", currency.getCurrencyName(), currency.getCurrencyCode(), currency.getCurrencySymbol());
//            }
//            // ---------------------------------------------------------------
//
//            logger.info("Get exchanging rates for user");
//            // -------------------- exchange rates ------------------------
//            // ERS.updateExchangeRates();
//            // List<ExchangeRate> exchangeRates = ExchangeRateService.getListOfExchangeRates();
//            System.out.println("\nCurrent Exchange Rates:");
//            for (ExchangeRate exchangeRate : exchangeRates) {
//                System.out.printf("%s %.6f\n", exchangeRate.getCurrencyCode(), exchangeRate.getRate());
//            }
//            // ------------------------------------------------------------
//
//            logger.info("STARTING THE TRANSACTIONS");
//            // ------------------- transactions ---------------------------
//            System.out.println("Number of transactions to be made: ");
//            int nrOfTransactions = Integer.parseInt(obj.nextLine());
//            while (nrOfTransactions > 0) {
//                TS.makeNewTransaction();
//                nrOfTransactions--;
//            }
//
//            List<Transaction> transactions = TransactionService.getListOfTransactions();
//
//            logger.info("There were " + transactions.size() + " transactions registered");
//            System.out.println("\nTotal number of transactions made: " + transactions.size());
//            int counterTrans = 0;
//            for (Transaction transaction : transactions) {
//                counterTrans++;
//                if (!transaction.getDone()) {
//                    logger.warning("Transaction " + counterTrans + " failed!");
//                    System.out.printf("The exchange of %.6f %s in %s failed!\n", transaction.getFromSum(), transaction.getFromCurrency().getCurrencyCode(), transaction.getToCurrency().getCurrencyCode());
//                } else {
//                    System.out.printf("Transaction accepted: %.6f %s to %.6f %s\n", transaction.getFromSum(), transaction.getFromCurrency().getCurrencyCode(),
//                            transaction.getToSum(), transaction.getToCurrency().getCurrencyCode());
//                }
//            }
//            // ------------------------------------------------------------
//
//            logger.info("Searching for a specific user");
//            // -------------- search for a specific user -----------------
//            System.out.println("\nGive the username of the user to search for ");
//            String username = obj.nextLine();
//
//            User user = US.getUserByUsername(username);
//
//            if (user == null) {
//                logger.warning("NO USER - " + username + " - FOUND");
//                System.out.println("There is no such user.");
//            } else {
//                System.out.println("Last name of the searched user: " + user.getLastName());
//                System.out.println(user.getLastName() + "'s Transactions:");
//                List<Transaction> transactionList = user.getListOfTransactions();
//                for (int i = 0; i < transactionList.size(); i++) {
//                    Transaction transaction = transactionList.get(i);
//                    if (!transaction.getDone()) {
//                        System.out.printf("%d. The exchange of %.6f %s in %s failed!\n", i + 1, transaction.getFromSum(), transaction.getFromCurrency().getCurrencyCode(), transaction.getToCurrency().getCurrencyCode());
//                    } else {
//                        System.out.printf("Transaction accepted: %.6f %s to %.6f %s\n", transaction.getFromSum(), transaction.getFromCurrency().getCurrencyCode(),
//                                transaction.getToSum(), transaction.getToCurrency().getCurrencyCode());
//                    }
//                }
//            }
//            // ------------------------------------------------------------
//
//            logger.info("Get info about currency buys&sales");
//            // ----------- get info about currency sale and buy -----------
//            System.out.println("\nInfo about currency sale: ");
//            Map<Currency, Double> salesInfo = TS.getCurrencySaleInfo();
//
//            for (Map.Entry<Currency, Double> entry : salesInfo.entrySet()) {
//                System.out.printf("%s <- %.6f\n", entry.getKey().getCurrencyCode(), entry.getValue());
//            }
//
//            System.out.println("\nInfo about currency buy: ");
//            Map<Currency, Double> buysInfo = TS.getCurrencyBuyInfo();
//
//            for (Map.Entry<Currency, Double> entry : buysInfo.entrySet()) {
//                System.out.printf("%s <- %.6f\n", entry.getKey().getCurrencyCode(), entry.getValue());
//            }
//            // ------------------------------------------------------------
//
//            logger.info("Get info about currency left in the exchange office");
//            // ---- get info about currency left in the exchange office ----
//            System.out.println("\nInfo about the currency quantity left in the exchange office:");
//            // Map<Currency, Double> quantity = EO.getQuantity();
//            for (Map.Entry<Currency, Double> entry : quantity.entrySet()) {
//                System.out.printf("%s <- %.6f\n", entry.getKey().getCurrencyCode(), entry.getValue());
//            }
//            // -------------------------------------------------------------
//        } catch (Exception e) {
//            System.out.println(e.getMessage() + "\n" + e.getCause() + "\n" + e.getLocalizedMessage());
//        }
//
//        logger.info("PROGRAM ENDED\n\n");
//
//    }
//}
