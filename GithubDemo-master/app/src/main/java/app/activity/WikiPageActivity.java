package app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.example.githubdemo.app.R;

import app.service.GithubService;

public class WikiPageActivity extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_page);
        String newString;

        wv = (WebView)findViewById(R.id.wvID);

        Bundle extras = getIntent().getExtras();

        if(extras==null){
            newString = null;
        }
        else{
            newString= extras.getString("STRING_I_NEED");
        }

        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDomStorageEnabled(true);

        //wv.loadUrl("https://en.wikipedia.org/wiki/Brazil");
         wv.loadDataWithBaseURL(GithubService.SERVICE_ENDPOINT, newString, "text/html", "UTF-8", "");
        wv.getSettings().setLoadWithOverviewMode(true);
       // wv.getSettings().setUseWideViewPort(true);



        Log.d("AADSDAS",newString);
    }


}
