package com.example.navigation_bar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class schoolt_table_storage extends AppCompatActivity {
    public int position;
    public String table_name, temp_string;
    public ArrayList<storage_table> storage_tables = new ArrayList<>();
    public RelativeLayout l;
    public storage_table temp;
    Button add_row, add_column, delete_row, delete_column;

    public void delete_operation(int row, int column, String operation)
    {
        final TableLayout ll = (TableLayout) findViewById(R.id.table_layout_in_sctt);
        ll.removeAllViews();
        ArrayList <String>temp_table = new ArrayList();
        if(operation == "row")
        {
            for(int i=0;i<storage_tables.get(position).rows;i++)
            {
                for(int j=0;j<storage_tables.get(position).columns;j++)
                {
                    if(i != row)
                    {
                        temp_table.add(storage_tables.get(position).table.get((i * storage_tables.get(position).columns)+j));
                    }
                }
            }
            storage_tables.get(position).rows -=1;
        }
        else
        {
            for(int i=0;i<storage_tables.get(position).rows;i++)
            {
                for(int j=0;j<storage_tables.get(position).columns;j++)
                {
                    if(j != column)
                    {
                        temp_table.add(storage_tables.get(position).table.get((i * storage_tables.get(position).columns)+j));
                    }
                }
            }
            storage_tables.get(position).columns -=1;
        }
        storage_tables.get(position).table = temp_table;
        //Toast.makeText(ll.getContext(),"Storage tables size : "+storage_tables.get(position).table.size(),Toast.LENGTH_SHORT).show();
        for (int i = 0; i < storage_tables.get(position).rows; i++)
        {
            TableRow tableRow = new TableRow(ll.getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(lp);
            for (int j = 0; j < storage_tables.get(position).columns; j++)
            {
                final EditText second = new EditText(ll.getContext());
                final TextView tv = new TextView(ll.getContext());
                //                    RelativeLayout.LayoutParams layoutParams = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //                   layoutParams.setMargins(left,top,right,bottom);
                //                   top+=90;
                //                   second.setLayoutParams(layoutParams);
                //                  second.setText(storage_tables.get(position).table.get(i));
                if(!storage_tables.get(position).table.get((i*(storage_tables.get(position).columns))+j).equals(""))
                {
                    second.setText(storage_tables.get(position).table.get((i*(storage_tables.get(position).columns))+j));
                }
                else
                {
                    second.setHint(storage_tables.get(position).name);
                    second.setHintTextColor(Color.parseColor("#3D5069"));
                }
                second.setTextColor(Color.WHITE);
                second.setTextSize(20);
                second.setCursorVisible(true);
                second.setBackgroundResource(R.drawable.bodrer_of_cell);
                final int finalI = i;
                final int finalJ = j;
                second.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {

                    }

                    @Override
                    public void afterTextChanged(Editable editable)
                    {
                        temp_string = editable.toString();
                        valueChanged((finalI*(storage_tables.get(position).columns))+finalJ, temp_string);
                    }
                    void valueChanged(int pojision, String temp)
                    {
                        storage_tables.get(position).table.set(pojision, temp);
                        // Toast.makeText(ll.getContext(),"Text Changed",Toast.LENGTH_SHORT).show();
                    }
                });
//                    second.setMovementMethod(new ScrollingMovementMethod());
//                    this.addContentView(second,layoutParams);
                tableRow.addView(second);
            }
            ll.addView(tableRow, i);
//
        }
    }
    public void create_operation(int row, int column, String operation)
    {
        final TableLayout ll = (TableLayout) findViewById(R.id.table_layout_in_sctt);
        ll.removeAllViews();
        if(operation == "row")
        {
            if(row != storage_tables.get(position).rows-1)
            {
                for(int i=0;i<storage_tables.get(position).rows;i++)
                {
                    for(int j=0;j<storage_tables.get(position).columns;j++)
                    {
                        if(i == row+1)
                        {
                            storage_tables.get(position).table.add((i * storage_tables.get(position).columns)+j,"");
                        }
                    }
                }
            }
            else
            {
                for(int j=0;j<storage_tables.get(position).columns;j++)
                {
                    storage_tables.get(position).table.add("");
                }
            }
            //Toast.makeText(ll.getContext(),"Storage table size :"+storage_tables.get(position).table.size(),Toast.LENGTH_SHORT).show();
            storage_tables.get(position).rows +=1;
        }
        else
        {
            if(column!=storage_tables.get(position).columns-1)
            {
                ArrayList <String>temp_array = new ArrayList();
                for(int i=0;i<storage_tables.get(position).rows;i++)
                {
                    for(int j=0;j<storage_tables.get(position).columns+1;j++)
                    {
                        temp_array.add("");
                    }
                }
                int count = 0;
                for(int i=0;i<storage_tables.get(position).rows;i++)
                {
                    for(int j=0;j<storage_tables.get(position).columns+1;j++)
                    {
                        if(j!=column+1)
                        {
                            temp_array.set((i*(storage_tables.get(position).columns+1) + (j)),storage_tables.get(position).table.get(count));
                            count++;
                        }
                    }
                }
                storage_tables.get(position).table = temp_array;
/*                for(int i=0;i<storage_tables.get(position).rows;i++)
                {
                    for (int j = 0; j < storage_tables.get(position).columns; j++)
                    {
                        if (j == column)
                        {
                            storage_tables.get(position).table.add((i * storage_tables.get(position).columns)+(j+1),"");
                        }
                    }
                }*/
            }
            else
            {
                for (int i = 0; i < storage_tables.get(position).rows - 1; i++) {
                    //arrayList.add("");
                    storage_tables.get(position).table.add((i + 1) * (storage_tables.get(position).columns + 1) - 1, "");
                }
                storage_tables.get(position).table.add("");
            }
            storage_tables.get(position).columns+=1;
        }
        //Toast.makeText(ll.getContext(),"Storage tables size : "+storage_tables.get(position).table.size(),Toast.LENGTH_SHORT).show();
        for (int i = 0; i < storage_tables.get(position).rows; i++)
        {
            TableRow tableRow = new TableRow(ll.getContext());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(lp);
            for (int j = 0; j < storage_tables.get(position).columns; j++)
            {
                final EditText second = new EditText(ll.getContext());
                final TextView tv = new TextView(ll.getContext());
                //                    RelativeLayout.LayoutParams layoutParams = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //                   layoutParams.setMargins(left,top,right,bottom);
                //                   top+=90;
                //                   second.setLayoutParams(layoutParams);
                //                  second.setText(storage_tables.get(position).table.get(i));
                if(!storage_tables.get(position).table.get((i*(storage_tables.get(position).columns))+j).equals(""))
                {
                    second.setText(storage_tables.get(position).table.get((i*(storage_tables.get(position).columns))+j));
                }
                else
                {
                    second.setHint(storage_tables.get(position).name);
                    second.setHintTextColor(Color.parseColor("#3D5069"));
                }
                second.setTextColor(Color.WHITE);
                second.setTextSize(20);
                second.setCursorVisible(true);
                second.setBackgroundResource(R.drawable.bodrer_of_cell);
                final int finalI = i;
                final int finalJ = j;
                second.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {

                    }

                    @Override
                    public void afterTextChanged(Editable editable)
                    {
                        temp_string = editable.toString();
                        valueChanged((finalI*(storage_tables.get(position).columns))+finalJ, temp_string);
                    }
                    void valueChanged(int pojision, String temp)
                    {
                        storage_tables.get(position).table.set(pojision, temp);
                        // Toast.makeText(ll.getContext(),"Text Changed",Toast.LENGTH_SHORT).show();
                    }
                });
//                    second.setMovementMethod(new ScrollingMovementMethod());
//                    this.addContentView(second,layoutParams);
                tableRow.addView(second);
            }
            ll.addView(tableRow, i);
