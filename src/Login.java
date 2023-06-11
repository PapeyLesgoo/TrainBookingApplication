import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login implements ActionListener {
    JFrame loginFrame;
    JLabel lab1 = new JLabel("Train Booking Application ",SwingConstants.CENTER);
    JLabel lab2 = new JLabel("Email Address : ");
    JLabel lab3 = new JLabel("Password : ");
    JTextField text1 = new JTextField("");
    JPasswordField passwordField = new JPasswordField("");
    JButton loginButton = new JButton("Login ");
    JButton regButton = new JButton("Register ");
    JButton showPassword = new JButton("Show Password");
    boolean shpwflag = false;
    Login(){
        lab1.setBounds(0,0,375,50);
        lab1.setOpaque(true);
        lab1.setBackground(Color.decode("#BF4F65"));

        lab2.setBounds(50,100,150,50);
        text1.setBounds(150,115,150,25);

        lab3.setBounds(50,150,150,50);
        passwordField.setBounds(150,165,150,25);

        showPassword.setBounds(120,225,140,25);
        showPassword.addActionListener(this);

        loginButton.setBounds(75,275,100,25);
        loginButton.addActionListener(this);
        regButton.setBounds(200,275,100,25);
        regButton.addActionListener(this);

        loginFrame = new JFrame();
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.setSize(375,400);
        loginFrame.setLocation(600,250);
        loginFrame.setLayout(null);
        loginFrame.setVisible(true);
        loginFrame.setBackground(Color.LIGHT_GRAY);
        loginFrame.setResizable(false);

        loginFrame.add(lab1);
        loginFrame.add(lab2);
        loginFrame.add(text1);
        loginFrame.add(lab3);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.add(regButton);
        loginFrame.add(showPassword);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==loginButton){
            String username = text1.getText();
            String password = String.valueOf(passwordField.getPassword());
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Blasted1@3894");
                Statement stmt = con.createStatement();
                ResultSet rs1 = stmt.executeQuery("SELECT `users`.`password` FROM `mydb`.`users` WHERE `users`.`username` = '"+username+"'");
                if (rs1.next()==false){
                    JOptionPane.showMessageDialog(null,"Invalid Email Address, Please Try Again");
                }else if (rs1.getString("password").equals(password)){
                    //System start
                    JOptionPane.showMessageDialog(null,"Login Successful");
                    MainInterface m = new MainInterface(username);
                    loginFrame.dispose();
                } else{
                    JOptionPane.showMessageDialog(null,"Invalid Password, Please Try Again");
                }
            } catch (Exception ef) {
            }
        }

        if (e.getSource()==regButton){
            Register r = new Register();
            //loginFrame.dispose();
        }

        if (e.getSource()==showPassword){
            if (!shpwflag){
                passwordField.setEchoChar((char)0);
                shpwflag = true;
            }else{
                passwordField.setEchoChar('*');
                shpwflag = false;
            }
        }
    }
}
