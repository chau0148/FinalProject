package com.example.amychau.multiplechoicetest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieList extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "MovieList";
    static SQLiteDatabase movie_database;
    static int counter =0;
    Button movie_search_button;
    ListView movie_list;
    EditText movie_search_text;
    TextView movie_title;
    boolean isPhone;
    static ArrayList<String> movie_list_array = new ArrayList<>();
    Cursor cursor;
    View movie_frame_layout;
    final ContentValues returnedValues = new ContentValues();
    String newMovie;
    MovieAdapter adapter;
    int ratingCounter;

    protected void onStart() {
        super.onStart();
        adapter = new MovieAdapter(this);
        movie_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_layout);

        Context context = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.movie_toolbar);
        setSupportActionBar(toolbar);
        String greeting=getString(R.string.movie_greeting);
        Toast.makeText(getApplicationContext(),greeting, Toast.LENGTH_LONG).show();

        isPhone = (findViewById(R.id.movie_frame) == null);
        movie_search_button = (Button) findViewById(R.id.movie_search_button);
        movie_list = (ListView) findViewById(R.id.movie_list);
        movie_search_text = (EditText) findViewById(R.id.movie_search);
        movie_frame_layout = (View) findViewById(R.id.movie_frame_layout);
        movie_title = (TextView) findViewById(R.id.movie_title);
        final ContentValues values = new ContentValues();

        //movie_list.setText("");
        final  MovieDatabaseHelper temp = new MovieDatabaseHelper(context);
        movie_database = temp.getWritableDatabase();
        String query = "SELECT DISTINCT * FROM "+ MovieDatabaseHelper.TABLE_NAME;

      /*  cursor= chat_database.query(ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},
                null, null, null, null, null);*/
        cursor= movie_database.rawQuery(query,null);
        cursor.moveToPosition(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ID));

        if (cursor.moveToFirst() && counter==0) { //loads list from database
            do{
                String movie = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_TITLE));
                movie_list_array.add(movie);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
            counter++;
        }

        movie_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //this restarts the process of getCount() & getView()
                Bundle searchQuery = new Bundle();
                //searchQuery.pu ("title",movie_search_text.getText().toString()); // load the query into the bundle
                Intent makeFrame = new Intent(MovieList.this, MovieDetails.class);
                makeFrame.putExtra("title",movie_search_text.getText().toString());
                startActivityForResult(makeFrame,99);

              /* old code, probably still need it
                chat_messages.add(chat_text.getText().toString());
                values.put(ChatDatabaseHelper.KEY_MESSAGE, chat_text.getText().toString());
                chat_database.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
                adapter.notifyDataSetChanged();
                chat_text.setText("");*/
            }
        });

        movie_list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { //view view	View: The view within the AdapterView that was clicked (this will be a view provided by the adapter)
                //position	int: The position of the view in the adapter.
                //long: The row id of the item that was clicked.
                Bundle infoPassed = new Bundle();
                cursor.moveToPosition(i);
                //infoPassed.putString(ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE);
                // long test = cursor.getLong(cursor.getColumnIndex(temp.KEY_ID));
                infoPassed.putString("title",(String)adapterView.getItemAtPosition(i));
                //infoPassed.putString("message",cursor.getString(cursor.getColumnIndex(temp.KEY_MESSAGE)));

                if (!isPhone) { //for tablet
                    MovieFragment mf = new MovieFragment();
                    mf.setArguments(infoPassed);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.movie_frame_layout, mf);
                    //ft.addToBackStack("Any string here"); //back button will unto transactionft.commit(); //put the fragment on the screen
                } else { // for phone: step 4 MessageDetails

                    Intent makeFrame = new Intent(MovieList.this, MovieSaved.class); //changed from MovieDetails
                    makeFrame.putExtras(infoPassed);
                    startActivityForResult(makeFrame, 55);
                }
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String snack = getString(R.string.movie_snack);
        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_one :
                Log.d("toolbar",snack);

                Snackbar.make(this.findViewById(R.id.action_one), snack, Snackbar.LENGTH_LONG).show();
                break;
            case R.id.action_two:
                Log.d("toolbar","option 2 selected = B");
                AlertDialog.Builder builder = new AlertDialog.Builder(MovieList.this);
                cursor.moveToPosition(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ID));
                ratingCounter=0;
                Double rating;
                Double ratingTotal = 0d;
                if (cursor.moveToFirst()) { //loads list from database
                    do{
                        rating = Double.valueOf(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_RATING)));
                        ratingTotal= ratingTotal +rating;
                        ratingCounter++;
                        cursor.moveToNext();
                    }while (!cursor.isAfterLast());
                }
                Double ratingAverage = ratingTotal/ratingCounter;

                builder.setTitle(getString(R.string.movie_rating));
                builder.setMessage(ratingAverage.toString());
                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;
