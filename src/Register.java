import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Register implements ActionListener {
    JFrame regFrame;
    JLabel lab1 = new JLabel("Train Booking Application ",SwingConstants.CENTER);
    JLabel lab2 = new JLabel("Email Address  ");
    JLabel lab3 = new JLabel("Password   ");
    JLabel lab4 = new JLabel("Confirm      ");
    JLabel lab5 = new JLabel("Name          ");
    JTextField text1 = new JTextField("");
    JTextField text2 = new JTextField("");
    JPasswordField jPasswordField1 = new JPasswordField("");
    JPasswordField jPasswordField2 = new JPasswordField("");
    JButton showPassword = new JButton("Show Password");
    JButton regButton = new JButton("Register ");
    EmailChecker emailChecker = new EmailChecker();
    boolean shpwflag = false;

    Register(){
        lab1.setBounds(0,0,400,50);
        lab1.setOpaque(true);
        lab1.setBackground(Color.decode("#BF4F65"));

        lab2.setBounds(50,100,150,50);
        text1.setBounds(150,115,150,25);

        lab3.setBounds(50,200,150,50);
        jPasswordField1.setBounds(150,215,150,25);

        lab4.setBounds(50,250,150,50);
        jPasswordField2.setBounds(150,265,150,25);

        lab5.setBounds(50,150,150,50);
        text2.setBounds(150,165,150,25);

        showPassword.setBounds(150,350,140,25);
        showPassword.addActionListener(this);

        regButton.setBounds(170,400,100,25);
        regButton.addActionListener(this);

        regFrame = new JFrame();
        //regFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        regFrame.setSize(400,500);
        regFrame.setLocation(500,250);
        regFrame.setLayout(null);
        regFrame.setVisible(true);
        regFrame.setBackground(Color.LIGHT_GRAY);
        regFrame.setResizable(false);

        regFrame.add(lab1);
        regFrame.add(lab2);
        regFrame.add(text1);
        regFrame.add(lab3);
        regFrame.add(jPasswordField1);
        regFrame.add(lab4);
        regFrame.add(jPasswordField2);
        regFrame.add(lab5);
        regFrame.add(text2);
        regFrame.add(regButton);
        regFrame.add(showPassword);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==regButton){
            String username = text1.getText();
            String name = text2.getText();
            String pw1 = String.valueOf(jPasswordField1.getPassword());
            String pw2 = String.valueOf(jPasswordField2.getPassword());
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Blasted1@3894");
                Statement stmt = con.createStatement();
                ResultSet rs1 = stmt.executeQuery("SELECT `users`.`password` FROM `mydb`.`users` WHERE `users`.`username` = '"+username+"'");
                if (rs1.next()==true) {
                    JOptionPane.showMessageDialog(null, "Email Address Already Exists, Please Try Again");
                } else{
                    if (pw1.equals(pw2)){
                        if (pw1.length()>=8){
                            boolean charcheck = false;
                            boolean digitcheck = false;
                            boolean spcharcheck = false;
                            for (int i = 0; i < pw1.length(); i++){
                                if (Character.isDigit(pw1.charAt(i))){
                                    digitcheck = true;
                                }else if(Character.isAlphabetic(pw1.charAt(i))){
                                    charcheck = true;
                                }else{
                                    spcharcheck = true;
                                }
                            }
                            if (digitcheck && charcheck && spcharcheck){
                                if (emailChecker.isAddressValid(username)){
                                    String q1 = "INSERT INTO `mydb`.`users` VALUES ('"+username+"','"+pw1+"','"+name+"')";
                                    stmt.execute(q1);
                                    //Login l = new Login();
                                    regFrame.dispose();
                                }else {
                                    JOptionPane.showMessageDialog(null,"Email Address does not exist");
                                }
                            } else{
                              JOptionPane.showMessageDialog(null,"Password must contain at least 1 letter, 1 digit, 1 special character");
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Password must be 8 characters long");
                        }

                    } else{
                        JOptionPane.showMessageDialog(null,"Passwords don't match ");
                    }
                }
            } catch (Exception ef) {
            }
        }

        if (e.getSource()==showPassword){
            if (!shpwflag){
                jPasswordField1.setEchoChar((char)0);
                jPasswordField2.setEchoChar((char)0);
                shpwflag = true;
            }else{
                jPasswordField1.setEchoChar('*');
                jPasswordField2.setEchoChar('*');
                shpwflag = false;
            }
        }
    }
}
