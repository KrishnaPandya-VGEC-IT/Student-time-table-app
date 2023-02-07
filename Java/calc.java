package com.example.navigation_bar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class calc extends Fragment
{
    View v;
    RelativeLayout l;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v= inflater.inflate(R.layout.fragment_calc,container,false);
/*       l = (RelativeLayout)v.findViewById(R.id.calc_view);
       RelativeLayout.LayoutParams lp =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
       EditText editText = new EditText(v.getContext());
       editText.setLayoutParams(lp);
       editText.setCursorVisible(true);
       editText.setBackgroundResource(R.drawable.back_text);
        l.addView(editText); */ //Whole working code
        return v;
    }
}
