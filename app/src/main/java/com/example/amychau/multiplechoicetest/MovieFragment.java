package com.example.amychau.multiplechoicetest;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Jim Cassidy on 2018-03-30.
 */

public class MovieFragment extends Fragment {

    static int counter = 0;


    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        Bundle infoPassed = getArguments();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View fragment_to = inflater.inflate(R.layout.movie_fragment, null);

        TextView title =(TextView) fragment_to.findViewById(R.id.movie_title);
        TextView actors = (TextView) fragment_to.findViewById(R.id.movie_actors);
        TextView description = (TextView) fragment_to.findViewById(R.id.movie_description);
        TextView rating = (TextView) fragment_to.findViewById(R.id.movie_rating);
        TextView runtime = (TextView) fragment_to.findViewById(R.id.movie_length);
        ImageView image = (ImageView) fragment_to.findViewById(R.id.movie_image);
        TextView genre = (TextView) fragment_to.findViewById(R.id.movie_genre);

        Button delete = (Button) fragment_to.findViewById(R.id.movie_delete_button);
        View framelayout = (View) fragment_to.findViewById(R.id.movie_frame_layout);

        //query the database
        String query = ("SELECT * FROM " + MovieDatabaseHelper.TABLE_NAME + " WHERE TITLE = '" + getArguments().getString("title")+"'");
        Cursor cursor = MovieList.movie_database.rawQuery(query,null);

        cursor.moveToPosition(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ID));
        title.setText(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_TITLE)));

        actors.setText(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ACTORS)));

        runtime.setText(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_RUNTIME)));

        description.setText(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_DESCRIPTION)));

        rating.setText(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_RATING)));

        genre.setText(cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_GENRE)));

        //id = cursor.getString(cursor.getColumnIndex(MovieDatabaseHelper.KEY_ID));
        byte[] bitmapdata=cursor.getBlob(cursor.getColumnIndex(MovieDatabaseHelper.KEY_POSTER));
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        image.setImageBitmap(bitmap);

//        title.setText(getArguments().getString("title")); //getArguments returns from intents
//        actors.setText(getArguments().getString("actors"));
//        description.setText(getArguments().getString("description"));
//        rating.setText(getArguments().getString("rating"));
//        runtime.setText(getArguments().getString("runtime"));
//        //image.getDrawingCache(getArguments().getByteArray("poster"));
//        genre.setText(getArguments().getString("genre"));

        final long messageID = getArguments().getLong("id");
        final Intent dataToDelete = new Intent();
        dataToDelete.putExtra("delete",messageID);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view){
                String query = ("DELETE FROM " + MovieDatabaseHelper.TABLE_NAME + " WHERE TITLE = '" + getArguments().getString("title")+"';");
                MovieList.movie_database.execSQL(query);
                MovieList.movie_list_array.remove(getArguments().getString("title"));

                //finish();
            }
        });
        return fragment_to;
    } // end of onCreate
}
