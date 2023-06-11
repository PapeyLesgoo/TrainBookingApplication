import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainLines {
    ArrayList<String>[] list = new ArrayList[4];
    ArrayList<String>[] times = new ArrayList[16];

    public TrainLines() {
        for (int i = 0; i < 4; i++){
            list[i] = new ArrayList<>();
        }
        for (int i = 0 ; i < 16; i++){
            times[i] = new ArrayList<>();
        }
        SetRoutes();
        SetTimes();
        PrintAll();
    }

    public void Search(ArrayList<String> tempList, String ser){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < list[i].size(); j++){
                System.out.println(list[i].get(j) + " = " + ser);
                if (list[i].get(j).toLowerCase().contains(ser)||list[i].get(j).equalsIgnoreCase(ser)||list[i].get(j).contains(ser)){
                    System.out.println(list[i].get(j));
                    System.out.println("Route"+(i+1));
                    tempList.add("Route " + (i+1) + " Stop " + (j+1) + "," + list[i].get(j));
                    System.out.println(tempList.get(i));
                }
            }
        }
    }


    public int RetSearch(String ser){
        int temp = 0;

        return temp;
    }

    public stopAndDest startAndDest(String start, String dest){
        stopAndDest result = new stopAndDest();
        int start_index = 0, end_index = 0, mlNum = 0, tNum = 0;
        String resStart = null, resDest = null;
        for (int i = 0; i < 4; i++){
            if (list[i].contains(start) && list[i].contains(dest)){
                for (int j = 0; j < list[i].size(); j++){
                    if (list[i].get(j).equalsIgnoreCase(start)){
                        System.out.println(list[i].get(j));
                        mlNum = i;
                        start_index = j;
                        resStart = list[i].get(j);
                        System.out.println(j);
                        result.start = resStart;
                        result.start_index = start_index;
                        result.mlNum = mlNum;
                        break;
                    }
                }
            }
        }
        if (resStart == null){
            System.out.println("Error in start");
            JOptionPane.showMessageDialog(null,"Start Stop not found");
            return null;
        }

        for (int j = 0; j < list[mlNum].size(); j++){
            System.out.println(list[mlNum].get(j));
            if (list[mlNum].get(j).equalsIgnoreCase(dest)){
                System.out.println(list[mlNum].get(j));
                end_index = j;
                resDest = list[mlNum].get(j);
                System.out.println(j);
                result.end_index = end_index;
                result.dest = resDest;
                break;
            }
        }

        if (resDest == null){
            JOptionPane.showMessageDialog(null,"Destination not found");
            System.out.println("Error in destination");
            return null;
        }

        return result;
    }

    public void TrainTimes(stopAndDest result, String[][] data){
        int tNum = 0;
        tNum = result.mlNum*4;
        if (result.start_index<result.end_index){
            for (int i = tNum; i < tNum+2;i++){
                String temp1 = null, temp2 = null;
                int hr1 = Integer.parseInt(times[i].get(result.start_index).substring(0,2));
                int hr2 = Integer.parseInt(times[i].get(result.end_index).substring(0,2));
                boolean flag1 = true, flag2 = true;
                if (hr1>24){
                    flag1 = false;
                    hr1 = hr1%24;
                    if (hr1<10){
                        //times[i].get(start_index).replaceFirst(times[i].get(start_index).substring(0,2),"0"+hr1);
                        temp1 = "Day+1 0" + hr1 + times[i].get(result.start_index).substring(2);
                        temp1 = "Day+1 " + hr1 + times[i].get(result.start_index).substring(2);
                    }
                }

                if (hr2>24){
                    flag2 = false;
                    hr2 = hr2%24;
                    if (hr2<10){
                        //times[i].get(start_index).replaceFirst(times[i].get(start_index).substring(0,2),"0"+hr1);
                        temp2 = hr1 + times[i].get(result.start_index).substring(2);
                    }else{
                        temp2 = hr1 + times[i].get(result.start_index).substring(2);
                    }
                }
                System.out.println("------------------------");
                data[i%4][0] = result.start;
                System.out.println("Start Spot : " + result.start);
                if (flag1 == false){
                    data[i%4][1] = temp1;
                    System.out.println("Departure Time : " + temp1);
                }
                else {
                    data[i%4][1] = times[i].get(result.start_index);
                    System.out.println("Departure Time : " + times[i].get(result.start_index));
                }
                System.out.println("Destination Spot : " + result.dest);
                data[i%4][2] = result.dest;
                if (flag2 == false){
                    data[i%4][3] = temp2;
                    System.out.println("Departure Time : " + temp2);
                }
                else {
                    data[i%4][3] = times[i].get(result.end_index);
                    System.out.println("Departure Time : " + times[i].get(result.end_index));
                }
                data[i%4][4] = ""+((i+1)%4);
            }
            data[0][4] = "1";
            data[1][4] = "2";
        }else if(result.start_index>result.end_index){
            int j = 0;
            result.start_index = times[result.mlNum].size() - result.start_index - 1;
            result.end_index = times[result.mlNum].size() - result.end_index - 1;
            tNum+=2;
            for (int i = tNum; i < tNum+2;i++){
                String temp1 = null, temp2 = null;
                int hr1 = Integer.parseInt(times[i].get(result.start_index).substring(0,2));
                int hr2 = Integer.parseInt(times[i].get(result.end_index).substring(0,2));
                boolean flag1 = true, flag2 = true;
                if (hr1>24){
                    flag1 = false;
                    hr1 = hr1%24;
                    if (hr1<10){
                        //times[i].get(start_index).replaceFirst(times[i].get(start_index).substring(0,2),"0"+hr1);
                        temp1 = hr1 + times[i].get(result.start_index).substring(2);
                    }
                    else {
                        temp2 = hr2 + times[i].get(result.end_index).substring(2);
                    }
                }

                if (hr2>24){
                    flag2 = false;
                    hr2 = hr2%24;
                    if (hr2<10){
                        //times[i].get(start_index).replaceFirst(times[i].get(start_index).substring(0,2),"0"+hr1);
                        temp2 = hr2 + times[i].get(result.end_index).substring(2);
                    }else{
                        temp2 = hr2 + times[i].get(result.end_index).substring(2);
                    }
                }
                System.out.println("------------------------");
                System.out.println("Start Spot : " + result.start);
                data[j][0] = result.start;
                if (flag1 == false){
                    data[j][1] = temp1;
                    System.out.println("Departure Time : " + temp1);
                }
                else {
                    data[j][1] = times[i].get(result.start_index);
                    System.out.println("Departure Time : " + times[i].get(result.start_index));
                }
                data[j][2] = result.dest;
                System.out.println("Destination Spot : " + result.dest);
                if (flag2 == false){
                    data[j][3] = temp2;
                    System.out.println("Departure Time : " + temp2);
                }
                else {
                    data[j][3] = times[i].get(result.end_index);
                    System.out.println("Departure Time : " + times[i].get(result.end_index));
                }
                data[j][4] = "" + ((j+1)+2);
                j++;
            }
            data[0][4] = "3";
            data[1][4] = "4";
        }else {
            System.out.println("Same Destination Error");
            JOptionPane.showMessageDialog(null,"Entered same stop and destination ");
        }
    }

    public void BookRide(){
        int start_index = 0, end_index = 0, mlNum = 0, tNum = 0;
        String resStart = null, resDest = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter starting spot ");
        String start = sc.nextLine();
        System.out.println("Enter destination ");
        String dest = sc.nextLine();
        for (int i = 0; i < 4; i++){
            if (list[i].contains(start) && list[i].contains(dest)){
                for (int j = 0; j < list[i].size(); j++){
                    if (list[i].get(j).equalsIgnoreCase(start)){
                        System.out.println(list[i].get(j));
                        mlNum = i;
                        start_index = j;
                        resStart = list[i].get(j);
                        System.out.println(j);
                        break;
                    }
                }
            }
        }
        if (resStart == null){
            System.out.println("Error in start");
            return;
        }

        for (int j = 0; j < list[mlNum].size(); j++){
            System.out.println(list[mlNum].get(j));
            if (list[mlNum].get(j).equalsIgnoreCase(dest)){
                System.out.println(list[mlNum].get(j));
                end_index = j;
                resDest = list[mlNum].get(j);
                System.out.println(j);
                break;
            }
        }

        if (resDest == null){
            System.out.println("Error in destination");
            return;
        }
        System.out.println(start_index);
        System.out.println(end_index);

        tNum = mlNum*4;
        if (start_index<end_index){
            for (int i = tNum; i < tNum+2;i++){
                String temp1 = null, temp2 = null;
                int hr1 = Integer.parseInt(times[i].get(start_index).substring(0,2));
                int hr2 = Integer.parseInt(times[i].get(end_index).substring(0,2));
                boolean flag1 = true, flag2 = true;
                if (hr1>24){
                    flag1 = false;
                    hr1 = hr1%24;
                    if (hr1<10){
                        //times[i].get(start_index).replaceFirst(times[i].get(start_index).substring(0,2),"0"+hr1);
                        temp1 = "Day+1 0" + hr1 + times[i].get(start_index).substring(2);
                        temp1 = "Day+1 " + hr1 + times[i].get(start_index).substring(2);
                    }
                }

                if (hr2>24){
                    flag2 = false;
                    hr2 = hr2%24;
                    if (hr2<10){
                        //times[i].get(start_index).replaceFirst(times[i].get(start_index).substring(0,2),"0"+hr1);
                        temp2 = "Day+1 0" + hr1 + times[i].get(start_index).substring(2);
                    }else{
                        temp2 = "Day+1 " + hr1 + times[i].get(start_index).substring(2);
                    }
                }
                System.out.println("------------------------");
                System.out.println("Start Spot : " + resStart);
                if (flag1 == false){
                    System.out.println("Departure Time : " + temp1);
                }
                else {
                    System.out.println("Departure Time : " + times[i].get(start_index));
                }
                System.out.println("Destination Spot : " + resDest);
                if (flag2 == false){
                    System.out.println("Departure Time : " + temp2);
                }
                else {
                    System.out.println("Departure Time : " + times[i].get(end_index));
                }
            }
        }else if(start_index>end_index){
            start_index = times[mlNum].size() - start_index - 1;
            end_index = times[mlNum].size() - end_index - 1;
            tNum+=2;
            for (int i = tNum; i < tNum+2;i++){
                String temp1 = null, temp2 = null;
                int hr1 = Integer.parseInt(times[i].get(start_index).substring(0,2));
                int hr2 = Integer.parseInt(times[i].get(end_index).substring(0,2));
                boolean flag1 = true, flag2 = true;
                if (hr1>24){
                    flag1 = false;
                    hr1 = hr1%24;
                    if (hr1<10){
                        //times[i].get(start_index).replaceFirst(times[i].get(start_index).substring(0,2),"0"+hr1);
                        temp1 = "Day+1 0" + hr1 + times[i].get(start_index).substring(2);
                    }
                    else {
                        temp2 = "Day+1 " + hr2 + times[i].get(end_index).substring(2);
                    }
                }

                if (hr2>24){
                    flag2 = false;
                    hr2 = hr2%24;
                    if (hr2<10){
                        //times[i].get(start_index).replaceFirst(times[i].get(start_index).substring(0,2),"0"+hr1);
                        temp2 = "Day+1 0" + hr2 + times[i].get(end_index).substring(2);
                    }else{
                        temp2 = "Day+1 " + hr2 + times[i].get(end_index).substring(2);
                    }
                }
                System.out.println("------------------------");
                System.out.println("Start Spot : " + resStart);
                if (flag1 == false){
                    System.out.println("Departure Time : " + temp1);
                }
                else {
                    System.out.println("Departure Time : " + times[i].get(start_index));
                }
                System.out.println("Destination Spot : " + resDest);
                if (flag2 == false){
                    System.out.println("Departure Time : " + temp2);
                }
                else {
                    System.out.println("Departure Time : " + times[i].get(end_index));
                }
            }
        }else {
            System.out.println("Same Destination Error");
        }
    }

    public void PrintAll(){
        for (int i = 0; i < 4; i++){
            for (int j = 0 ; j < list[i].size(); j++){
                System.out.print(list[i].get(j) + " -> ");
            }
            System.out.println("End");
        }
    }

    public void SetRoutes(){
        int count = 0;
        File file = new File("Trainlines.txt");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine())!=null){
                String arr[] = line.split(", ");
                for (int i = 0; i < arr.length;i++){
                    list[count].add(arr[i]);
                }
                count++;
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void SetTimes(){
        int count = 0;
        File file = new File("Traintimes.txt");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine())!=null){
                String arr[] = line.split(", ");
                for (int i = 0; i < arr.length;i++){
                    times[count].add(arr[i]);
                    System.out.println(times[count].get(i));
                }
                count++;
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
