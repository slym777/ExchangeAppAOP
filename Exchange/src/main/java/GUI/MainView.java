package GUI;

import model.Currency;
import model.ExchangeOffice;
import model.ExchangeRate;
import model.Transaction;
import service.TransactionService;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainView extends JFrame {
    private JButton login;
    private JTextArea textArea1, textArea2, textArea3, textArea4;

    public MainView(String windowName) {
        super(windowName);

        setDefaultCloseOperation(Login.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(975, 650));
        setSize(975, 600);

        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

        login = new JButton("Log in");
        login.setBounds(825, 50, 100, 30);

        add(login);
        setLayout(null);

        JLabel label = new JLabel("Welcome, Guest!");
        label.setFont(new Font("Tahoma", Font.BOLD|Font.ITALIC, 30));
        label.setBounds(50, 50, 400, 50);
        add(label);


        textArea1 = new JTextArea("");
        textArea1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        textArea1.setEditable(false);

        JLabel label1 = new JLabel("  Transactions Summary");
        label1.setFont(new Font("Tahoma", Font.BOLD, 13));
        JScrollPane scrollPane1 = new JScrollPane(textArea1);
        scrollPane1.setColumnHeaderView(label1);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setBounds(50, 170, 200, 300);
        add(scrollPane1);


        textArea2 = new JTextArea("");
        textArea2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        textArea2.setEditable(false);

        JLabel label2 = new JLabel("  Buy Info (this session)");
        label2.setFont(new Font("Tahoma", Font.BOLD, 13));
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        scrollPane2.setColumnHeaderView(label2);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setBounds(275, 170, 200, 300);
        add(scrollPane2);


        textArea3 = new JTextArea("");
        textArea3.setFont(new Font("Tahoma", Font.PLAIN, 13));
        textArea3.setEditable(false);

        JLabel label3 = new JLabel("  Sale Info (this session)");
        label3.setFont(new Font("Tahoma", Font.BOLD, 13));
        JScrollPane scrollPane3 = new JScrollPane(textArea3);
        scrollPane3.setColumnHeaderView(label3);
        scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.setBounds(500, 170, 200, 300);
        add(scrollPane3);


        textArea4 = new JTextArea("");
        textArea4.setFont(new Font("Tahoma", Font.PLAIN, 13));
        textArea4.setEditable(false);

        JLabel label4 = new JLabel("  Currency quantity left");
        label4.setFont(new Font("Tahoma", Font.BOLD, 13));
        JScrollPane scrollPane4 = new JScrollPane(textArea4);
        scrollPane4.setColumnHeaderView(label4);
        scrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane4.setBounds(725, 170, 200, 300);
        add(scrollPane4);

        updateFields();
    }

    public void updateFields() {
        List<Transaction> transactionList = TransactionService.getListOfTransactions();

        if (transactionList.size() == 0) {
            textArea1.setText("\n No transaction registered!");
        } else {
            textArea1.setText("");

            for (int i = 0; i < transactionList.size(); i++) {
                textArea1.setText(textArea1.getText() + "\n  " +
                        (i + 1) + ") " + transactionList.get(i).toString() + "\n");
            }
        }


        TransactionService TS = TransactionService.getInstance();
        Map<Currency, Double> buyInfo = TS.getCurrencyBuyInfo();

        if (buyInfo.size() == 0) {
            textArea2.setText("\n No information!");
        } else {
            textArea2.setText("");
            for (Map.Entry<Currency, Double> entry : buyInfo.entrySet()) {
                textArea2.setText(textArea2.getText() + "\n  " +
                        entry.getKey().getCurrencyCode() + " <- " +  new DecimalFormat("##############.##").format(entry.getValue()));
            }

        }


        Map<Currency, Double> saleInfo = TS.getCurrencySaleInfo();

        if (saleInfo.size() == 0) {
            textArea3.setText("\n No information!");
        } else {
            textArea3.setText("");
            for (Map.Entry<Currency, Double> entry : saleInfo.entrySet()) {
                textArea3.setText(textArea3.getText() + "\n  " +
                        entry.getKey().getCurrencyCode() + " <- " + new DecimalFormat("##############.##").format(entry.getValue()));
            }
        }


        ExchangeOffice EO = ExchangeOffice.getInstance();
        Map<Currency, Double> quantity = EO.getQuantity();

        if (quantity.size() == 0) {
            textArea4.setText("\n No information!");
        } else {
            textArea4.setText("");
            for (Map.Entry<Currency, Double> entry : quantity.entrySet()) {
                textArea4.setText(textArea4.getText() + "\n  " +
                        entry.getKey().getCurrencyCode() + " <- " + new DecimalFormat("##############.##").format(entry.getValue()));
            }
        }
    }

    public JButton getLogin() {
        return login;
    }
}
