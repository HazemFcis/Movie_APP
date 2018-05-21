package com.example.hazem.train1r;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class details extends AppCompatActivity {
    Intent intent;
    TextView t_title,t_rate,t_overveiw;
    ImageView imageView ;
    movies movie;
    RequestQueue requestQueue2,requestQueue3;
    List<String>trailer_key,list_rev;
    Adapter3 adapter3;
    Adapter_rev adapter_rev;
    RecyclerView recyclerView,recyclerView2;
    ArrayList<String>list_tra;
    List<movies>load;
    int k=0;
    Button button;
    FavoriteDbHelper favoriteDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue2= Volley.newRequestQueue(this);
        requestQueue3=Volley.newRequestQueue(this);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setTitle("MovieDetail");
        trailer_key=new ArrayList<>();
        set_date();
        String o="https://api.themoviedb.org/3/movie/";
        o+=getIntent().getExtras().getString("get_id");
        o+="/videos?api_key=0f7070c7d96558edcb399c197671d0d2";
        list_tra=new ArrayList<>();
        load=new ArrayList<>();
        volley(o);
        list_rev=new ArrayList<>();
        String oo="https://api.themoviedb.org/3/movie/";
        oo+=getIntent().getExtras().getString("get_id");
        oo+="/reviews?api_key=0f7070c7d96558edcb399c197671d0d2";
        volley2(oo);
        button=findViewById(R.id.TV_rate);
        favoriteDbHelper =new FavoriteDbHelper(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ok=true;
                if (favoriteDbHelper.Exists((getIntent().getExtras().getString("get-tit")))){
                    ok=false;
                }
                if (ok){
                    favoriteDbHelper.addFavorite(movie);
                }
            }
        });
        //recyclerView.setLayoutParams(new ViewGroup.LayoutParams());
    }
    public  void set_date()
    {
        t_title=(TextView)findViewById(R.id.TV_Title);
        t_rate=(TextView)findViewById(R.id.bb);
        t_overveiw=(TextView)findViewById(R.id.TV_overveiw);
        imageView=(ImageView)findViewById(R.id.IM_DE);
        load=new ArrayList<>();
        String S=getIntent().getExtras().getString("get-tit");
        t_title.setText(S);
        String s=getIntent().getExtras().getString("get-rdate");
        //String substr=s.substring(0,4);
        t_rate.setText(getIntent().getExtras().getString("get-rate"));
        Picasso.with(this).load(getIntent().getExtras().getString("get-im")).into(imageView);
        t_overveiw.setText(getIntent().getExtras().getString("get-over"));
        //Toast.makeText(this, S.toString(), Toast.LENGTH_SHORT).show();
        movie=new movies();
        movie.setId(getIntent().getExtras().getString("get_id"));
        movie.setTitle(S);
        movie.setImage(getIntent().getExtras().getString("get-im"));
        movie.setRating(getIntent().getExtras().getString("get-rate"));
        //movie.setRelease_date(s);
        movie.setOverview(getIntent().getExtras().getString("get-over"));
    }
    public  void volley (String url){
        final JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(com.android.volley.Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {
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
                        trailer_key.add(jsonObject.getString("key"));
                        k++;
                        list_tra.add("Tralair  "+String.valueOf(k)+jsonObject.getString("key"));
                     // Toast.makeText(details.this, String.valueOf(k), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
               // linearLayoutManager.smoothScrollToPosition(recyclerView, null, 0);
                if(list_tra.size()!=0) {
                    //Toast.makeText(getApplicationContext(),list_tra.size()+"@", Toast.LENGTH_SHORT).show();
                    adapter3 = new Adapter3(getApplicationContext(),list_tra);
                    recyclerView.setAdapter(adapter3);
                    adapter3.notifyDataSetChanged();
                    //recyclerView.smoothScrollToPosition(0);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ta","Error");
            }
        });
        requestQueue2.add(jsonObjectRequest);}
    public  void volley2 (String url){
        final JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(com.android.volley.Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {
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

                        list_rev.add("Author :   "+jsonObject.getString("author")+"\n"+jsonObject.getString("content")+"\n");
                      // Toast.makeText(getApplicationContext(),jsonObject.getString("author"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
                recyclerView2.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
                // linearLayoutManager.smoothScrollToPosition(recyclerView, null, 0);
                if(list_rev.size()!=0) {
                    //Toast.makeText(getApplicationContext(),list_rev.size()+"@", Toast.LENGTH_SHORT).show();
                    adapter_rev = new Adapter_rev(getApplicationContext(),list_rev);
                    recyclerView2.setAdapter(adapter_rev);
                    adapter_rev.notifyDataSetChanged();
                    //recyclerView.smoothScrollToPosition(0);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ta","Error");
            }
        });
        requestQueue3.add(jsonObjectRequest);}


}

