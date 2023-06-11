import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainInterface implements ActionListener {
    JFrame mainFrame;
    JLabel titleLab = new JLabel("Train Booking Application ", SwingConstants.CENTER);
    Icon img = new ImageIcon("train.jfif");
    JLabel picLab = new JLabel(img);
    JButton search = new JButton("Search");
    JButton makeBooking = new JButton("Make Booking");
    JButton viewRoutes = new JButton("View Routes");
    JButton viewServices = new JButton("View Class Services");
    JButton cancelButton = new JButton("Cancel Booking");
    JTextField searchtext = new JTextField("Search");
    JTextField startStop = new JTextField("Start stop");
    JTextField destination = new JTextField("Destination");
    TrainLines tr = new TrainLines();
    Booking bk = new Booking();
    String uname;
    MainInterface(String username){
        uname = username;
        //titleLab.setBounds(0,0,600,50);
        //titleLab.setBackground(Color.decode("#BF4F65"));

        search.setBounds(375,10,100,25);
        //search.setIcon(serchimage);
        search.addActionListener(this);
        searchtext.setBounds(125,10,200,25);

        viewRoutes.setBounds(25,70,150,25);
        viewRoutes.addActionListener(this);

        viewServices.setBounds(200,70,200,25);
        viewServices.addActionListener(this);

        picLab.setBounds(0,120,600,400);
        picLab.setOpaque(true);

        cancelButton.setBounds(425,70,125,25);
        cancelButton.addActionListener(this);

        startStop.setBounds(25,600,150,25);

        destination.setBounds(225,600,150,25);

        makeBooking.setBounds(425,600,125,25);
        makeBooking.addActionListener(this);

        mainFrame = new JFrame("Train Booking Application ");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(610,700);
        mainFrame.setLocation(400,50);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.getContentPane().setBackground(Color.decode("#818589"));
        mainFrame.setResizable(false);

        mainFrame.add(titleLab);
        mainFrame.add(search);
        mainFrame.add(searchtext);
        mainFrame.add(viewRoutes);
        mainFrame.add(startStop);
        mainFrame.add(destination);
        mainFrame.add(makeBooking);
        mainFrame.add(viewServices);
        mainFrame.add(cancelButton);
        mainFrame.add(picLab);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==search){
            String ser = searchtext.getText();
            ArrayList<String> tempList = new ArrayList<>();
            tr.Search(tempList,ser);
            if (tempList.isEmpty()){
                JOptionPane.showMessageDialog(null,"No search matches entry");
            }else{
                serTableRes serFrame =  new serTableRes(tempList);
            }
        }

        if (e.getSource()==viewRoutes){
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    File myFile = new File("Routes project.pdf");
                    desktop.open(myFile);
                } catch (IOException ex) {}
            }
        }

        if (e.getSource()==makeBooking){
            stopAndDest result = tr.startAndDest(startStop.getText(),destination.getText());
            if (result == null){
                JOptionPane.showMessageDialog(null,"Null Error");
            }else{
                JOptionPane.showMessageDialog(null,"Lesgooo");
                BookingInterface bk = new BookingInterface(result,uname);
            }
        }

        if (e.getSource()==viewServices){
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    File myFile = new File("ServicesProject.pdf");
                    desktop.open(myFile);
                } catch (IOException ex) {}
            }
        }

        if(e.getSource()==cancelButton){
            if (bk.checkBooking(uname)){
                JOptionPane.showMessageDialog(null,"Please wait a moment");
                CancelInterface c = new CancelInterface(uname);
            }else{
                JOptionPane.showMessageDialog(null,"You have not made a booking");
            }
        }
    }
}
