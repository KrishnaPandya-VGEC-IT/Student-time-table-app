package com.example.navigation_bar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

public class schooltt extends Fragment implements View.OnClickListener
{
    int rows=3,columns=3;
    View v;
    RelativeLayout rl;
    public int dynamic_id=1000;
    TextView tv;
    private FloatingActionButton floating_create;
    private  Fragment mMyFragment;
    View m_view;
    public Bundle savedState = null;
    private TextView vstup;
    public  ArrayList<example_item>example_items = new ArrayList<>();
    public  ArrayList<storage_table>storage_tables = new ArrayList<>();
    private RecyclerView recyclerView;
    private example_adapter adapter;
    TableLayout tableLayout;
    String s;
    private RecyclerView.LayoutManager mLayoutmanager;
    Button button;
    private String table_name;
    private Configuration configuration;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_sctimetb, container, false);
        BuildRecyclerView();
/*        if(savedInstanceState != null)
        {
            ArrayList<example_item> list = savedInstanceState.getParcelableArrayList("key");
            for(int i=0 ; i<list.size();i++)
            {
                example_items.add(new example_item(list.get(i).getMtext1(),list.get(i).getMtext2()));
            }
        } */
        button = (Button) v.findViewById(R.id.btn_add);
        View apdu = inflater.inflate(R.layout.example_cardview,container,false);
        tableLayout = (TableLayout)apdu.findViewById(R.id.table_in_cardview);
        floating_create = (FloatingActionButton) v.findViewById(R.id.floating_add);
        adapter.setOnItemClickListener(new example_adapter.OnItemClickListener()
        {
            @Override
            public void OnItemClick(int position)
            {
                changeItem(position,"Yo!");
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });

        button.setOnClickListener((View.OnClickListener) this);
        floating_create.setOnClickListener((View.OnClickListener)this);
    /*    button.setOnClickListener(View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t = v.findViewById(R.id.tv);
                t.setText("YA MAAYLA KUTRYA");
            }
        }); */
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                Bundle b =  data.getExtras();
                storage_tables = (ArrayList<storage_table>) b.getSerializable("storage_tables");
                int temp_pos = b.getInt("pos");
                saveData();
            }
        }
    }

    public void changeItem(int position, String text)
    {
        Intent intent = new Intent(this.getContext(),schoolt_table_storage.class);
        Bundle bundle  = new Bundle();
        bundle.putInt("position",position);
//        bundle.putSerializable("tables_data",storage_tables);
        bundle.putParcelableArrayList("tables_data",storage_tables);
        intent.putExtras(bundle);
//        startActivity(intent);
        startActivityForResult(intent,1);
        adapter.notifyItemChanged(position);
    }
    public void createExampleList()
    {
      /*  example_items.add(new example_item("hello","world"));
        example_items.add(new example_item("hello","world"));
        example_items.add(new example_item("hello","world")); */
    }

    public void BuildRecyclerView()
    {
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        mLayoutmanager = new LinearLayoutManager(this.getContext());
        adapter = new example_adapter(example_items);
        recyclerView.setLayoutManager(mLayoutmanager);
        recyclerView.setAdapter(adapter);
    }

    public void insertItem(int pos,String name)
    {
        TableLayout tb = new TableLayout(v.getContext());
        example_items.add(pos,new example_item(name,"Table"));
        storage_tables.add(new storage_table(table_name,3,3,new ArrayList<String>()));
        adapter.notifyDataSetChanged();
        saveData();
    }

    public void removeItem(int pos)
    {
        example_items.remove(pos);
        storage_tables.remove(pos);
        adapter.notifyItemRemoved(pos);
        View card = v.findViewById(R.id.cardview);
        card.requestLayout();
        saveData();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
/*        outState.putParcelableArrayList("key",example_items);
        Set<example_item>set = new HashSet<example_item>();
        set.addAll(example_items); */
        super.onSaveInstanceState(outState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
/*        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Toast.makeText(this.getContext(),"Landscape orientation prohibited",Toast.LENGTH_LONG).show();
        } */
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null)
        {
            loadData();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        loadData();
/*        if(savedInstanceState!=null) {
            boolean value = savedInstanceState.containsKey("key");

        } */
    }


   /*    ArrayList<example_item> list = savedInstanceState.getParcelableArrayList("key");
            example_items = new ArrayList<example_item>();
            if (list.size() == 0)
            {
                Toast.makeText(this.getContext(), "Khali chhe",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this.getContext(),"Leti vakhte kaik chhe",Toast.LENGTH_LONG).show();
            } */
/*            for(int i=0;i<list.size();i++)
            {
                example_items.add(new example_item(list.get(i).getMtext1().toString(),list.get(i).getMtext2().toString()));
            }*/
/*        if(savedInstanceState!=null)
        {
            example_items = savedInstanceState.getParcelableArrayList("key");
        } */


    public void saveData()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Gson second_gson = new Gson();
        String json = gson.toJson(example_items);
        String second_json = second_gson.toJson(storage_tables);
        editor.putString("task list",json);
        editor.putString("storage tables",second_json);
        editor.apply();
    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Gson second_gson = new Gson();
        String json = sharedPreferences.getString("task list",null);
        String second_json = sharedPreferences.getString("storage tables",null);
        Type type = new TypeToken<ArrayList<example_item>>(){}.getType();
        Type second_type =new TypeToken<ArrayList<storage_table>>(){}.getType();
        example_items = gson.fromJson(json,type);
        storage_tables = second_gson.fromJson(second_json,second_type);

        if(example_items == null)
        {
            example_items = new ArrayList<>();
        }
        if(storage_tables == null)
        {
            storage_tables = new ArrayList<>();
        }
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_add:
            case R.id.floating_add:
                AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());
                alert.setMessage("Enter table name");

// Set an EditText view to get user input
                final EditText input = new EditText(this.getContext());
                input.setTextColor(Color.BLACK);
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        table_name = input.getText().toString();
                        boolean flag =false;
                        if(table_name.length()!=0)
                        {
                            int position = example_items.size();
                            insertItem(position,table_name);
                        }
                        else
                        {
                            Toast.makeText(v.getContext(),"Table name cannot be empty",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        //table_name = "";
                    }
                });
                AlertDialog dialog = alert.create();
                Button b = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                Button c = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
//                alert.show();
                dialog.show();
/*                final EditText editText = new EditText(tableLayout.getContext());
                final Button ok = new Button()
                RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                editText.setLayoutParams(layoutParams);
                Button button = new Button(tableLayout.getContext());
                button.setLayoutParams(layoutParams);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         s = editText.getText().toString();
                    }
                }); */
                break;

        }
    }
}