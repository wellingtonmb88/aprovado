package com.wellingtonmb88.aprovado.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.wellingtonmb88.aprovado.R;

public class AboutActivity extends AppCompatActivity {

    private Toolbar mToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        LinearLayout toolbar = (LinearLayout) findViewById(R.id.toolbar);
        mToolbarLayout = (Toolbar) toolbar.findViewById(R.id.toolbar_layout);
        mToolbarLayout.setTitleTextColor(getResources().getColor(R.color.white));
        WebView wv = (WebView) findViewById(R.id.webView);

        wv.loadUrl(getString(R.string.url_activity_about));

        setSupportActionBar(mToolbarLayout);

        if( getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    } 

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_rate) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String uriString =
                    "market://details?id=com.wellingtonmb88.aprovado";
            intent.setData(Uri.parse(uriString));
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}