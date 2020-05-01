package com.example.tavanyab.utiles;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 5/8/16.
 */
public class FileUtils {
    private static final char[] CSV_FILE_FIELD_DELIMITER = {',', ';'};

    Writer writer;
    String header = new String();

    public FileUtils(OutputStream outputStream) {
        try {
            writer = new OutputStreamWriter(outputStream, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public FileUtils() {
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
        try {
            writer.write(this.header);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public void close() throws IOException {
        writer.close();
    }

    public static List<String[]> readCSVFile(InputStream inputStream) throws IOException {
        List<String[]> finalList = new ArrayList<String[]>();
        Reader reader = new InputStreamReader(inputStream);

        CsvReader csvReader = new CsvReader(reader);

        csvReader.setFieldDelimiter(CSV_FILE_FIELD_DELIMITER);

        csvReader.setBlockDelimiter('\n');

        String[] line;
        List<String[]> list = new ArrayList<String[]>();
        while ((line = csvReader.readLine()) != null) {
            list.add(line);
        }
        for (String[] currentLine : list) {
            currentLine[currentLine.length - 1] = currentLine[currentLine.length - 1].replaceAll("\r", "");
            currentLine[currentLine.length - 1].replaceAll("\r", "");
            finalList.add(currentLine);
        }

        reader.close();
        csvReader.close();
        return finalList;
    }

    public static String readTextFile(InputStream inputStream) throws IOException {
        String content = "";
        byte[] buf = new byte[10000];
        int len = 0;
        while ((len = inputStream.read(buf)) > 0) {
            content += new String(buf);
        }
        return content;
    }

}
