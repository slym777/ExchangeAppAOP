package service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVService {
    private static CSVService ourInstance = new CSVService();

    public static CSVService getInstance() {
        return ourInstance;
    }

    private CSVService() {
    }

    public List<List<String>> readCSVData(String filePath, String fileName) throws FileNotFoundException, IOException {
        List<List<String>> result = new ArrayList<>();
        String row;

        BufferedReader csvReader = new BufferedReader(new FileReader(filePath + fileName));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            List<String> listData = new ArrayList<>();
            for (String el: data) listData.add(el);

            result.add(listData);
        }

        csvReader.close();
        return result;
    }

    private void writeCSVData(String filePath, String fileName, List<String> header, List<List<String>> data) throws IOException {
        FileWriter csvWriter = new FileWriter(filePath + fileName);
        for (String el: header) {
            csvWriter.append(el);

            if (!el.equals(header.get(header.size() - 1))) {
                csvWriter.append(",");
            }
        }
        csvWriter.append("\n");

        for (List<String> row: data) {
            csvWriter.append(String.join(",", row));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }

    public void createCSVExchangeOffice() throws IOException {
        List<String> head = Arrays.asList("Name", "Country", "Address", "PhoneNumber");
        List<List<String>> data = Arrays.asList(
                Arrays.asList("Exchange SRL", "Romania", "str. Splaiul Independentei 204", "+40732671236")
        );

        writeCSVData("src/main/java/files/", "exchangeOfficeInfo.csv", head, data);
    }

    public void createCSVUsers() throws IOException {
        List<String> head = Arrays.asList("First Name", "Last Name", "Username", "PasswordHash", "Country", "Address");
        List<List<String>> data = Arrays.asList(
                Arrays.asList("Sandu", "Petrasco", "godzilla", "asdi123ns", "Romania", "Splaiul Independentei 204"),
                Arrays.asList("Ion", "Popescu", "ipopescu", "jfj221ksa9", "Romania", "Iuliu Maniu 207")
        );

        writeCSVData("src/main/java/files/", "usersInfo.csv", head, data);
    }

    public void createCSVCurrency() throws IOException {
        List<String> head = Arrays.asList("Currency Name", "Currency Code", "Currency Symbol", "Quantity", "Rate (1$ to currency)");
        List<List<String>> data = Arrays.asList(
                Arrays.asList("Euro", "EUR", "€", "1000.0", "0.890452"),
                Arrays.asList("United States Dollar", "USD", "$", "1000.0", "1.000000"),
                Arrays.asList("British Pound", "GBP", "£", "1000.0", "0.765682"),
                Arrays.asList("Romanian leu", "RON", "lei", "10000.0", "4.243342"),
                Arrays.asList("Moldovan leu", "MDL", "L", "20000.0", "17.374685"),
                Arrays.asList("Russian ruble", "RUB", "\u20BD", "80000.0", "65.469646"),
                Arrays.asList("Swiss franc", "CHF", "Fr", "1000.0", "0.994877")
        );

        writeCSVData("src/main/java/files", "currencyInfo.csv", head, data);
    }
}
