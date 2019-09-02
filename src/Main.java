import java.io.*;
import java.util.*;

public class Main {

    //CSV File Format Configuration
    private static final int NUM_OF_COL = 6;
    private static final int DATE_ROW = 6;
    private static final int HEADER = 10;
    private static final int FOOTER = 12;

    private static List<String> header;
    private static List<List<String>> output;

    private static final String RAW_DATA_PATH = "raw_data";
    private static final String OUTPUT_PATH = "output";


    public static void main(String[] args) {
        buildHeader();

        try {
            File dir = new File(RAW_DATA_PATH);
            File[] files = dir.listFiles();
            String[] fileNames = dir.list();

            for (int i = 0; i < files.length; i++) {
                String inputFilePath = RAW_DATA_PATH + "/" + fileNames[i];

                //collect data
                List<ArrayList<String>> data = readData(inputFilePath);

                //initialize output
                output = new ArrayList<>();
                output.add(header);

                //re-format raw data into required output format
                reformatData(data, output, fileNames[i].split("\\.")[0]);

//                //export output data
//                String outputFilePath = OUTPUT_PATH + "/" + fileNames[i];
//                saveData(output, outputFilePath);

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static void buildHeader() {
        header = new ArrayList<String>();
        header.add("fund_name");
        header.add("ticker");
        header.add("date");
        header.add("security_name");
        header.add("CUSIP");
        header.add("price");
        header.add("quantity");
        header.add("clean_mkt_value");
        header.add("dirty_mkt_value");
        header.add("ptf_weight");
    }

    private static void reformatData(List<ArrayList<String>> myData, List<List<String>> output, String ticker) throws Exception {
//        String ticker = TICKER;
        String fund = myData.get(0).get(0);
        ArrayList<String> dates = myData.get(DATE_ROW);


        List<ArrayList<String>> body = myData.subList(HEADER, myData.size() - FOOTER);


        for (int i = 0; i < body.size(); i++) {
            List<String> row = body.get(i);
            String securityName = row.get(0);

            for (int j = 1; j < row.size(); j += NUM_OF_COL) {

                List<String> rowSection = new ArrayList<>();
                rowSection.addAll(row.subList(j, j + NUM_OF_COL));
                String date = dates.get(j);
                rowSection.add(0, securityName);
                rowSection.add(0, date);
                rowSection.add(0, ticker);
                rowSection.add(0, fund);
                output.add(rowSection);
            }
        }

    }

    private static ArrayList<String> getDates(ArrayList<String> datesRow) throws Exception {
//        1,7,13,20
        ArrayList<String> dates = new ArrayList<>();
        for (int i = 1; i < datesRow.size(); i += NUM_OF_COL) {
            dates.add(datesRow.get(i));
        }
        return dates;
    }

    private static List<ArrayList<String>> readData(String filePath) throws Exception {
        List<ArrayList<String>> collection = new ArrayList<ArrayList<String>>();
        File fileTemplate = new File(filePath);
        FileInputStream fis = new FileInputStream(fileTemplate);
        Reader fr = new InputStreamReader(fis, "UTF-8");

        List<String> values = CSVHelper.parseLine(fr);
        while (values != null) {
            collection.add(new ArrayList<String>(values));
            values = CSVHelper.parseLine(fr);
        }
        fis.close();
        fr.close();
        return collection;
    }

    private static void saveData(List<List<String>> myData, String filePath) throws Exception {
        File csvFile = new File(filePath);
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
