package com.example.amychau.multiplechoicetest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetails extends Activity {
    private String title_request;
    public ProgressBar progress;
    public TextView movie_title;
    public TextView movie_actors ;
    public TextView movie_description;
    public TextView movie_rating;
    public TextView movie_genre;
    public TextView movie_runtime;
    public ImageView movie_image;
    public Button movie_save_button;
    public ListView movie_list;
    public ArrayList<String> movie_array = new ArrayList<>();
    protected static final String ACTIVITY_NAME="MovieDetails";
    private  String TABLE_NAME  ="Movie_History";
    private static SQLiteDatabase movie_database;
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_layout);
        title_request = getIntent().getStringExtra("title");
        String url = ("http://www.omdbapi.com/?t=" + title_request + "&apikey=8e3e4f5e&r=xml");
        //String posterUrl = ("http://img.omdbapi.com/?i="+id+"&h=600&apikey=8e3e4f5e");
        MovieQuery mq = new MovieQuery();
        mq.execute(url);

        progress = (ProgressBar) findViewById(R.id.movie_progress);

        progress.setVisibility(View.VISIBLE);

        movie_title = (TextView) findViewById(R.id.movie_title);
        movie_actors = (TextView) findViewById(R.id.movie_actors);
        movie_description = (TextView) findViewById(R.id.movie_description);
        movie_rating = (TextView) findViewById(R.id.movie_rating);
        movie_runtime = (TextView) findViewById(R.id.movie_length);
        movie_image = (ImageView) findViewById(R.id.movie_image);
        movie_genre = (TextView) findViewById(R.id.movie_genre);
        movie_save_button = (Button) findViewById(R.id.movie_save_button);

        //final MovieAdapter adapter = new MovieAdapter(this);
        final ContentValues values = new ContentValues();


        Context context = getApplicationContext();


        final MovieDatabaseHelper temp_database = new MovieDatabaseHelper(context);
        movie_database = temp_database.getWritableDatabase();
        //String query = "SELECT * FROM "+MovieDatabaseHelper.TABLE_NAME;

        movie_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view){
                Intent savedMovie = new Intent();
                values.put("title", movie_title.getText().toString());
                values.put("actors",movie_actors.getText().toString());
                values.put("description", movie_description.getText().toString());
                values.put("rating",movie_rating.getText().toString());
                values.put("genre",movie_genre.getText().toString());
                values.put("runtime",movie_runtime.getText().toString());

                BitmapDrawable mImage = (BitmapDrawable) movie_image.getDrawable();
                Bitmap bitImage = mImage.getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitImage.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] bArray = bos.toByteArray();
                values.put("poster",bArray);
                movie_database.insert(MovieDatabaseHelper.TABLE_NAME, null, values);
                savedMovie.putExtra("savedMovie",movie_title.getText().toString());
                setResult(99,savedMovie);

                //movie_array.add(movie_title.getText().toString());


                /*adapter.notifyDataSetChanged();
                movie_title.setText("");*/
                Toast.makeText(getApplicationContext(),"Movie Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
   /* private class MovieAdapter extends ArrayAdapter<String> {

        public MovieAdapter(Context ctx) {
            super(ctx, 0);
        }

        // returns the number of rows that will be in your listView
        public int getCount() {
            return movie_array.size();
        }

        //returns the item to show in the list at the specified position
        public String getItem(int position) {
            return movie_array.get(position);
        }

        //this returns the layout that will be positioned at the specified row in the list
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = MovieDetails.this.getLayoutInflater();

            // This will recreate your View that you made in the resource file. If the position is
            // an even number (position%2 == 0), then inflate chat_row_incoming, else inflate
            // chat_row_outgoing

            View result = inflater.inflate(R.layout.movie_row, null);


            //From the resulting view, get the TextView which holds the string message

            TextView message = (TextView) result.findViewById(R.id.movie_title);
            //message.setText(   getItem(position)  ); // get the string at position
            //might have to seperate this next part
            message.setText(getItem(position));
            return result;
        }

        // This is the database id of the item at position. For now, we aren’t using SQL, so just return the number: position.
        public long getItemId(int position) {
            return position;

        }

    }*/

    public class MovieQuery extends AsyncTask<String, Integer, String> {
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
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(3000 /* milliseconds */);
                conn.setConnectTimeout(3000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream stream = conn.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(stream, null);

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG){
                        continue;
                    }
                    if (parser.getName().equals("movie")) {
                        title = parser.getAttributeValue(null, "title");
                        publishProgress(17);
                        actors = parser.getAttributeValue(null, "actors");
                        publishProgress(33);
                        length = parser.getAttributeValue(null, "runtime");
                        publishProgress(50);
                        description = parser.getAttributeValue(null, "plot");
                        publishProgress(65);
                        rating = parser.getAttributeValue(null, "imdbRating");
                        publishProgress(82);
                        genre = parser.getAttributeValue(null, "genre");
                        publishProgress(100);
                        id = parser.getAttributeValue(null, "imdbID");
                        poster_image_location_remote = ("http://img.omdbapi.com/?i="+id+"&h=600&apikey=8e3e4f5e");
                        poster_image_name = poster_image_location_remote.substring(poster_image_location_remote.lastIndexOf('/') + 1);
                        //poster_image_location_local=("/data/data/com.example.jimcassidy.movieapp/files/"+poster_image_name);
                        publishProgress(100);
                        //FileInputStream fis = new FileInputStream (new File(poster_image_name));
                        if (fileExistance(poster_image_name)) {
                            Log.i(ACTIVITY_NAME, "image found locally");
                            FileInputStream fstream = null;
                            File poster_file = getBaseContext().getFileStreamPath(poster_image_name);
                            try {
                                fstream = new FileInputStream(poster_file);
                            } catch (FileNotFoundException e) {
                                Log.i(ACTIVITY_NAME, "file not found error in image parsing");
                                e.printStackTrace();
                            }
                            poster_image = BitmapFactory.decodeStream(fstream);
                        } else {
                            Log.i(ACTIVITY_NAME, "image downloaded");
                            poster_image = getImage(poster_image_location_remote);
                            FileOutputStream outputStream = openFileOutput(poster_image_name, Context.MODE_PRIVATE);
                            poster_image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();

                        }
                    }
                }
                progress.setVisibility(View.INVISIBLE);
            } catch (Exception exc) {
                Log.i(ACTIVITY_NAME, "catch error");
                exc.getMessage();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Integer... value) {
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(value[0]);

        }

        @Override
        public void onPostExecute(String value) {
            movie_title.setText(movie_title.getText()+title);
            movie_actors.setText(movie_actors.getText()+ actors);
            movie_description.setText(movie_description.getText()+ description);
            movie_genre.setText(movie_genre.getText()+ genre);
            movie_rating.setText(movie_rating.getText()+rating);
            movie_runtime.setText(movie_runtime.getText()+length);
            movie_image.setImageBitmap(poster_image);
            movie_save_button.setText("save");
            progress.setVisibility(View.INVISIBLE);

        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                    //return MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(url.toURI().toString()));
                    //return BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(url.toURI().toString())));
                    //return test;
                } else
                    Log.i(ACTIVITY_NAME, "else on response code ");
                    return null;
            } catch (Exception e) {
                Log.i(ACTIVITY_NAME, ""+e);
                return null;
            } finally {
                if (connection != null) {
                    Log.i(ACTIVITY_NAME, "connection severed ");
                    connection.disconnect();
                }
            }
        }

        public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }
}

