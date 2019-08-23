package com.company;

import java.io.*;
import java.util.*;
public class Main {
    private static final int NUM_OF_COL = 6;
    private static final int DATE_ROW= 6;
    private static final int HEADER= 10;
    private static final int FOOTER= 12;

    private static final String TICKER = "BFCCX";
    private static final String FILE_PATH = "BFCCX_raw.csv";

    public static void main(String[] args) {
        List<ArrayList<String>> data = null;
        try {
            data = readData();
            List<List<String>> output = reformatData(data);
            saveData(output);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<List<String>> reformatData(List<ArrayList<String>> myData) throws Exception {
        String ticker = TICKER;
        String fund = myData.get(0).get(0);
        ArrayList<String> dates = myData.get(DATE_ROW);
//        for(int i=1; i<dates.size(); i+=NUM_OF_COL) {
//            System.out.println(i+": "+ dates.get(i));
//        }

        List<ArrayList<String>> body = myData.subList(HEADER, myData.size()-FOOTER);
        List<List<String>> output = new ArrayList<>();


        for (int i=0; i<body.size(); i++) {
            List<String> row = body.get(i);
            String securityName = row.get(0);

            for (int j = 1; j < row.size(); j += NUM_OF_COL) {

                List<String> rowSection = new ArrayList<>();
                rowSection.addAll(row.subList(j, j + NUM_OF_COL));
                String date = dates.get(j);
                rowSection.add(0, ticker);
                rowSection.add(0, date);
                rowSection.add(0, securityName);
                rowSection.add( 0, fund);
                output.add(rowSection);
            }
        }
//        System.out.println("size: "+ output.size());
//        System.out.println("#of content rows: "+ body.size());
//        System.out.println("#of date rows: "+dates.size());
//          System.out.println("#output.size should be: "+dates.size()*body.size());



        return output;
    }
    public static ArrayList<String> getDates(ArrayList<String> datesRow) throws Exception {
//        1,7,13,20
        ArrayList<String> dates = new ArrayList<>();
        for(int i=1; i<datesRow.size(); i+=NUM_OF_COL) {
            dates.add(datesRow.get(i));
        }
        return dates;
    }


    public static List<ArrayList<String>> readData() throws Exception {
        List<ArrayList<String>> collection = new ArrayList<ArrayList<String>>();
        File fileTemplate = new File(FILE_PATH);
        FileInputStream fis = new FileInputStream(fileTemplate);
        Reader fr = new InputStreamReader(fis, "UTF-8");

        List<String> values = CSVHelper.parseLine(fr);
        while (values!=null) {
            collection.add( new ArrayList<String>(values) );
            values = CSVHelper.parseLine(fr);
        }
        fis.close();
        fr.close();
        return collection;
    }

    public static void saveData(List<List<String>> myData) throws Exception {
        File csvFile = new File("output.csv");
        FileOutputStream fos = new FileOutputStream(csvFile);
        Writer fw = new OutputStreamWriter(fos, "UTF-8");
        for (List<String> oneDatum : myData) {
            List<String> rowValues = oneDatum;
            CSVHelper.writeLine(fw, rowValues);
        }
        fw.flush();
        fw.close();
    }

}
