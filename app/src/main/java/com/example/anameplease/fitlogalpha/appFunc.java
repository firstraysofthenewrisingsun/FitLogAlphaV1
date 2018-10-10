package com.example.anameplease.fitlogalpha;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class appFunc {



    public appFunc(){

    }

    public static String lowerCalc(double a, Context context){
        Double answ1 =(a*1.09703) + 14.2546;

        if(!(answ1== null)){

            Toast toast = Toast.makeText(context.getApplicationContext(), "Please enter some data", Toast.LENGTH_LONG);
            toast.show();

        }

        String result = Double.toString(answ1);


        return result;
    }

    public static String upperCalc(double b, Context context){
        Double answ = (b*1.1307) + 0.6998;

        if(!(answ== null)){

            Toast toast = Toast.makeText(context.getApplicationContext(), "Please enter some data", Toast.LENGTH_LONG);
            toast.show();

        }

        String results = Double.toString(answ);


        return results;

    }

    public String readFile (File file) {

        String content = null;
        try {
            // Read the entire contents of sample.txt
            content = FileUtils.readFileToString(file, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }


    public void appendFile(File filename, String data){

        try {
            FileOutputStream fileinput = new FileOutputStream(filename, true);
            PrintStream printstream = new PrintStream(fileinput);
            printstream.print(data+"\n");
            fileinput.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void writeToSDFile(String n, String d, String nts, String fleNme, File root){

        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal




        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

        File dir = new File (root.getAbsolutePath());
        dir.mkdirs();
        File file = new File(dir, fleNme);

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(n);
            pw.println(d);
            pw.println(nts);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeToSDFile(String n, String d, String fleNme, File root){

        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal




        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

        File dir = new File (root.getAbsolutePath());
        dir.mkdirs();
        File file = new File(dir, fleNme);

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(n);
            pw.println(d);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }







}
