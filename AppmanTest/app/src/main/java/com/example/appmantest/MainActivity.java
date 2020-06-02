package com.example.appmantest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    JsonData ojbData = new JsonData();
    ListView listView;
    ArrayList<String> arrayListJson = new ArrayList<String>();
    ArrayList<String> arrayListTh= new ArrayList<String>();
    ArrayList<String> arrayListEn = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Find Id of ListView
        listView = findViewById(R.id.listView);

        // receive a return json data
        String jsonDataStr = getJSON();

        try {
            // Convert json string to obj
            JSONObject jsonObject = new JSONObject(jsonDataStr);

            // Storage id first name and last name
            String id,fName,lName;

            // Get Id first name and last name from JSON Obj
            id = jsonObject.getString("Id");
            fName = jsonObject.getString("firstName");
            lName = jsonObject.getString("lastName");

            // set id first and last name to a JsonData object
            ojbData.setTitle(id,fName,lName);

            // Get json array from json obj
          JSONArray arraData = jsonObject.getJSONArray("data");

          // Instance for convert json array to json obj
          JSONObject jsonConvert = null;

          // Get size of json array
          int sizeArray = arraData.length();

          // Loop for convert json array to json obj and then add some data to ArrayLists
            for(int i=0;i<sizeArray;i++){

                // Convert json array to json obj
                jsonConvert = arraData.getJSONObject(i);

                // Get a docType and storage in dataID variable
                String dataID = jsonConvert.getString("docType");

                //  Get child json from parent json to JSON json obj
                JSONObject desJson = jsonConvert.getJSONObject("description");

                // Get a data of TH from desJson obj
                String th = desJson.getString("th");

                // Get a data of EN from desJson obj
                String en = desJson.getString("en");

                // Set data to setDescription Method
                ojbData.setDescription(dataID,th,en);

                // Add a data to a ArrayList for show on ListView
                arrayListJson.add(ojbData.getId());

                // Add data to both ArrayList for show des on AlertDialog in ListView setOnItemClickListener
                arrayListTh.add(th);
                arrayListEn.add(en);
            }

            // Specific a listView and send ArrayList of docType to ArrayAdapter
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,arrayListJson);

            // Set ArrayAdapter to ListView
            listView.setAdapter(arrayAdapter);

            // Method for click ListView and show a description of TH and EN
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // Declare and set a Message to Alert Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage(arrayListTh.get(position)+"/"+arrayListEn.get(position));
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // SET ID FIRST LAST NAME TO ACTION BAR
        this.getSupportActionBar().setTitle(ojbData.getTitle());


    }

    public String getJSON() {
        /*** The Method for fetch data from intern.json file and return a string of json data */
        try {
            // Specific path of local JSON file
            InputStream inputStream = this.getAssets().open("intern.json");

            // All size of json data if available
            int size = inputStream.available();

            // Declare byte instance
            byte[] bufBytes = new byte[size];

            // Read data and fetch buffer to instance
            inputStream.read(bufBytes);

            // Close Input Stream
            inputStream.close();

            // Convert type of data to UTF-8
            String data = new String(bufBytes,"UTF-8");

            // return json data
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}


