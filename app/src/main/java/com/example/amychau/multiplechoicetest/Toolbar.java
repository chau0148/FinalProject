package com.example.amychau.multiplechoicetest;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Toolbar is a fragment class that will show up on each of the frames.
 * This toolbar will navigate to the other activities and show the Help Menu
 */
public class Toolbar extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean onCreateOptionsMenu(Menu m){
        getActivity().getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view = inflater.inflate(R.layout.custom_dialog, null);
                builder.setView(view)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("User clicked", "OK");
                            }
                        });
                break;
        }
        return true;
    }
}
