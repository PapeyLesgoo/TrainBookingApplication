import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class BookingInterface implements ActionListener {
    JFrame bookFrame;
    JTable jt;
    TrainLines tr = new TrainLines();
    JLabel payLab = new JLabel("To pay with cash, go to nearest station and make payment at least before 2 days of your booking");
    JLabel yearLab = new JLabel("Year");
    JLabel monthLab = new JLabel("Month");
    JLabel dayLab = new JLabel("Days");
    JLabel trainLab = new JLabel("Train");
    JLabel classLab = new JLabel("Class");
    JLabel priceLab = new JLabel("Price");
    JLabel roomLab = new JLabel("Room");
    JLabel payBoxLab = new JLabel("Payment with Card");
    JComboBox<String> yearbox;
    JComboBox<Integer> monthbox;
    JComboBox<Integer> daysbox;
    JComboBox<Integer> trainNumBox;
    JComboBox<String> roomTypeBox;
    JComboBox<Integer> roomBox;
    JComboBox<String> payBox;
    JComboBox<Integer> price;
    JButton go = new JButton("Go");
    JButton back = new JButton("Back");
    boolean monthFlag = true;
    Integer[] realDays1;
    Integer[] realDays2;
    String[][] realData;
    String uname;
    int mlNum = 0;
    Booking bk = new Booking();

    BookingInterface(stopAndDest result,String username){
        uname = username;
        mlNum = result.mlNum+1;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        payLab.setBounds(0,500,535,25);

        String[] column = {"Start Stop","Departure Time","Destination","Arrival Time","Train Number"};
        String[][] data = new String[2][5];
        tr.TrainTimes(result, data);
        realData = data;
        jt = new JTable(data,column);
        jt.setBounds(0,30,600,60);
        jt.setEnabled(false);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(0,30,530,55);
        JTableHeader Theader = jt.getTableHeader();
        Theader.setBackground(Color.red);
        Theader.setForeground(Color.white);

        String temp1 = null;
        if ((12-now.getMonthValue())>=6){
            temp1 = "" + now.getYear();
        }else{
            temp1 = "" + now.getYear() + "," + (now.getYear()+1);
        }
        String[] year = temp1.split(",");
        yearbox = new JComboBox<>(year);
        yearbox.setBounds(110,200,75,25);
        yearLab.setBounds(130,170,50,25);
        //yearbox.setEnabled(false);

        Integer[] months = new Integer[6];

        int temp1max = 0;
        int remDaysMonth = 0;
        if (now.getDayOfMonth()+7>LocalDateTime.MAX.getDayOfMonth()){
            monthFlag = false;
            remDaysMonth = 14 + now.getDayOfMonth() - LocalDateTime.MAX.getDayOfMonth();
            if ((12-now.getMonthValue()+1)>=6){
                int j = 0;
                for (int i = now.getMonthValue()+1; i < now.getMonthValue()+6+1; i++){
                    months[j++] = i;
                }
            }else{
                int j = 0;
                for (int i = now.getMonthValue()+1; i < 13; i++){
                    months[j++] = i;
                }

                for (int i = 1; i <= (6+now.getMonthValue()+1)-12;i++){
                    months[j++] = i;
                }
            }
        }else{
            temp1max = LocalDateTime.MAX.getDayOfMonth() - (now.getDayOfMonth()+7);
            if ((12-now.getMonthValue())>=6){
                int j = 0;
                for (int i = now.getMonthValue(); i < now.getMonthValue()+6; i++){
                    months[j++] = i;
                }
            }else{
                int j = 0;
                for (int i = now.getMonthValue(); i < 13; i++){
                    months[j++] = i;
                }

                for (int i = 1; i <= (6+now.getMonthValue())-12;i++){
                    months[j++] = i;
                }
            }
        }

        monthbox = new JComboBox<>(months);
        monthbox.setBounds(210,200,75,25);
        monthLab.setBounds(230,170,50,25);
        monthbox.addActionListener(this);
        //monthbox.setEnabled(false);

        if (now.getDayOfMonth()+7>LocalDateTime.MAX.getDayOfMonth()){
            monthFlag = false;
            remDaysMonth = 14 + now.getDayOfMonth() - LocalDateTime.MAX.getDayOfMonth();
        }else{
            temp1max = LocalDateTime.MAX.getDayOfMonth() - (now.getDayOfMonth()+7);
        }

        LocalDate nextDate;
        if (now.getMonthValue()==12){
            nextDate = LocalDate.of(now.getYear()+1, 1,1);
        }else{
            nextDate = LocalDate.of(now.getYear(), now.getMonthValue()+1,1);
        }
        Integer[] days1 = new Integer[temp1max];
        Integer[] days2 = new Integer[nextDate.lengthOfMonth()-remDaysMonth];
        System.out.println(nextDate.lengthOfMonth()-remDaysMonth);
        if (monthFlag){
            int j = 0;
            for (int i = now.getDayOfMonth()+7; i < LocalDateTime.MAX.getDayOfMonth();i++){
                days1[j++] = i;
            }
            realDays1 = days1;
            System.out.println(Arrays.toString(realDays1));
            Arrays.toString(realDays1);
        }

        int j1 = 0;
        if (remDaysMonth==0){
            remDaysMonth++;
        }
        for (int i = remDaysMonth;i <= days2.length; i++){
            days2[j1++] = i;
        }
        realDays2 = days2;
        Arrays.toString(realDays2);
        System.out.println(Arrays.toString(realDays2));
        if (monthFlag==true){
            daysbox = new JComboBox<>(days1);
        }else{
            daysbox = new JComboBox<>(days2);
        }
        //daysbox.setEnabled(false);
        daysbox.setBounds(310,200,75,25);
        dayLab.setBounds(330,170,50,25);
        //daysbox.addActionListener(this);

        if (data[1][4]=="2"){
            Integer[] possTrains = {1,2};
            trainNumBox = new JComboBox<>(possTrains);

        }else{
            Integer[] possTrains = {3,4};
            trainNumBox = new JComboBox<>(possTrains);
        }
        trainNumBox.setBounds(385,300,75,25);
        trainLab.setBounds(410,270,75,25);

        String[] roomTypes = {"First Class","Second Class","Business Class","Economy Class"};
        roomTypeBox = new JComboBox<>(roomTypes);
        roomTypeBox.setBounds(15,300,125,25);
        classLab.setBounds(65,270,50,25);
        roomTypeBox.addActionListener(this);

        Integer rooms[] = new Integer[20];
        for (int i = 0; i < 20; i++){
            rooms[i] = i+1;
        }
        roomBox = new JComboBox<>(rooms);
        roomBox.setBounds(265,300,75,25);
        roomLab.setBounds(285,270,50,25);

        String[] pri = {"1750","1000","1250","1500"};
        price = new JComboBox(pri);
        price.setBounds(170,300,75,25);
        price.addActionListener(this);
        priceLab.setBounds(190,270,75,25);
        //price.setEnabled(false);

        String[] yesNo = {"Yes","No"};
        payBox = new JComboBox<>(yesNo);
        payBox.setBounds(230,400,70,25);
        payBoxLab.setBounds(210,370,150,25);

        go.setBounds(410,475,75,25);
        go.addActionListener(this);

        back.setBounds(0,0,75,25);
        back.addActionListener(this);


        bookFrame = new JFrame("Search Results");
        //bookFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bookFrame.setSize(550,600);
        bookFrame.setLocation(440,50);
        bookFrame.setLayout(null);
        bookFrame.setVisible(true);
        bookFrame.getContentPane().setBackground(Color.decode("#818589"));
        bookFrame.setResizable(false);

        bookFrame.add(daysbox);
        bookFrame.add(monthbox);
        bookFrame.add(yearbox);
        bookFrame.add(go);
        bookFrame.add(roomTypeBox);
        bookFrame.add(roomBox);
        bookFrame.add(trainNumBox);
        bookFrame.add(payBox);
        bookFrame.add(payLab);
        bookFrame.add(sp);
        bookFrame.add(back);
        bookFrame.add(yearLab);
        bookFrame.add(monthLab);
        bookFrame.add(dayLab);
        bookFrame.add(classLab);
        bookFrame.add(price);
        bookFrame.add(trainLab);
        bookFrame.add(roomLab);
        bookFrame.add(payBoxLab);
        bookFrame.add(priceLab);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String temp2 = (String) yearbox.getSelectedItem();
        if (e.getSource()==monthbox){
            daysbox.removeAllItems();
            int temp = (int) monthbox.getSelectedItem();
            if (temp==now.getMonthValue() && monthFlag){
                for (int i = 0; i < realDays1.length;i++){
                    daysbox.addItem(realDays1[i]);
                }
            }else if(temp==now.getMonthValue()+1){
                for (int i = 0; i < realDays1.length;i++){
                    daysbox.addItem(realDays2[i]);
                }
            } else if (Integer.parseInt(temp2)==now.getYear()) {
                LocalDate date = LocalDate.of(now.getYear(), (int)monthbox.getSelectedItem(), 1);
                for (int i = 1; i < date.lengthOfMonth();i++){
                    daysbox.addItem(i);
                }
            }else{
                LocalDate date = LocalDate.of(now.getYear()+1,(int)monthbox.getSelectedItem(),1);
                for (int i = 1; i < date.lengthOfMonth();i++){
                    daysbox.addItem(i);
                }
            }
        }

        if (e.getSource()==go){
            String year = (String) yearbox.getSelectedItem();
            int month = (int) monthbox.getSelectedItem();
            int day = (int) monthbox.getSelectedItem();
            int yearInt = Integer.parseInt(year);
            int endMonth = 0,  endYear = 0, endDay = 0;
            String startDate = year + "-" + month + "-" + day;
            String endDate;
            int check1 = 0, check2 = 0;
            int trNum = 0, trIndex = 0;
            int payBol = 0;
            String tempPay = (String) payBox.getSelectedItem();
            System.out.println(Integer.parseInt(realData[1][4]) + " " + (int)trainNumBox.getSelectedItem());
            if (tempPay.equals("Yes")){
                payBol = 1;
            }else{
                payBol = 0;
            }
            if (1 == (int)trainNumBox.getSelectedItem() || 3 == (int)trainNumBox.getSelectedItem()){
                trNum = Integer.parseInt(realData[0][4]);
                trIndex = 0;
            }else{
                trNum = Integer.parseInt(realData[1][4]);
                trIndex = 1;
            }
            if (Integer.parseInt(realData[0][3].substring(0,2))-Integer.parseInt(realData[0][1].substring(0,2))<0){
                if (LocalDateTime.MAX.getDayOfMonth()<day+1){
                    if (month+1>12){
                        endYear = yearInt+1;
                        endMonth = 1;
                        endDay = 1;
                    }else{
                        endYear = yearInt;
                        endDay = 1;
                        endMonth++;
                    }
                }else{
                    endYear = yearInt;
                    endDay = day+1;
                    endMonth = month;
                }
            }else {
                endYear = yearInt;
                endDay = day;
                endMonth = month;
            }
            endDate = endYear + "-" + endMonth + "-" + endDay;
            startDate+= " " + realData[trIndex][1];
            System.out.println(trIndex);
            endDate+= " " + realData[trIndex][3];
            System.out.println(startDate);
            System.out.println(endDate);
            int seat = (int) roomBox.getSelectedItem();
            String trId = "ML"+mlNum+"T"+trNum+"R"+seat;
            bk.makeBooking2(startDate,endDate,payBol,trId,uname);
        }

        if (e.getSource()==roomTypeBox){
            roomBox.removeAllItems();
            String type = (String) roomTypeBox.getSelectedItem();
            if (type.equals("First Class")){
                System.out.println("A");
                price.setSelectedItem(price.getItemAt(roomTypeBox.getSelectedIndex()));
                for (int i = 0; i < 20; i++){
                    Integer x = i + 1;
                    roomBox.addItem(x);
                }
            }else if(type.equals("Second Class")){
                System.out.println("B");
                price.setSelectedItem(price.getItemAt(roomTypeBox.getSelectedIndex()));
                for (int i = 20; i < 40; i++){
                    Integer x = i + 1;
                    roomBox.addItem(x);
                }
            } else if (type.equals("Business Class")) {
                System.out.println("C");
                price.setSelectedItem(price.getItemAt(roomTypeBox.getSelectedIndex()));
                for (int i = 40; i < 60; i++){
                    Integer x = i + 1;
                    roomBox.addItem(x);
                }
            }else if(type.equals("Economy Class")){
                System.out.println("D");
                price.setSelectedItem(price.getItemAt(roomTypeBox.getSelectedIndex()));
                for (int i = 60; i < 80; i++){
                    Integer x = i + 1;
                    roomBox.addItem(x);
                }
            }
        }

        if (e.getSource()==back){
            bookFrame.dispose();
        }

        if (e.getSource()==price){
            roomBox.removeAllItems();
            String type = (String) price.getSelectedItem();
            if (type.equals("1750")){
                System.out.println("l");
                roomTypeBox.setSelectedItem(roomTypeBox.getItemAt(price.getSelectedIndex()));
                for (int i = 0; i < 20; i++){
                    roomBox.addItem(i+1);
                }
            }else if(type.equals("1000")){
                System.out.println("m");
                roomTypeBox.setSelectedItem(roomTypeBox.getItemAt(price.getSelectedIndex()));
                for (int i = 20; i < 40; i++){
                    roomBox.addItem(i+1);
                }
            } else if (type.equals("1250")) {
                System.out.println("n");
                roomTypeBox.setSelectedItem(roomTypeBox.getItemAt(price.getSelectedIndex()));
                for (int i = 40; i < 60; i++){
                    roomBox.addItem(i+1);
                }
            }else if(type.equals("1500")){
                System.out.println("o");
                roomTypeBox.setSelectedItem(roomTypeBox.getItemAt(price.getSelectedIndex()));
                for (int i = 60; i < 80; i++){
                    roomBox.addItem(i+1);
                }
            }
        }
    }
}
