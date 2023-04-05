package com.example.march31videoplayer;

import org.qtproject.qt.android.bindings.QtActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.content.Intent;
import android.content.Context;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class MainActivity extends QtActivity {

    public final String tag = "March 31 JAVA class";
    public final int REQUEST_CODE = 1;
    public long filePickPointer;
    public Context context;
    public String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public int[] granted = {PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED};

    //public AppCompatActivity appCompatObj = new AppCompatActivity();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onRequestPermissionsResult(1, permissions, granted);


    }
    public native void filePathReceived(long filePickerPointer, String filePath);

//    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        // Handle the result here
//                        Intent data = result.getData();
//                        Uri uri = data.getData();
//                        //path_tv.setText("File Path: "+data);
//
//                        File sourceFile = new File(uri.toString());
//                        String filename = sourceFile.getName();
//                        File destFile = new File (getCacheDir(), filename);
//
//                        try (InputStream in = new FileInputStream(sourceFile);
//                             OutputStream out = new FileOutputStream(destFile)) {
//                            byte[] buffer = new byte[1024];
//                            int length;
//                            while ((length = in.read(buffer)) > 0) {
//                                out.write(buffer, 0, length);
//                            }
//                        } catch (IOException e) {
//                            Log.d(tag,"Exception found: "+e.toString());
//                        }
//                        // playing video
//
//
//                        Log.d(tag,"The android uri is: "+uri);
//
//                        //media controller
//                        //MediaController vidControl = new MediaController(MainActivity.this);
////                        vidControl.setAnchorView(myView);
////                        myView.setMediaController(vidControl);
//                    }
//                }
//            });



    public void openFile(long filePicker){
        filePickPointer = filePicker;
        Log.d(tag, filePicker+"");
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
       // intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("video/*");
        //intent.putExtra("pointer", filePicker);

        startActivityForResult(intent, REQUEST_CODE);
        //activityResultLauncher.launch(intent);
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data){

        if (requestcode == REQUEST_CODE && resultcode == Activity.RESULT_OK){

            if (data!= null){

                Uri uri = data.getData();
//                String decodedUri = decodePath(uri.toString());
                File sourceFile = new File(uri.toString());
                String absolutePath = sourceFile.getAbsolutePath();
//
//                String filename = sourceFile.getName();
//                File destFile = new File (getCacheDir(), "my_file.mp4");
//                Log.d("The cache directory is: ", getCacheDir().toString());
//
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    Log.d("The write method is running? ","yes");
//                    try (InputStream in = Files.newInputStream(sourceFile.toPath());
//                         OutputStream out = Files.newOutputStream(destFile.toPath())) {
//                        byte[] buffer = new byte[1024];
//                        int length;
//                        while ((length = in.read(buffer)) > 0) {
//                            out.write(buffer, 0, length);
//                        }
//                    } catch (IOException e) {
//                        Log.d(tag,"Exception found: "+e.toString());
//                    }
//                }
//                Log.d("File size is: ", destFile.length()+"");
//                String absolutePath = destFile.getAbsolutePath();
//                URL url = null;
//                try {
//                    url = destFile.toURI().toURL();
//                } catch (MalformedURLException e) {
//                    Log.d(tag, "Run time exception when creating url");
//                    throw new RuntimeException(e);
//                }
//
//                //Log.d("The decoded Url is: ", decodedUrl);
//               // String encoded_url = URLEncoder.encode(url.toString(), StandardCharsets.UTF_8);
//                Log.d("The absolute path is: ", absolutePath);
//                Log.d("The URI is: ", uri.toString());
//                Log.d("The URL is: ", url.toString());
//                //int flag = Intent.FLAG_GRANT_READ_URI_PERMISSION;
//                //getContentResolver().takePersistableUriPermission(convertedPath, flag);
//
////                ParcelFileDescriptor parcelFileDescriptor = null;
////                try {
////                    parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r", null);
////                } catch (FileNotFoundException e) {
////                    throw new RuntimeException(e);
////                }
////                FileInputStream inputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
//                //Log.d(tag, uri.getPath()+" "+ uri);
//                String decodedAbsPath = "file:"+decodePath(absolutePath);
//
                filePathReceived(filePickPointer, absolutePath);
            }


        }
    }

    public void setScreenOn(boolean isOn){
        if (isOn){
            runOnUiThread(() -> getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON));
        } else {
            runOnUiThread(() -> getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON));
        }

    }

    public String decodePath(String path){
        String decodedPath = "";
        try {
             decodedPath = URLDecoder.decode(path.toString(), "UTF-8");
            return decodedPath;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


}
