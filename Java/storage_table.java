package com.example.navigation_bar;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class storage_table implements Parcelable, Serializable
{
    public int rows;
    public int columns;
    public String name;
    public List<String>table = new ArrayList<String>();
    protected storage_table(Parcel in)
    {
        super();
        readFromParcel(in);
    }

    public static final Creator<storage_table> CREATOR = new Creator<storage_table>() {
        @Override
        public storage_table createFromParcel(Parcel in) {
            return new storage_table(in);
        }

        @Override
        public storage_table[] newArray(int size) {
            return new storage_table[size];
        }
    };

    public storage_table(String name, int i, int i1,List<String> s1)
    {
        this.name = name;
        this.rows = i;
        this.columns = i1;
        this.table = s1;
    }

    public void readFromParcel(Parcel in)
    {
        rows = in.readInt();
        columns = in.readInt();
        name = in.readString();
        in.readStringList(table);   //doubtful
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(rows);
        parcel.writeInt(columns);
        parcel.writeString(name);
        parcel.writeStringList(table);
    }
}
