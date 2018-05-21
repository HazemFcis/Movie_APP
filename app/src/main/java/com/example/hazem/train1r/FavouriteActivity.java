package com.example.hazem.train1r;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.hazem.train1r.ContentProv;
import com.android.volley.RequestQueue;
import java.util.ArrayList;
import java.util.List;
public class FavouriteActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    tadapter tadapter;
    FavoriteDbHelper favoriteDbHelper;
    List<movies>listfav;
    public static int index = -1;
    public static int top = -1;
    LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutManager = new LinearLayoutManager(this);
        setContentView(R.layout.activity_favourite);
        favoriteDbHelper=new FavoriteDbHelper(this);
        recyclerView =(RecyclerView)findViewById(R.id.rv_movie2);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        listfav=new ArrayList<>();
        Cursor cu = this.getContentResolver().query(ContentProv.M_uri,new String[] {"movieid","title","userrating","posterpath","overview"},null,null,null);
        while (cu.moveToNext()) {
            movies movies=new movies();
            movies.setId(cu.getString(0));
            movies.setTitle(cu.getString(1));
            movies.setOverview(cu.getString(4));
            movies.setRating(cu.getString(2));
            movies.setImage(cu.getString(3));
            listfav.add(movies);
        }
        cu.close();
        tadapter=new tadapter(this, listfav);
        recyclerView.setAdapter(tadapter);
        tadapter.notifyDataSetChanged();
    }
    @Override
    public void onPause()
    {
        super.onPause();
        //read current recyclerview position
        index = mLayoutManager.findFirstVisibleItemPosition();
        View v = recyclerView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - recyclerView.getPaddingTop());
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //set recyclerview position
        if(index != -1)
        {
            mLayoutManager.scrollToPositionWithOffset( index, top);
        }
    }
}