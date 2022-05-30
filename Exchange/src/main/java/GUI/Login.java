package GUI;

import auth.Authenticable;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JLabel usernameLabel, passwordLabel, message;
    private JTextField usernameText;
    private JPasswordField passwordText;

    private JButton submit, register;
    public Login(String windowName) {
        super(windowName);

        setDefaultCloseOperation(Login.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 400));
        setSize(400, 400);

        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

        usernameLabel = new JLabel();
        usernameLabel.setText("Username:");
        usernameLabel.setBounds(70, 100, 100, 30);

        usernameText = new JTextField();
        usernameText.setBounds(170, 100, 160, 30);

        passwordLabel = new JLabel();
        passwordLabel.setText("Password:");
        passwordLabel.setBounds(70, 140, 100, 30);

        passwordText = new JPasswordField();
        passwordText.setBounds(170, 140, 160, 30);

        message = new JLabel();
//        message.setText("Aici va fi mesajul!!");
        message.setBounds(140, 170, 260, 30);

        submit = new JButton("Login");
        submit.setBounds(100, 200, 80, 25);

        register = new JButton("Register");
        register.setBounds(200, 200, 100, 25);


        add(usernameLabel);
        add(usernameText);
        add(passwordLabel);
        add(passwordText);

        add(message);
        add(submit);
        add(register);

        setLayout(null);
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(JLabel usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public void setPasswordLabel(JLabel passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    public JLabel getMessage() {
        return message;
    }

    public void setMessage(JLabel message) {
        this.message = message;
    }

    public JTextField getUsernameText() {
        return usernameText;
    }

    public void setUsernameText(JTextField usernameText) {
        this.usernameText = usernameText;
    }

    public JPasswordField getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(JPasswordField passwordText) {
        this.passwordText = passwordText;
    }

    public JButton getSubmit() {
        return submit;
    }

    public void setSubmit(JButton submit) {
        this.submit = submit;
    }

    public JButton getRegister() {
        return register;
    }

    public void setRegister(JButton register) {
        this.register = register;
    }
}
