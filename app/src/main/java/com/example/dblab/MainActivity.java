package com.example.dblab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button add, viewAll;
    EditText name, age;

    ListView lv_customerList;
    ArrayAdapter customerArrayAdapter;
    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        viewAll = findViewById(R.id.viewAll);
        lv_customerList = findViewById(R.id.lv_customerList);
        dataBaseHelper = new DataBaseHelper(MainActivity.this);


        ShowCustomersOnListView(dataBaseHelper);


        add.setOnClickListener(new View.OnClickListener() {
            @Override

                public void onClick(View view) {
                customerModel cModel;
                try{
                    cModel =  new customerModel(-1, name.getText().toString(), Integer.parseInt(age.getText().toString()));
                    Toast.makeText(MainActivity.this, cModel.toString(), Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Error creating customer", Toast.LENGTH_SHORT).show();
                    cModel = new customerModel(-1, "error", 0);
                }
//                making refrence to the new custmer database
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                boolean success = dataBaseHelper.addOne(cModel);
                Toast.makeText(MainActivity.this, "success: " + success, Toast.LENGTH_SHORT).show();
                ShowCustomersOnListView(dataBaseHelper);
            }
        });


        viewAll.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ShowCustomersOnListView(dataBaseHelper);

                customerArrayAdapter = new ArrayAdapter<customerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
                lv_customerList.setAdapter(customerArrayAdapter);

                //Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                customerModel ClickedStudent = (customerModel) adapterView.getItemAtPosition(i);
                dataBaseHelper.DeleteOne(ClickedStudent);
                ShowCustomersOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this, "Deleted: " + ClickedStudent.toString(), Toast.LENGTH_SHORT).show();
            }

        });



        }

    private void ShowCustomersOnListView(DataBaseHelper dataBaseHelper) {
        customerArrayAdapter = new ArrayAdapter<customerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        lv_customerList.setAdapter(customerArrayAdapter);
    }


}



