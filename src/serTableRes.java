import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class serTableRes implements ActionListener {
    JFrame serTable;
    JButton viewRoutes = new JButton("View Routes");
    JButton back = new JButton("Back");
    
    serTableRes(ArrayList<String> serList){
        String[] column = {"Route and Stop Number","Stop Name"};
        String[][] data = new String[serList.size()][2];
        for (int i = 0; i <serList.size();i++){
            data[i] = serList.get(i).split(",");
            System.out.println(Arrays.toString(data[i]));
        }
        JTable jt = new JTable(data,column);
        jt.setBounds(0,30,600,200);
        jt.setEnabled(false);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(0,30,300,serList.size()*25+20);
        JTableHeader Theader = jt.getTableHeader();
        Theader.setBackground(Color.red);
        Theader.setForeground(Color.white);

        viewRoutes.setBounds(75,serList.size()*25+50,125,25);
        viewRoutes.addActionListener(this);

        back.setBounds(0,0,75,25);
        back.addActionListener(this);

        serTable = new JFrame("Search Results");
        //serTable.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        serTable.setSize(310,200);
        serTable.setLocation(400,50);
        serTable.setLayout(null);
        serTable.setVisible(true);
        serTable.getContentPane().setBackground(Color.decode("#818589"));
        serTable.setResizable(false);

        serTable.add(sp);
        serTable.add(viewRoutes);
        serTable.add(back);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==viewRoutes){
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    File myFile = new File("Routes project.pdf");
                    desktop.open(myFile);
                } catch (IOException ex) {}
            }
        }

        if (e.getSource()==back){
            System.out.println("B");
            serTable.dispose();
            System.out.println("A");
        }
    }
}
