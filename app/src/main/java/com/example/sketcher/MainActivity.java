package com.example.sketcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static DrawingFragment drawingFragment = null;
    // Color available, default = red
    int[] color_arr = {R.color.colorRed, R.color.colorGreen,
        R.color.colorBlue,R.color.colorPurple};
    float[] size_arr = {15,30,60};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.drawingFragment = new DrawingFragment();
        drawingFragment.setContainerActivity(this);

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.draw_layout,drawingFragment);

        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void clearDrawing(View v){
        DrawingView dv = drawingFragment.getDrawingView();
        dv.startNew();
    }

    public void changeBrushSize(View v){
        DrawingView dv = drawingFragment.getDrawingView();

        int viewID = v.getId();
        if(viewID == findViewById(R.id.button_small).getId()){
            dv.changePaintSize(size_arr[0]);
        }else if(viewID == findViewById(R.id.button_medium).getId()){
            dv.changePaintSize(size_arr[1]);
        }else if(viewID == findViewById(R.id.button_large).getId()){
            dv.changePaintSize(size_arr[2]);
        }
    }
    public void onClickShare(View v){
        ContactsFragment frag = new ContactsFragment();
        frag.setContainerActivity(this);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        transaction.add(R.id.screen_layout,frag);

        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changePaintColor(View v){
        DrawingView dv = drawingFragment.getDrawingView();

        int viewID = v.getId();
        if(viewID == findViewById(R.id.button_red).getId()){
            dv.changePaintColor(color_arr[0]);
        }else if(viewID == findViewById(R.id.button_green).getId()){
            dv.changePaintColor(color_arr[1]);
        }else if(viewID == findViewById(R.id.button_blue).getId()){
            dv.changePaintColor(color_arr[2]);
        }else if(viewID == findViewById(R.id.button_purple).getId()){
            dv.changePaintColor(color_arr[3]);
        }
    }

    public void clickContact(View v){
        ListView listView = (ListView) v.getParent();
        int contactId = listView.getPositionForView(v)+1;

        System.out.println("CONTACT_ID: "+contactId);

        Cursor emails = getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID
                        + " = " + contactId, null, null);

        List<String> emailsList = new ArrayList<>();
        if(emails.moveToNext()) {
            String email = emails
                    .getString(emails.getColumnIndex(
                            ContactsContract.CommonDataKinds.Email.ADDRESS));
            System.out.println(email);
            emailsList.add(email);
            launchMail(email);

        }
        emails.close();

//        launchMail(emailsList);
    }

    /**
     * This function calls intent with email address
     * @param address
     */
    public void launchMail(String address){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("vnd.android.cursor.dir/email");

        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { address });
        System.out.println(BuildConfig.APPLICATION_ID +".provider");
        Uri uri = FileProvider.getUriForFile(this,
                 BuildConfig.APPLICATION_ID + ".provider",
                 new File("/A/B/C.png"));
        intent.putExtra(android.content.Intent.EXTRA_STREAM, uri);

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

}