//
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#434559")));
        setContentView(R.layout.table_layout_through_sctt);
        Bundle bundle;
        bundle = getIntent().getExtras();
        ScrollView scrollView = new ScrollView(this);
        LinearLayout.LayoutParams layoutP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layoutP);
        add_row = (Button) findViewById(R.id.add_row_btn_in_table_layout_through_sctt);
        add_column = (Button) findViewById(R.id.add_col_btn_in_table_layout_through_sctt);
        delete_row = (Button) findViewById(R.id.remove_row_btn_in_table_layout_through_sctt);
        delete_column = (Button) findViewById(R.id.remove_col_btn_in_table_layout_through_sctt);

        final LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(linearParams);

        scrollView.addView(linearLayout);

//        storage_tables = (ArrayList<storage_table>)getIntent().getSerializableExtra("tables_data");
        storage_tables = bundle.getParcelableArrayList("tables_data");
        position = (Integer) bundle.getInt("position");
/*        table_storages =  (ArrayList<schooltt.table_storage>)bundle.getSerializable("Data_of_tables");
        position = (Integer)bundle.getSerializable("position");*/
//        storage_tables.get(position).table.add(storage_tables.get(position).name);
        int top = 10, left = 121, right = 0, bottom = 0;
//

        if (storage_tables.get(position).table.size() == 0)
        {
            final TableLayout ll = (TableLayout) findViewById(R.id.table_layout_in_sctt);
            for (int i = 0; i < storage_tables.get(position).rows; i++) {
                for (int j = 0; j < storage_tables.get(position).columns; j++) {
                    storage_tables.get(position).table.add("");
                }
            }
            for (int i = 0; i < storage_tables.get(position).rows; i++) {
                TableRow tableRow = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tableRow.setLayoutParams(lp);
                for (int j = 0; j < storage_tables.get(position).columns; j++) {
                    final EditText second = new EditText(this);
                    final TextView tv = new TextView(this);
                    second.setHint(storage_tables.get(position).name);
                    second.setTextColor(Color.WHITE);
                    second.setHintTextColor(Color.parseColor("#3D5069"));
                    second.setTextSize(20);
                    second.setCursorVisible(true);
                    second.setBackgroundResource(R.drawable.bodrer_of_cell);
                    final int finalI = i;
                    final int finalJ = j;
                    second.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            temp_string = editable.toString();
                            valueChanged( (finalI*(storage_tables.get(position).columns))+finalJ, temp_string);
//                            storage_tables.get(position).table.add(editable.toString());
                        }

                        void valueChanged(int pojision, String temp)
                        {
                            storage_tables.get(position).table.set(pojision, temp);
                            // Toast.makeText(ll.getContext(),"Text Changed",Toast.LENGTH_SHORT).show();
                        }
                    });
