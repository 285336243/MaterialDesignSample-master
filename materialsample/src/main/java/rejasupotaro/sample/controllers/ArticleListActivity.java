package rejasupotaro.sample.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.sample.R;
import rejasupotaro.sample.adapters.ArticleListAdapter;
import rejasupotaro.sample.adapters.DrawerListAdapter;
import rejasupotaro.sample.data.model.Article;
import rejasupotaro.sample.listeners.OnRecyclerViewItemClickListener;
import com.antonioleiva.recyclerviewextensions.GridLayoutManager;

public class ArticleListActivity extends Activity {

    private ArticleListAdapter adapter;

    private ActionBarDrawerToggle drawerToggle;

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @InjectView(R.id.left_drawer)
    ListView drawerListView;

    @InjectView(R.id.list)
    RecyclerView articleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.inject(this);
        setupDrawer();
        setupViews();
    }

    private void setupDrawer() {
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        String[] drawerItems = getResources().getStringArray(R.array.drawer_items);
        drawerListView.setAdapter(new DrawerListAdapter(
                this,
                drawerItems
        ));

        getActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.hamenu,
                R.string.drawer_open,
                R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void setupViews() {
        List<Article> articleList = Article.createDummyData();
        adapter = new ArticleListAdapter(articleList);

        articleListView.setLayoutManager(new GridLayoutManager(this));
        articleListView.setHasFixedSize(true);
        articleListView.setItemAnimator(new DefaultItemAnimator());
        articleListView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<Article>() {
            @Override
            public void onItemClick(View view, Article article) {
//                openDetailActivity(article, view);
//                startActivity(new Intent(ArticleListActivity.this,ArticleDetailActivity.class));
                Intent intent = ArticleDetailActivity.createIntent(ArticleListActivity.this, article);
                startActivity(intent);
            }
        });
    }

    private void openDetailActivity(Article article, View view) {
        startActivity(ArticleDetailActivity.createIntent(this, article),
                ArticleDetailActivity.createOption(this, view));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

