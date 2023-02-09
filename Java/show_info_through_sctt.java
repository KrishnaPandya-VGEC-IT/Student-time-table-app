package com.example.navigation_bar;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class show_info_through_sctt extends AppCompatActivity
{
    String subject, abbreviation, location, type, teacher;
    TextView t_subject, t_abbreviation, t_location, t_type, t_teacher;
    Button back;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_info_through_sctt);
        Bundle b1 = getIntent().getExtras();
        subject = b1.getString("subject","");
        abbreviation = b1.getString("abbreviation","");
        location = b1.getString("location","");
        type = b1.getString("type", "" );
        teacher = b1.getString("teacher","");
        t_subject = (TextView)findViewById(R.id.ShowSubject_sctt);
        t_abbreviation = (TextView)findViewById(R.id.ShowAbbreviation_sctt);
        t_location  =(TextView)findViewById(R.id.ShowLocation_sctt);
        t_type  = (TextView )findViewById(R.id.ShowType_sctt);
        t_teacher = (TextView)findViewById(R.id.ShowTeacher_sctt);
        back = (Button)findViewById(R.id.Back_sctt);
        t_subject.setText(subject);
        t_abbreviation.setText(abbreviation);
        t_type.setText(type);
        t_location.setText(location);
        t_teacher.setText(teacher);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(back.getContext(),schoolt_table_storage.class);
                Bundle b = new Bundle();
                b.putInt("position",b1.getInt("position"));
                b.putSerializable("tables_data",b1.getSerializable("tables_data"));
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
