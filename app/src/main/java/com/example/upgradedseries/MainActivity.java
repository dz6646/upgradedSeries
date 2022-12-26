package com.example.upgradedseries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView position;
    TextView sum;
    RecyclerView serie;
    EditText firstVal;
    EditText progVal;
    Button update;
    RadioGroup type;
    String[] series;
    List<Item> items;
    myAdapter adp;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count = 0;
        series = new String[20];
        items = new ArrayList<>();
        position = findViewById(R.id.pos);
        sum = findViewById(R.id.sum);
        serie = findViewById(R.id.series_recycler_view);
        firstVal = findViewById(R.id.first_value_input);
        progVal = findViewById(R.id.progressor_input);
        update = findViewById(R.id.update_button);
        type = findViewById(R.id.series_type_radio_group);
        initList();
        //initArr();
        serie.setLayoutManager(new LinearLayoutManager(this));
        adp = new myAdapter(getApplicationContext(), items, new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                if(count > 0)
                {
                    position.setText("Position: " + (pos + 1));
                    double clickSum = Double.parseDouble(series[0]);
                    for(int i = 1; i < pos; i++)
                    {
                        clickSum = clickSum + Double.parseDouble(series[i]);
                    }
                    sum.setText("Sum Till Here: " + String.format(Double.toString(clickSum), "%.2f"));
                }
            }
        });
        serie.setAdapter(adp);

    }


    public void updateRecycler(View view) {
        int selectedId = type.getCheckedRadioButtonId();
        adp.notifyDataSetChanged();
        if (selectedId != -1)
        {
            count++;
            // a radio button is selected
            RadioButton selectedRadioButton = findViewById(selectedId);
            String selectedRadioButtonText = selectedRadioButton.getText().toString();
            double first;
            double prog;
            if(inputCheck(firstVal.getText().toString()) && inputCheck(progVal.getText().toString()))
            {
                first = Double.parseDouble(firstVal.getText().toString());
                prog = Double.parseDouble(progVal.getText().toString());
            }
            else
            {
                Toast.makeText(this, "Wrong number", Toast.LENGTH_SHORT).show();
                return;
            }
            if(selectedRadioButtonText.equals("mathematical"))
            {
                mathSeries(first, prog);
            }
            else
            {
                geoSeries(first, prog);
            }
        }
        else
        {
            Toast.makeText(this, "Please select a serie", Toast.LENGTH_SHORT).show();
        }
    }

    public void mathSeries(double first, double prog)
    {
        for(int i = 0; i < series.length; i++)
        {
            series[i] = String.format("%.02f", first);
            items.set(i, new Item(series[i]));
            first += prog;
        }
    }

    public void geoSeries(double first, double prog)
    {
        for(int i = 0; i < series.length; i++)
        {
            series[i] = String.format("%.02f", first);
            items.set(i, new Item(series[i]));
            first *= prog;
        }
    }

    public boolean inputCheck(String check)
    {
        if(check.equals(""))
        {
            return false;
        }
        for(int i = 0; i < check.length(); i++)
        {
            if((check.charAt(i) < '0' || check.charAt(i) > '9') && (check.equals("-") || check.equals(".") || check.equals("-.")))
            {
                return false;
            }
        }
        return true;
    }

    public void initArr()
    {
        for(int i = 0; i < series.length; i++)
        {
            series[i] = "";
        }
    }
    public void initList()
    {
        for(int i = 0; i < 20; i++)
        {
            items.add(new Item(""));
        }
    }
}