//                    storage_tables.get(position).table.add(temp_string);
//                    second.setMovementMethod(new ScrollingMovementMethod());
//                    this.addContentView(second,layoutParams);
                    tableRow.addView(second);
                }
                ll.addView(tableRow, i);
            }
        }
        else
        {
            final TableLayout ll = (TableLayout) findViewById(R.id.table_layout_in_sctt);
            for (int i = 0; i < storage_tables.get(position).rows; i++)
            {
                TableRow tableRow = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tableRow.setLayoutParams(lp);
                for (int j = 0; j < storage_tables.get(position).columns; j++)
                {
                    final EditText second = new EditText(this);
                    final TextView tv = new TextView(this);
                    //                    RelativeLayout.LayoutParams layoutParams = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    //                   layoutParams.setMargins(left,top,right,bottom);
                    //                   top+=90;
                    //                   second.setLayoutParams(layoutParams);
                    //                  second.setText(storage_tables.get(position).table.get(i));
                    if(!storage_tables.get(position).table.get((i*(storage_tables.get(position).columns))+j).equals(""))
                    {
                        second.setText(storage_tables.get(position).table.get((i*(storage_tables.get(position).columns))+j));
                    }
                    else
                    {
                        second.setHint(storage_tables.get(position).name);
                        second.setHintTextColor(Color.parseColor("#3D5069"));
                    }
                    second.setTextColor(Color.WHITE);
                    second.setTextSize(20);
                    second.setCursorVisible(true);
                    second.setBackgroundResource(R.drawable.bodrer_of_cell);
                    final int finalI = i;
                    final int finalJ = j;
                    second.addTextChangedListener(new TextWatcher()
                    {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                        {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                        {

                        }

                        @Override
                        public void afterTextChanged(Editable editable)
                        {
                            temp_string = editable.toString();
                            valueChanged((finalI*(storage_tables.get(position).columns))+finalJ, temp_string);
                        }
                        void valueChanged(int pojision, String temp)
                        {
                            storage_tables.get(position).table.set(pojision, temp);
                            // Toast.makeText(ll.getContext(),"Text Changed",Toast.LENGTH_SHORT).show();
                        }
                    });
//                    second.setMovementMethod(new ScrollingMovementMethod());
//                    this.addContentView(second,layoutParams);
                    tableRow.addView(second);
                }
                ll.addView(tableRow, i);
//
            }
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            //            public ArrayList<ArrayList<String>>temporary_2D_List = new ArrayList<ArrayList<String>>();
            @Override
            public void onClick(View view) {
 /*               int on = 0;
                for(int i=0;i<storage_tables.get(position).rows;i++)
                {
                    ArrayList<String>arrayList = new ArrayList<String>();
                    for(int j=0;j<storage_tables.get(position).columns;j++)
                    {
                        arrayList.add(storage_tables.get(position).table.get(on));
                        on++;
                    }
                    temporary_2D_List.add(arrayList);
                }*/
                switch (view.getId()) {
                    /*
                    for (int j = 0; j < storage_tables.get(position).columns; j++) {
                            //arrayList.add("");
                            storage_tables.get(position).table.add("");
                        }
                        storage_tables.get(position).rows += 1;

                     */
                    case R.id.add_row_btn_in_table_layout_through_sctt:
//                        ArrayList<String>arrayList = new ArrayList<String>();
                        final TableLayout ll = (TableLayout) findViewById(R.id.table_layout_in_sctt);
                        ll.removeAllViews();
                        Toast.makeText(ll.getContext(), "Click any row after which you want to add the row", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(l3.getContext(),"Size of storage table[i] : "+storage_tables.get(position).table.size(),Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < storage_tables.get(position).rows; i++) {
                            TableRow tableRow = new TableRow(ll.getContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            tableRow.setLayoutParams(lp);
                            for (int j = 0; j < storage_tables.get(position).columns; j++) {
                                final EditText second = new EditText(ll.getContext());
                                final TextView tv = new TextView(ll.getContext());
                                //                    RelativeLayout.LayoutParams layoutParams = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                //                   layoutParams.setMargins(left,top,right,bottom);
                                //                   top+=90;
                                //                   second.setLayoutParams(layoutParams);
                                //                  second.setText(storage_tables.get(position).table.get(i));
                                if (!storage_tables.get(position).table.get((i * (storage_tables.get(position).columns)) + j).equals("")) {
                                    second.setText(storage_tables.get(position).table.get((i * (storage_tables.get(position).columns)) + j));
                                } else {
                                    second.setHint(storage_tables.get(position).name);
                                    second.setHintTextColor(Color.parseColor("#3D5069"));
                                }
                                second.setTextColor(Color.WHITE);
                                second.setTextSize(20);
                                second.setCursorVisible(true);
                                second.setFocusable(false);
                                final int finalI = i;
                                final int finalJ = j;
                                second.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        create_operation(finalI, finalJ, "row");
                                    }
                                });
                                second.setBackgroundResource(R.drawable.bodrer_of_cell);
//                    second.setMovementMethod(new ScrollingMovementMethod());
//                    this.addContentView(second,layoutParams);
                                tableRow.addView(second);
                            }
                            ll.addView(tableRow, i);
//
                        }
                        break;
                    //temporary_2D_List.add(arrayList);
                    //Toast.makeText(ll.getContext(),"Temp 2d list rows"+temporary_2D_List.size(),Toast.LENGTH_SHORT).show();
    /*                    for (int i = 0; i < temporary_2D_List.size(); i++) {
                            TableRow tableRow = new TableRow(ll.getContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            tableRow.setLayoutParams(lp);
                            for (int j = 0; j < temporary_2D_List.get(i).size(); j++)
                            {
                                final EditText second = new EditText(ll.getContext());
                                final TextView tv = new TextView(ll.getContext());
                                if(!temporary_2D_List.get(i).get(j).equals(""))
                                {
                                    second.setText(temporary_2D_List.get(i).get(j));
                                }
                                else
                                {
                                    second.setHint(storage_tables.get(position).name);
                                    second.setHintTextColor(Color.parseColor("#3D5069"));
                                }
                                second.setTextColor(Color.WHITE);
                                second.setTextSize(20);
                                second.setCursorVisible(true);
                                second.setBackgroundResource(R.drawable.bodrer_of_cell);
                                final int finalI = i;
                                final int finalJ = j;
                                second.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                                    {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                                    {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                        temp_string = editable.toString();
                                        valueChanged(finalI, finalJ,temp_string);
                                        storage_tables.get(position).table.clear();
                                        for(int i=0;i<temporary_2D_List.size();i++)
                                        {
                                            for (int j=0;j<temporary_2D_List.get(i).size();j++)
                                            {
                                                storage_tables.get(position).table.add(temporary_2D_List.get(i).get(j));
                                            }
                                        }
                                        storage_tables.get(position).rows = temporary_2D_List.size();
                                        storage_tables.get(position).columns = temporary_2D_List.get(0).size();
                                        //temporary_2D_List.clear();
                                        //                                  valueChanged(((finalI + 1) * (finalJ + 1) - 1), temp_string);
    //                            storage_tables.get(position).table.add(editable.toString());
                                    }

                                    void valueChanged(int row,int column, String temp)
                                    {
                                            temporary_2D_List.get(row).set(column,temp);
                                    }
                                });
    //                    storage_tables.get(position).table.add(temp_string);
    //                    second.setMovementMethod(new ScrollingMovementMethod());
    //                    this.addContentView(second,layoutParams);
                                tableRow.addView(second);
                            }
                            ll.addView(tableRow, i);
                        } */

                    case R.id.add_col_btn_in_table_layout_through_sctt:
                        ArrayList<String>arrayList = new ArrayList<String>();
                        final TableLayout l2 = (TableLayout) findViewById(R.id.table_layout_in_sctt);
                        l2.removeAllViews();
                        Toast.makeText(l2.getContext(), "Click any column after which you want to add the column", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(l3.getContext(),"Size of storage table[i] : "+storage_tables.get(position).table.size(),Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < storage_tables.get(position).rows; i++) {
                            TableRow tableRow = new TableRow(l2.getContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            tableRow.setLayoutParams(lp);
                            for (int j = 0; j < storage_tables.get(position).columns; j++) {
                                final EditText second = new EditText(l2.getContext());
                                final TextView tv = new TextView(l2.getContext());
                                //                    RelativeLayout.LayoutParams layoutParams = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                //                   layoutParams.setMargins(left,top,right,bottom);
                                //                   top+=90;
                                //                   second.setLayoutParams(layoutParams);
                                //                  second.setText(storage_tables.get(position).table.get(i));
                                if (!storage_tables.get(position).table.get((i * (storage_tables.get(position).columns)) + j).equals("")) {
                                    second.setText(storage_tables.get(position).table.get((i * (storage_tables.get(position).columns)) + j));
                                } else {
                                    second.setHint(storage_tables.get(position).name);
                                    second.setHintTextColor(Color.parseColor("#3D5069"));
                                }
                                second.setTextColor(Color.WHITE);
                                second.setTextSize(20);
                                second.setCursorVisible(true);
                                second.setFocusable(false);
                                final int finalI = i;
                                final int finalJ = j;
                                second.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        create_operation(finalI, finalJ, "column");
                                    }
                                });
                                second.setBackgroundResource(R.drawable.bodrer_of_cell);
//                    second.setMovementMethod(new ScrollingMovementMethod());
//                    this.addContentView(second,layoutParams);
                                tableRow.addView(second);
                            }
                            l2.addView(tableRow, i);
//
                        }
                        break;

                    case R.id.remove_row_btn_in_table_layout_through_sctt:
                        final TableLayout l3 = (TableLayout) findViewById(R.id.table_layout_in_sctt);
                        l3.removeAllViews();
                        Toast.makeText(l3.getContext(), "Click any cell of the row which you want to remove", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(l3.getContext(),"Size of storage table[i] : "+storage_tables.get(position).table.size(),Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < storage_tables.get(position).rows; i++) {
                            TableRow tableRow = new TableRow(l3.getContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            tableRow.setLayoutParams(lp);
                            for (int j = 0; j < storage_tables.get(position).columns; j++) {
                                final EditText second = new EditText(l3.getContext());
                                final TextView tv = new TextView(l3.getContext());
                                //                    RelativeLayout.LayoutParams layoutParams = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                //                   layoutParams.setMargins(left,top,right,bottom);
                                //                   top+=90;
                                //                   second.setLayoutParams(layoutParams);
                                //                  second.setText(storage_tables.get(position).table.get(i));
                                if (!storage_tables.get(position).table.get((i * (storage_tables.get(position).columns)) + j).equals("")) {
                                    second.setText(storage_tables.get(position).table.get((i * (storage_tables.get(position).columns)) + j));
                                } else {
                                    second.setHint(storage_tables.get(position).name);
                                    second.setHintTextColor(Color.parseColor("#3D5069"));
                                }
                                second.setTextColor(Color.WHITE);
                                second.setTextSize(20);
                                second.setCursorVisible(true);
                                second.setFocusable(false);
                                final int finalI = i;
                                final int finalJ = j;
                                second.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(storage_tables.get(position).table.size() == storage_tables.get(position).columns)
                                        {
                                            Toast.makeText(l3.getContext(),"Only one row remaining. Cannot be deleted",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            delete_operation(finalI, finalJ, "row");
                                        }
                                    }
                                });
                                second.setBackgroundResource(R.drawable.bodrer_of_cell);
//                    second.setMovementMethod(new ScrollingMovementMethod());
//                    this.addContentView(second,layoutParams);
                                tableRow.addView(second);
                            }
                            l3.addView(tableRow, i);
//
                        }
                        break;

                    case R.id.remove_col_btn_in_table_layout_through_sctt:
                        final TableLayout l4 = (TableLayout) findViewById(R.id.table_layout_in_sctt);
                        l4.removeAllViews();
                        Toast.makeText(l4.getContext(), "Click any cell of the column which you want to remove", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(l4.getContext(),"Size of storage table[i] : "+storage_tables.get(position).table.size(),Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < storage_tables.get(position).rows; i++) {
                            TableRow tableRow = new TableRow(l4.getContext());
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            tableRow.setLayoutParams(lp);
                            for (int j = 0; j < storage_tables.get(position).columns; j++) {
                                final EditText second = new EditText(l4.getContext());
                                final TextView tv = new TextView(l4.getContext());
                                //                    RelativeLayout.LayoutParams layoutParams = new  RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                //                   layoutParams.setMargins(left,top,right,bottom);
                                //                   top+=90;
                                //                   second.setLayoutParams(layoutParams);
                                //                  second.setText(storage_tables.get(position).table.get(i));
                                if (!storage_tables.get(position).table.get((i * (storage_tables.get(position).columns)) + j).equals("")) {
                                    second.setText(storage_tables.get(position).table.get((i * (storage_tables.get(position).columns)) + j));
                                } else {
                                    second.setHint(storage_tables.get(position).name);
                                    second.setHintTextColor(Color.parseColor("#3D5069"));
                                }
                                second.setTextColor(Color.WHITE);
                                second.setTextSize(20);
                                second.setCursorVisible(true);
                                second.setFocusable(false);
                                final int finalI = i;
                                final int finalJ = j;
                                second.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(storage_tables.get(position).table.size() == storage_tables.get(position).rows)
                                        {
                                            Toast.makeText(l4.getContext(),"Only one column remaining. Cannot be deleted!",Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            delete_operation(finalI, finalJ, "column");
                                        }
                                    }
                                });
                                second.setBackgroundResource(R.drawable.bodrer_of_cell);
//                    second.setMovementMethod(new ScrollingMovementMethod());
//                    this.addContentView(second,layoutParams);
                                tableRow.addView(second);
                            }
                            l4.addView(tableRow, i);
                        }
                        break;
                    //storage_tables.get(position).table.clear();
                /*for(int i=0;i<temporary_2D_List.size();i++)
                {
                    for (int j=0;j<temporary_2D_List.get(i).size();j++)
                    {
                        storage_tables.get(position).table.add(temporary_2D_List.get(i).get(j));
                    }
                }
                storage_tables.get(position).rows = temporary_2D_List.size();
                storage_tables.get(position).columns = temporary_2D_List.get(0).size();
                Toast.makeText(add_row.getContext(),"Pati gyu",Toast.LENGTH_SHORT).show();
                //temporary_2D_List.clear(); */
                }
            }
        };
        add_row.setOnClickListener(onClickListener);
        add_column.setOnClickListener(onClickListener);
        delete_row.setOnClickListener(onClickListener);
        delete_column.setOnClickListener(onClickListener);
    /*   EditText editText = new EditText(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        editText.setLayoutParams(lp);
        editText.setText("Position  : "+position);
        editText.setTextColor(Color.WHITE);
        editText.setTextSize(20);
        editText.setCursorVisible(true); */
        TextView second = new TextView(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(121, 140, 0, 0);
        second.setLayoutParams(layoutParams);
//        second.setText("Rows : " + storage_tables.get(position).rows + "Columns : " + storage_tables.get(position).columns);
        second.setTextColor(Color.WHITE);
        second.setTextSize(20);
        second.setCursorVisible(true);
        TextView third = new TextView(this);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(121, 160, 0, 0);
        //     third.setText("Size of filled table:" + storage_tables.get(position).table.size());
        third.setTextColor(Color.WHITE);
        third.setTextSize(20);
//        this.addContentView(editText,lp);
        this.addContentView(second, layoutParams);
        this.addContentView(third, layoutParams1);
//        this.addContentView(scrollView,layoutParams);
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putSerializable("storage_tables", storage_tables);
        b.putInt("pos", position);
//       b.putParcelableArrayList("storage_tables",storage_tables);
        intent.putExtras(b);
        setResult(RESULT_OK, intent);
//        finish();
        //       table_name = table_storages.get(position).name;
        //      this.setTitle(table_name);
    }
}
