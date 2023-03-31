package com.example.march31;

import org.qtproject.qt.android.bindings.QtActivity;

import android.app.Activity;
import android.content.ClipData;
import android.net.Uri;
import android.util.Log;
import android.content.Intent;

import android.os.Bundle;

public class MainActivity extends QtActivity {
    public final String tag = "March 31 JAVA class";
    public final int REQUEST_CODE = 1;
    public long filePickPointer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    // HOW TO PREVENT ANDROID APPLICATION FROM SLEEPING/ AUTO LOCK


    public native void filePathReceived(long filePickerPointer, String filePath);

    public void openFile(long filePicker){
        filePickPointer = filePicker;
        Log.d(tag, filePicker+"");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        //intent.putExtra("pointer", filePicker);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data){
        if (requestcode == REQUEST_CODE && resultcode == Activity.RESULT_OK){
            if (data!= null){
                //long pointer = data.getLongExtra("pointer", 0L);
                //String strVal = pointer+"";
                //Log.d(tag, strVal);
                Uri uri = data.getData();
                //Log.d(tag, uri.getPath()+" "+ uri);

                filePathReceived(filePickPointer, "file:///"+uri.toString());
            }
        }
    }

}