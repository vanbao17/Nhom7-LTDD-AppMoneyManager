package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsActivity extends AppCompatActivity implements NewsAdapter.OnItemClickListener{
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsread);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        callApiGetNews();
    }
    public void onItemClick(Article newsItem) {
        // Xử lý khi một item được click, chẳng hạn chuyển sang một activity khác
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("link", newsItem.getUrl()); // Truyền dữ liệu nếu cần thiết
        startActivity(intent);
    }
    private void callApiGetNews() {
        ApiService.apiService.getlistNews("bitcoin","ba82c530e6204bce85e719f66c6cb316").enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    NewsResponse newsResponse = (NewsResponse) response.body();
                    if (newsResponse != null) {
                        List<Article> articles = newsResponse.getArticles();
                        NewsAdapter newsAdapter = new NewsAdapter(articles,new NewsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Article newsItem) {
                                // Xử lý khi một item được click, chẳng hạn chuyển sang một activity khác
                                Intent intent = new Intent(NewsActivity.this, WebViewActivity.class);
                                intent.putExtra("link", newsItem.getUrl());
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(newsAdapter);
                    }

                } else {
                    // Xử lý khi có lỗi từ API
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(NewsActivity.this,"failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
