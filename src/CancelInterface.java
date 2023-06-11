import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CancelInterface implements ActionListener {
    JFrame canFrame;
    String[][] realdata;
    String uname;
    JButton canButton = new JButton("Cancel Booking");
    JButton back =  new JButton("Back");
    JComboBox bkIdsbox;
    Booking bk = new Booking();
    JLabel lab1 = new JLabel("Select Ticket Number");
    CancelInterface(String username){
        uname = username;
        ArrayList<String> tempList = new ArrayList<>();
        bk.fillCanTable(tempList,username);
        String data[][] = new String[tempList.size()][2];
        for (int i = 0; i<tempList.size();i++){
            data[i] = tempList.get(i).split(",");
        }
        Integer[] ids = new Integer[tempList.size()];
        String[] column = {"Ticket Number", "Reservation date"};
        JTable jt = new JTable(data,column);
        jt.setBounds(0,30,300,300);
        jt.setEnabled(false);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(0,30,500,tempList.size()*30+25);
        JTableHeader Theader = jt.getTableHeader();
        Theader.setBackground(Color.red);
        Theader.setForeground(Color.white);
        
        for (int i = 0; i < data.length;i++){
            ids[i] = Integer.parseInt(data[i][0]);
        }
        bkIdsbox = new JComboBox<>(ids);
        bkIdsbox.setBounds(25,tempList.size()*30+80,100,25);
        bkIdsbox.setEnabled(true);

        lab1.setBounds(10,tempList.size()*30+50,125,25);
        lab1.setBackground(Color.LIGHT_GRAY);

        canButton.setBounds(250,tempList.size()*30+80,150,25);
        canButton.addActionListener(this);

        back.setBounds(0,0,75,25);
        back.addActionListener(this);

        canFrame = new JFrame("Cancel Booking Window");
        //canFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        canFrame.setSize(510,tempList.size()*30+200);
        canFrame.setLocation(420,75);
        canFrame.setLayout(null);
        canFrame.setVisible(true);
        canFrame.getContentPane().setBackground(Color.decode("#818589"));
        canFrame.setResizable(false);

        canFrame.add(sp);
        canFrame.add(bkIdsbox);
        canFrame.add(canButton);
        canFrame.add(lab1);
        canFrame.add(back);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==canButton){
            int id = (int) bkIdsbox.getSelectedItem();
            bk.CancelBooking(id,uname);
        }

        if (e.getSource()==back){
            canFrame.dispose();
        }
    }
}