//            case R.id.action_three:
//                Log.d("toolbar","option 3 selected = C");
//                AlertDialog.Builder builder2 = new AlertDialog.Builder(MovieList.this);
//                builder2.setTitle(R.string.movie_box_title);
//                // Add the buttons
//                LayoutInflater inflater = MovieList.this.getLayoutInflater();
//                View dialog2 = inflater.inflate(R.layout.movie_custom_layout, null);
//                final EditText edit = dialog2.findViewById(R.id.editText);
//                builder2.setView(dialog2)
//                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //EditText edit = (EditText) findViewById(R.id.editText);
//                                //greeting = edit.getText().toString();
//                            }
//                        });
//                builder2.show();
//                break;
            case R.id.action_about:
                Toast.makeText(getApplicationContext(),getString(R.string.movie_version), Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        movie_database.close();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
       //moved adapter here

        if (requestCode == 55) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                MovieFragment messagefrag = new MovieFragment();
                messagefrag.setArguments(data.getBundleExtra("savedMovie"));
                FragmentManager fragman = getFragmentManager();
                FragmentTransaction fragtran = fragman.beginTransaction();
                fragtran.replace(R.id.movie_frame_layout, messagefrag);
                fragtran.commit(); //put the fragment on the screen*/
            }
        }
        if (requestCode ==99 && resultCode==99){
            Bundle returnedMovie = new Bundle();
            returnedMovie = data.getExtras();
            newMovie = returnedMovie.getString("savedMovie");
           // newMovie = returnedMovie.getBundle("savedMovie").toString();
            movie_list_array.add(newMovie);
            returnedValues.put(MovieDatabaseHelper.KEY_TITLE, newMovie);

            //movie_database.insert(MovieDatabaseHelper.TABLE_NAME, null, returnedValues);
            //adapter.notifyDataSetChanged();
            //movie_title.setText("");
        }
    }

    private class MovieAdapter extends ArrayAdapter<String> {

        public MovieAdapter(Context ctx) {
            super(ctx, 0);
        }

        // returns the number of rows that will be in your listView
        public int getCount() {
            return movie_list_array.size();
        }

        //returns the item to show in the list at the specified position
        public String getItem(int position) {
            return movie_list_array.get(position);
        }

        //this returns the layout that will be positioned at the specified row in the list
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = MovieList.this.getLayoutInflater();

            // This will recreate your View that you made in the resource file. If the position is
            // an even number (position%2 == 0), then inflate chat_row_incoming, else inflate
            // chat_row_outgoing

            View result = inflater.inflate(R.layout.movie_row, null);

            //From the resulting view, get the TextView which holds the string message

            TextView movie = (TextView) result.findViewById(R.id.movie_row);
            //message.setText(   getItem(position)  ); // get the string at position
            //might have to seperate this next part
            movie.setText("");
            movie.setText(getItem(position));
            return result;
        }

        // This is the database id of the item at position. For now, we aren’t using SQL, so just return the number: position.
        public long getItemId(int position) {
            return position;

        }

    }

}