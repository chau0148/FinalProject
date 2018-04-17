package com.example.amychau.multiplechoicetest;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Toolbar to show at the top of each layout/activity
 */
public class Toolbar extends AppCompatActivity {

    public android.support.v7.widget.Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return super.onCreateOptionsMenu(m);
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        switch(mi.getItemId()){
            case R.id.patient:
                Log.d("Patient Form", "Selected");
                //Go to Patient Form Activity
                break;
            case R.id.movie:
                Log.d("Movie Information", "Selected");
                //Go to Movie Information Activity
                break;
            case R.id.transpo:
                Log.d("OC Transpo Information", "Selected");
                //Go to OC Transpo Activity
                break;
            case R.id.help:
                Log.d("Help Box", "Selected");
                AlertDialog.Builder builder = new AlertDialog.Builder(Toolbar.this);
                LayoutInflater inflater = Toolbar.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.custom_dialog, null);
                builder.setView(view)
                        .setPositiveButton("OK", (dialogInterface, i) -> Log.d("User clicked", "OK"));
                break;
        }
        return true;
    }
}
