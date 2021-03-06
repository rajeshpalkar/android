package com.example.rajeshpalkar.a07;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

//import android.app.Fragment;

public class FragmentDetails extends Fragment {

    HashMap movie;
    private int total =0,sizeList;
    MovieData movieData;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ShareActionProvider shareActionProvider;

    private ChangeTitleToMovie changeTitleToMovie;

    public FragmentDetails() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentDetails newInstance(HashMap<String,?> movie) {
        FragmentDetails fragment = new FragmentDetails();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        changeTitleToMovie = (ChangeTitleToMovie) getContext();

      /*  if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/

        if(savedInstanceState!=null)
        {
            total = savedInstanceState.getInt("Total");
        }


      //  MovieData m = new MovieData();
      //  movie = (HashMap<String,?>) getArguments().getSerializable(ARG_PARAM1);


    }

    public void onSavedInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("Total",total);
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.detailed_fragment_menu,menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");

        String dataToSend = "Movie: " + (String) movie.get("name") + "\nDescription" + (String) movie.get("description");

        intentShare.putExtra(Intent.EXTRA_TEXT,dataToSend);
        shareActionProvider.setShareIntent(intentShare);

        super.onCreateOptionsMenu(menu,inflater);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        movieData = new MovieData();
        sizeList = movieData.getSize();

        if(getArguments() != null)
            movie = (HashMap<String, ?>) getArguments().getSerializable(ARG_PARAM1);

        System.out.println("movie::"+movie);

        String mv =  (String) movie.get("name");
        TextView t2 = (TextView) rootView.findViewById(R.id.textView7);
        t2.setText(mv);
        changeTitleToMovie.changeTitle(mv);

        String dir =  (String) movie.get("director");
        TextView t = (TextView) rootView.findViewById(R.id.textView5);
        t.setText(dir);

        String cast =  (String) movie.get("stars");
        TextView t1 = (TextView) rootView.findViewById(R.id.textView6);
        t1.setText(cast);

        String des =  (String) movie.get("description");
        TextView t3 = (TextView) rootView.findViewById(R.id.textView8);
        t3.setText("\nDescription : "+des);


        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView4);
        Picasso.with(getActivity()).load((String) movie.get("url")).into(imageView);
        imageView.setCropToPadding(true);


        RatingBar rb = (RatingBar) rootView.findViewById(R.id.ratingBar);
        String movieRating = (String) movie.get("rating");
        float halfMovieRating = Float.valueOf(movieRating)/2;
        rb.setRating(halfMovieRating);


        return rootView;
    }

    public interface ChangeTitleToMovie {
        public void changeTitle(String title);
    }

}
