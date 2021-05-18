package com.company;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws IOException {

        //Note - if the file is a link, then the code for the retrieval is similar
        // We have to retrieve the String from the link first and then parse the String itself instead of using FileReader
        //
        //URL url = new URL("http://domain.com/file.txt");
        //BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
        //StringBuilder sb = new StringBuilder();
        //String s;
        //while ((s = read.readLine()) != null)
        //    sb.append(s);
        //read.close();
        //Object obj = jsonParser.parse(sb.toString());
        //


        //Parsing the JSON array and retrieving the file
        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(new FileReader("alpha-qr-gFpwhsQ8fkY1.json"));
            JSONArray array = (JSONArray) obj;

            // Creating Iterator Object to sort the data into Imported and Domestic goods arrays.
            Iterator<JSONObject> it = array.iterator();
            ArrayList<JSONObject> Domestic = new ArrayList<JSONObject>();
            ArrayList<JSONObject> Imported = new ArrayList<JSONObject>();
            while(it.hasNext()){
                JSONObject jsonObject = (JSONObject) it.next();
                if((Boolean) jsonObject.get("domestic")){
                    Domestic.add(jsonObject);
                }else{
                    Imported.add(jsonObject);
                }
            }

            // Sorting the Domestic imports array
            Domestic.sort(Comparator.comparing(o -> o.get("name").toString()));
            float domestic_price = 0;

            //Printing the Domestic imports
            System.out.println(". Domestic");
            it = Domestic.iterator();

            while(it.hasNext()){
                JSONObject jsonObject = it.next();
                System.out.println("... " + jsonObject.get("name"));
                System.out.println("\tPrice: $" + jsonObject.get("price"));
                if(jsonObject.get("description").toString().length() > 30){
                    System.out.println("\t" + jsonObject.get("description").toString().substring(0,11) + "...");
                }else{
                    System.out.println("\t" + jsonObject.get("description"));
                }
                if(jsonObject.get("weight") == null){
                    System.out.println("\tWeight: N/A");
                }
                //Summing the price of every product
                domestic_price += (double)jsonObject.get("price");
            }

            // Sorting the Imported imports array
            Imported.sort(Comparator.comparing(o -> o.get("name").toString()));
            float import_price = 0;

            //Printing the Imported imports
            it = Imported.iterator();
            System.out.println(". Iterator");

            while(it.hasNext()){
                JSONObject jsonObject = it.next();
                System.out.println("... " + jsonObject.get("name"));
                System.out.println("\tPrice: $" + jsonObject.get("price"));
                if(jsonObject.get("description").toString().length() > 30){
                    System.out.println("\t" + jsonObject.get("description").toString().substring(0,11) + "...");
                }else{
                    System.out.println("\t" + jsonObject.get("description"));
                }
                if(jsonObject.get("weight") == null){
                    System.out.println("\tWeight: N/A");
                }
                //Summing the price of every product
                import_price += (double)jsonObject.get("price");
            }
            //Printing the total price
            System.out.println("Domestic cost: $" + domestic_price);
            System.out.println("Imported cost: $" + import_price);

            //Printing the total number of items
            System.out.println("Domestic size: " + Domestic.size());
            System.out.println("Imported size: " + Imported.size());
            

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
