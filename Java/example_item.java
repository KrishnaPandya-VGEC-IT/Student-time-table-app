package com.example.navigation_bar;
import android.os.Parcel;
import android.os.Parcelable;

public class example_item implements Parcelable
{
    private String mtext1;
    private String mtext2;

    public example_item(Parcel in)
    {
        super();
        readFromParcel(in);
    }
    public static final Parcelable.Creator<example_item> CREATOR = new Parcelable.Creator<example_item>() {
        public example_item createFromParcel(Parcel in) {
            return new example_item(in);
        }

        public example_item[] newArray(int size) {

            return new example_item[size];
        }

    };

    public void readFromParcel(Parcel in) {
       mtext1 = in.readString();
       mtext2 = in.readString();
    }
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mtext1);
        dest.writeString(mtext2);
    }

    public example_item(String text1,String text2) //TableLayout t1
    {
        mtext1 = text1;
        mtext2 = text2;
    }

    public void changeText1(String text)
    {
        mtext1 = text;
    }
    public String getMtext1()
    {
        return mtext1;
    }
    public String getMtext2()
    {
        return  mtext2;
    }
}
