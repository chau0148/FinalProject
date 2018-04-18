package com.example.amychau.multiplechoicetest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieSaved extends Activity {
    private String title_request;
    public ProgressBar progress;
    public TextView movie_title;
    public TextView movie_actors;
    public TextView movie_description;
    public TextView movie_rating;
    public TextView movie_genre;
    public TextView movie_runtime;
    public ImageView movie_image;
    public Button movie_delete_button;
    public ListView movie_list;
    public ArrayList<String> movie_array = new ArrayList<>();
    protected static final String ACTIVITY_NAME = "MovieDetails";
    private static String TABLE_NAME = "Movie_History";
    //private static SQLiteDatabase movie_database;
    private Cursor cursor;

    /* @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {



     }
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_saved_layout);
        title_request = getIntent().getStringExtra("title");
        //String url = ("http://www.omdbapi.com/?t=" + title_request + "&apikey=8e3e4f5e&r=xml");
        //String posterUrl = ("http://img.omdbapi.com/?i="+id+"&h=600&apikey=8e3e4f5e");
        //MovieDetails.MovieQuery mq = new MovieDetails.MovieQuery();
        //mq.execute(url);

        progress = (ProgressBar) findViewById(R.id.movie_progress);

        progress.setVisibility(View.VISIBLE);

        movie_title = (TextView) findViewById(R.id.movie_title);
        movie_actors = (TextView) findViewById(R.id.movie_actors);
        movie_description = (TextView) findViewById(R.id.movie_description);
        movie_rating = (TextView) findViewById(R.id.movie_rating);
        movie_runtime = (TextView) findViewById(R.id.movie_length);
        movie_image = (ImageView) findViewById(R.id.movie_image);
        movie_genre = (TextView) findViewById(R.id.movie_genre);
        movie_delete_button = (Button) findViewById(R.id.movie_delete_button);
        progress = (ProgressBar)findViewById(R.id.movie_progress);
        //final MovieAdapter adapter = new MovieAdapter(this);
        final ContentValues values = new ContentValues();

       /* Context context = getApplicationContext();

        final MovieDatabaseHelper temp_database = new MovieDatabaseHelper(context);
        movie_database = temp_database.getWritableDatabase();
        String query = ("SELECT * FROM " + MovieDatabaseHelper.TABLE_NAME + " WHERE TITLE = " + title_request);*/
        MovieQuery mq = new MovieQuery();
        mq.execute(title_request);
        progress.setVisibility(View.INVISIBLE);

        movie_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = ("DELETE FROM " + MovieDatabaseHelper.TABLE_NAME + " WHERE TITLE = '" + title_request + "';");
                MovieList.movie_database.execSQL(query);
                MovieList.movie_list_array.remove(title_request);
                finish();
            }
        });
    }


     class MovieQuery extends AsyncTask<String, Integer, String> {
        String title;
        String actors;
        String length;
        String description;
        String rating;
        String genre;
        String poster_image_name;
        String poster_image_location_remote;
        String poster_image_location_local;
        String id;
        Bitmap poster_image;
        Bitmap test;

        @Override
        protected String doInBackground(String... strings) {
            Context context = getApplicationContext();

            final MovieDatabaseHelper temp_database = new MovieDatabaseHelper(context);
            //movie_database = temp_database.getWritableDatabase();
            String query = ("SELECT * FROM " + MovieDatabaseHelper.TABLE_NAME + " WHERE TITLE = '" + strings[0]+"'");
            cursor = MovieList.movie_database.rawQuery(query,null);
            //cursor= movie_database.rawQuery(query,null);
            cursor.moveToPosition(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ID));
            title = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_TITLE));
            publishProgress(17);
            actors = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ACTORS));
            publishProgress(33);
            length = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_RUNTIME));
            publishProgress(50);
            description = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_DESCRIPTION));
            publishProgress(65);
            rating = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_RATING));
            publishProgress(82);
            genre = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_GENRE));
            publishProgress(100);
            id = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ID));
            byte[] bitmapdata=cursor.getBlob(cursor.getColumnIndex(MovieDatabaseHelper.KEY_POSTER));

            publishProgress(100);

            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
            poster_image=bitmap;
            return null;
        }

        @Override
        public void onProgressUpdate(Integer... value) {
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(value[0]);
        }

        @Override
        public void onPostExecute(String value) {
            movie_title.setText(movie_title.getText() + title);
            movie_actors.setText(movie_actors.getText() + actors);
            movie_description.setText(movie_description.getText() + description);
            movie_genre.setText(movie_genre.getText() + genre);
            movie_rating.setText(movie_rating.getText() + rating);
            movie_runtime.setText(movie_runtime.getText() + length);
            movie_image.setImageBitmap(poster_image);
            //movie_save_button.setText("delete");
            progress.setVisibility(View.INVISIBLE);

        }


    }
}