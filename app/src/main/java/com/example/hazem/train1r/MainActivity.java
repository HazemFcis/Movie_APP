package com.example.hazem.train1r;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
     RecyclerView recyclerView ;
     tadapter tadapter;
     List<movies>moviesList;
     RequestQueue requestQueue;
    FavoriteDbHelper favoriteDbHelper;
    private Parcelable savedRecyclerLayoutState;
    private LinearLayoutManager mGridLayoutManager;
    final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private Parcelable mListState = null;
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("POP Movies");
        favoriteDbHelper=new FavoriteDbHelper(this);
        requestQueue= Volley.newRequestQueue(this);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView =(RecyclerView)findViewById(R.id.rv_movie);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        moviesList = new ArrayList<>();
        tadapter=new tadapter(this,moviesList);
        recyclerView.setAdapter(tadapter);
         if(savedRecyclerLayoutState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
           //recyclerView.scrollTo(savedInstanceState.getInt("positionx"),savedInstanceState.getInt("positiony"));
        }
        tadapter.notifyDataSetChanged();

        volley("http://api.themoviedb.org/3/movie/top_rated?api_key=0f7070c7d96558edcb399c197671d0d2");
    }
    public  void volley (String url){
        final JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {
                    jsonArray=response.getJSONArray("results");
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                for (int i =0;i<jsonArray.length();++i)
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String poster_path="https://image.tmdb.org/t/p/w185";
                        poster_path +=jsonObject.getString("poster_path");
                        String title=jsonObject.getString("title");
                        String overview=jsonObject.getString("overview");
                        String vote_average=jsonObject.getString("vote_average");
                        String ral_date=jsonObject.getString("release_date");
                        String ii=jsonObject.getString("id");
                        movies m=new movies();
                        m.setImage(poster_path);
                        m.setOverview(overview);
                        m.setTitle(title);
                        m.setRating(vote_average+"\n"+ral_date);
                        //m.setRelease_date(ral_date);
                        m.setId(ii);
                        moviesList.add(m);
                        tadapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ta","Error");
            }
        });
        requestQueue.add(jsonObjectRequest);}
  @Override
  public boolean onCreateOptionsMenu(Menu menu){
      MenuInflater menuInflater=new MenuInflater(this);
      menuInflater.inflate(R.menu.menu1,menu);
      return  super.onCreateOptionsMenu(menu);
  }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.it_pop){
            moviesList = new ArrayList<>();
            tadapter=new tadapter(this,moviesList);
            recyclerView.setAdapter(tadapter);
            tadapter.notifyDataSetChanged();
            volley("http://api.themoviedb.org/3/movie/popular?api_key=0f7070c7d96558edcb399c197671d0d2");

        }
       if  (item.getItemId()==R.id.it_rated) {
            moviesList = new ArrayList<>();
            tadapter=new tadapter(this,moviesList);
            recyclerView.setAdapter(tadapter);
            tadapter.notifyDataSetChanged();
            volley("http://api.themoviedb.org/3/movie/top_rated?api_key=0f7070c7d96558edcb399c197671d0d2");

       }
        if (item.getItemId()==R.id.ff){
            Intent intent=new Intent(MainActivity.this,FavouriteActivity.class);
          startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT,recyclerView.getLayoutManager().onSaveInstanceState());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
    }
   @Override
   protected void onPause() {
       super.onPause();

       mBundleRecyclerViewState = new Bundle();
       mListState = recyclerView.getLayoutManager().onSaveInstanceState();
       mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, mListState);
   }
   @Override
   public void onConfigurationChanged(Configuration newConfig) {
       super.onConfigurationChanged(newConfig);


       if (mBundleRecyclerViewState != null) {
           new Handler().postDelayed(new Runnable() {

               @Override
               public void run() {
                   mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                   recyclerView.getLayoutManager().onRestoreInstanceState(mListState);

               }
           }, 50);
       }

       // Checks the orientation of the screen
       if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

           gridLayoutManager.setSpanCount(2);

       } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

           gridLayoutManager.setSpanCount(2);

       }
       recyclerView.setLayoutManager(gridLayoutManager);
   }


}
