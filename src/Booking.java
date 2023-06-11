import javax.swing.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Booking {
    Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Booking b = new Booking();
        b.createConnection();
    }

    void createConnection(){
        MyTime t1 = new MyTime();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Blasted1@3894");
            Statement stmt = con.createStatement();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = ""+now.getYear()+"-"+now.getMonthValue()+"-"+now.getDayOfMonth()+" "+now.getHour()+":"+now.getMinute()+":"+now.getSecond();
            String q6 = "DELETE FROM `mydb`.`bookings` WHERE `bookings`.`endTime` <= '" + date + "'";
            stmt.execute(q6);
            //System.out.println(q6);
            System.out.println("a");
        } catch (SQLException e) {
            System.out.println("Error");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Class");
            throw new RuntimeException(e);
        }
    }

    String getTrainID(){
        boolean flag1 = false, flag2 = true, flag3 = true;
        String id = "";
        while (flag1 || flag2 || flag3){
            id = "";
            flag1 = false;
            flag2 = true;
            flag3 = true;
            System.out.println("Enter 1 FOR ML1, 2 FOR ML2, 3 FOR ML3, 4 FOR ML4 ");
            int ch1 =  sc.nextInt();
            switch (ch1){
                case 1:
                    id+="ML1";
                    break;
                case 2:
                    id+="ML2";
                    break;
                case 3:
                    id+="ML3";
                    break;
                case 4:
                    id+="ML4";
                    break;
                default:
                    System.out.println("Error in ML");
                    flag1 = true;
                    break;
            }
            System.out.println("Enter 1 for Train 1, 2 for Train 2");
            int ch2 = sc.nextInt();
            if (ch2>0 && ch2<5){
                id += "T" + ch2;
                flag2 = false;
            }
            System.out.println("Enter Room ID, 1-20 are First Class, 21-40 are Second Class, 41-60 are Business Class, 61-80 are Economy Class");
            int ch3 = sc.nextInt();
            if (ch3>0 && ch3<81){
                id += "R" + ch3;
                flag3 = false;
            }
            System.out.println(id);
        }
        return id;
    }

    void makeBooking2(String sdate, String edate, int payBol, String trID,String name){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Blasted1@3894");
            Statement stmt = con.createStatement();
            String  q = "SELECT `bookings`.`idbookings` FROM `mydb`.`bookings` ORDER BY `bookings`.`idbookings` DESC LIMIT 1  ";
            ResultSet rs = stmt.executeQuery(q);
            rs.next();
            System.out.println(rs.getInt("idbookings"));
            int bkID = rs.getInt("idbookings") + 1;
                if (bkID==1){
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String x = "";
                    int hr = now.getHour();
                    String q4 = "INSERT INTO `mydb`.`bookings` VALUES ("+bkID+",'"+trID+"','"+sdate+"'"+",'"+edate+"',"+payBol+",'"+now.getYear()+"-"+now.getMonthValue()+"-"+now.getDayOfMonth()+" "+hr+":"+now.getMinute()+":"+now.getSecond()+"','"+name+"')";
                    stmt.execute(q4);
                    JOptionPane.showMessageDialog(null,"Booking Successful");
                }else{
                    //rs2.next();
                    //String temp1 = rs2.getString("trainID");
                    //System.out.println(temp1);
                    System.out.println("A");
                    //rs3.next();
                    //String temp2 = rs3.getString("trainID");
                    //System.out.println(temp2);
                    System.out.println("B");
                    //String q3 = "SELECT `bookings`.`trainID` FROM `mydb`.`bookings` WHERE '"+sdate+"' BETWEEN `bookings`.`startTime` AND `bookings`.`endTime` AND `bookings`.`trainID` LIKE '" + trID+"'";
                    String q33 = "SELECT `bookings`.`trainID` FROM `mydb`.`bookings` WHERE '"+sdate+"' >= `bookings`.`startTime` AND '"+sdate+"'<`bookings`.`endTime` AND `bookings`.`trainID` LIKE '" + trID+"'";
                    ResultSet rs2 = stmt.executeQuery(q33);
                    boolean temp = rs2.next();
                    //String q5 = "SELECT `bookings`.`trainID` FROM `mydb`.`bookings` WHERE '"+edate+"' BETWEEN `bookings`.`startTime` AND `bookings`.`endTime` AND `bookings`.`trainID` LIKE '" + trID+"'";
                    String q55 = "SELECT `bookings`.`trainID` FROM `mydb`.`bookings` WHERE '"+edate+"' >= `bookings`.`startTime` AND '"+edate+"'<`bookings`.`endTime` AND `bookings`.`trainID` LIKE '" + trID+"'";
                    ResultSet rs3 = stmt.executeQuery(q55);
                    boolean temp1 = rs3.next();
                    String q66 = "SELECT `bookings`.`trainID` FROM `mydb`.`bookings` WHERE `bookings`.`startTime` >= '"+sdate+"' AND `bookings`.`startTime` < '"+edate+"' AND `bookings`.`trainID` LIKE '"+ trID+"'";
                    ResultSet rs4 = stmt.executeQuery(q66);
                    boolean temp2 = rs4.next();
                    //System.out.println(rs4.getString("trainID"));
                    String q77 = "SELECT `bookings`.`trainID` FROM `mydb`.`bookings` WHERE `bookings`.`endTime` >= '"+sdate+"' AND `bookings`.`endTime` < '"+edate+"' AND `bookings`.`trainID` LIKE '"+ trID+"'";
                    ResultSet rs5 = stmt.executeQuery(q77);
                    boolean temp3 = rs5.next();
                    String q88 = "SELECT `bookings`.`trainID` FROM `mydb`.`bookings` WHERE `bookings`.`endTime` = '"+sdate+"' AND `bookings`.`endTime` = '"+edate+"' AND `bookings`.`trainID` LIKE '"+ trID+"'";
                    ResultSet rs6 = stmt.executeQuery(q88);
                    boolean temp4 = rs6.next();
                    //System.out.println(rs5.getString("trainID"));
                    if (temp==false && temp1==false && temp2==false && temp3==false && temp4==false){
                        System.out.println("C");
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String x = "";
                        int hr = now.getHour();
                        String q4 = "INSERT INTO `mydb`.`bookings` VALUES ("+bkID+",'"+trID+"','"+sdate+"'"+",'"+edate+"',"+payBol+",'"+now.getYear()+"-"+now.getMonthValue()+"-"+now.getDayOfMonth()+" "+hr+":"+now.getMinute()+":"+now.getSecond()+"','"+name+"')";
                        stmt.execute(q4);
                        JOptionPane.showMessageDialog(null,"Booking Successful");
                    }else{
                        System.out.println();
                        JOptionPane.showMessageDialog(null,"Seat is not available, Please Try Again");
                    }
                    rs2.close();
                    rs3.close();
                }
                //String q2 = "`mydb`.`bookings` VALUES (\"+bkID+\",'\"+id+\"','2023-04-25 06:13:05'\"+\",'2023-04-25 10:13:05',\"+\"1\"+\",'\"+now.getYear()+\"-\"+now.getMonthValue()+\"-\"+now.getDayOfMonth()+\" \"+hr+\":\"+now.getMinute()+\":\"+now.getSecond()+\"')\"; ";
        } catch (Exception e) {

        }

    }

    boolean checkBooking(String username){
        boolean check = false;
        try {
            String q1 = "SELECT `bookings`.`userID` FROM `mydb`.`bookings` WHERE `bookings`.`userID` = '"+username+"'";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Blasted1@3894");
            Statement stmt = con.createStatement();
            ResultSet rs1 = stmt.executeQuery(q1);
            return rs1.next();
        }catch (Exception e){

        }
        return false;
    }

    void CancelBooking(int bkID, String username){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Blasted1@3894");
            Statement stmt = con.createStatement();
            String del = "DELETE FROM `mydb`.`bookings` WHERE `bookings`.`userID` = '"+username+"' AND `bookings`.`idbookings` = "+bkID+"";
            stmt.execute(del);
            JOptionPane.showMessageDialog(null,"Cancelled Successfully, Please Exit Cancel window to see changes");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"SQL Error");
        }
    }

    void fillCanTable(ArrayList<String> tempList, String username){
        try {
            int i = 0;
            String q1 = "SELECT `bookings`.`idbookings`,`bookings`.`startTime` FROM `mydb`.`bookings` WHERE `bookings`.`userID` = '"+username+"'";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","Blasted1@3894");
            Statement stmt = con.createStatement();
            ResultSet rs1 = stmt.executeQuery(q1);
            while (rs1.next()){
                System.out.println(rs1.getInt("idbookings"));
                System.out.println(rs1.getString("startTime"));
                tempList.add((rs1.getInt("idbookings")+","+rs1.getString("startTime")));
                i++;
            }
        }catch (Exception e){

        }
    }

    void makeBooking(){
        /*
        String  q = "SELECT `bookings`.`idbookings` FROM `mydb`.`bookings` ORDER BY `bookings`.`idbookings` DESC LIMIT 1  ";
            ResultSet rs = stmt.executeQuery(q);
            rs.next();
            System.out.println(rs.getInt("idbookings"));
            int bkID = rs.getInt("idbookings") + 1;
         boolean flag = true;
            String id = "";
            while (flag) {
                id = getTrainID();
                String q2 = "SELECT `trainlines`.`CurrentBookerID` FROM `mydb`.`trainlines` WHERE `trainlines`.`RoomID` LIKE \""+id+"\"";
                ResultSet rs2 = stmt.executeQuery(q2);
                while (rs2.next()){
                    System.out.println("A");
                    System.out.println(rs2.getString("CurrentBookerID"));
                    String r = rs2.getString("CurrentBookerID");
                    System.out.println(r);
                    if (r == null || r.equals("null")){
                        flag = false;
                        System.out.println(flag);
                    }
                }
                rs2.close();
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String x = "";
            int hr = 0;
            if (now.getHour()>12){
                x = "PM";
            }else{
                x = "AM";
            }
            hr = now.getHour()%12;
            String q4 = "INSERT INTO `mydb`.`bookings` (`idbookings`," +
                    "`trainID`," +
                    "`startTime`," +
                    "`endTime`," +
                    "`payBol`," +
                    "`bookTime`)" +
                    "VALUES" +
                    "(`" + bkID +
                    "`,`" + id +
                    "`,`20230425 06:13:05 PM`" +
                    ",`20230425 10:13:05 PM" +
                    ",`" + 1 +
                    "`,`"+now.getYear()+now.getMonthValue()+now.getDayOfMonth()+" "+hr+":"+now.getMinute()+":"+now.getSecond()+" "+x+"`);";
            //q4 = "INSERT INTO `mydb`.`bookings` VALUES ("+bkID+",'"+id+"','2023-04-25 06:13:05'"+",'2023-04-25 10:13:05',"+"1"+",'"+now.getYear()+"-"+now.getMonthValue()+"-"+now.getDayOfMonth()+" "+hr+":"+now.getMinute()+":"+now.getSecond()+"')";
            //stmt.execute(q4);
            //System.out.println("a");
            //String q3 = "UPDATE `mydb`.`trainlines` SET `CurrentBookerID` = " + bkID  + " WHERE `RoomID` = '" + id + "'";
            //stmt.execute(q3);
         */
    }

    void removeBooking(){
        /*
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = ""+now.getYear()+"-"+now.getMonthValue()+"-"+now.getDayOfMonth()+" "+now.getHour()+":"+now.getMinute()+":"+now.getSecond();
            //String q6 = "DELETE FROM `mydb`.`bookings` WHERE `bookings`.`endTime` <= '" + date + "'";
            id = "ML1T1R23";
            //stmt.execute(q6);
            //System.out.println(q6);
            System.out.println("a");
            String q5 = "SELECT `bookings`.`trainID` FROM `mydb`.`bookings` WHERE `bookings`.`endTime` <= '" + date + "'";
            ResultSet r4 = stmt.executeQuery(q5);
            while (r4.next()){
                String tempID = r4.getString("trainID");
                System.out.println(tempID);
                String q7 = "UPDATE `mydb`.`trainlines` SET `CurrentBookerID` = " + null + " WHERE `RoomID` = '" + tempID + "'";
                stmt.execute(q7);
            }
         */
    }
}

