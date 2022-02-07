package com.company;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        BufferedReader reader = null;
        PrintWriter writer = null;

        String floatSubRegex = "[+-]?[0-9]*\\.?[0-9]+(\\s|\\z)";

        System.out.println("Enter number of lines per record: ");
        String s = scanner.nextLine();
        int linesPerRecord = Integer.parseInt(s);
        System.out.println("Enter variable names, separated by spaces: ");
        s = scanner.nextLine().trim();
        String[] varNames = s.split("\\s+");
        int numVar = varNames.length;

        try{
            reader = new BufferedReader(new FileReader("BetaLog.txt"));
            writer = new PrintWriter(new BufferedWriter(new FileWriter("BetaLog.csv")));

            StringBuilder headerBuilder = new StringBuilder();
            headerBuilder.append("time");
            for (int i=0; i<numVar; i++) {
                headerBuilder.append(',');
                headerBuilder.append(varNames[i]);
            }
            writer.println(headerBuilder.toString());

            while (true){
                StringBuilder inputRecordBuilder = new StringBuilder();

                boolean finished = false;

                for (int i=0; i<linesPerRecord; i++){
                    if ((s = reader.readLine()) == null) {
                        finished = true;
                        break;
                    }
                    inputRecordBuilder.append(s + " ");
                }
                if (finished) break;
                String inputRecord = inputRecordBuilder.toString();

                Pattern pattern = Pattern.compile(floatSubRegex);
                Matcher matcher = pattern.matcher(inputRecord);
                if (!matcher.find()) {
                    break;
                }

                float time = Float.parseFloat(matcher.group());
                float[] data = new float[numVar];

                for (int i=0; i<numVar; i++){
                    pattern = Pattern.compile("\\b" + varNames[i] + "\\s+=\\s*" + floatSubRegex);
                    matcher = pattern.matcher(inputRecord);
                    if (!matcher.find()){
                        finished = true;
                        break;
                    }
                    String varDeclarationString = matcher.group().trim();
                    pattern = Pattern.compile("(\\s+|=)(" + floatSubRegex + ")");
                    matcher = pattern.matcher(varDeclarationString);
                    if (!matcher.find()){
                        finished = true;
                        break;
                    }
                    data[i] = Float.parseFloat(matcher.group(2).trim());
                }
                if (finished) break;

                StringBuilder outputRecordBuilder = new StringBuilder();
                outputRecordBuilder.append(time);
                for (int i=0; i<numVar; i++){
                    outputRecordBuilder.append(',');
                    outputRecordBuilder.append(data[i]);
                }
                writer.println(outputRecordBuilder.toString());
            }


        }
        finally{
            if (reader != null) reader.close();
            if (writer != null) writer.close();
        }

    }
}
