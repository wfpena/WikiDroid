package app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import app.Data;
import app.adapter.CardAdapter;
import app.domain.PageObject;
import app.model.Github;
import app.service.GithubService;
import app.service.ServiceFactory;
import com.example.githubdemo.app.R;
import com.google.gson.GsonBuilder;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends ActionBarActivity {

    Realm realm;
    RealmResults<PageObject> rr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        rr = realm.where(PageObject.class).findAll();

        /**
         * Set up Android CardView/RecycleView
         */
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CardAdapter mCardAdapter = new CardAdapter();
        mRecyclerView.setAdapter(mCardAdapter);
        realm.beginTransaction();
        rr = realm.where(PageObject.class).findAll();
        //Log.d("DEG",rr.size()+"");
        //rr.clear();
        realm.commitTransaction();
        //Log.d("DEG",rr.size()+"");
        for (int i=0; i<rr.size(); i++) {
            //realm.
            //Log.d("DEG",rr.get(0).getTitle());
            mCardAdapter.addData(rr.get(i));
        }

        realm.close();
        /**
         * START: button set up
         */
        Button bClear = (Button) findViewById(R.id.button_clear);
        Button bFetch = (Button) findViewById(R.id.button_fetch);
        bClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardAdapter.clear();
                realm.beginTransaction();
                rr = realm.where(PageObject.class).findAll();
                rr.clear();
                realm.commitTransaction();
            }
        });

        bFetch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final GithubService service = ServiceFactory.createRetrofitService(GithubService.class, GithubService.SERVICE_ENDPOINT);
                for(String login : Data.githubList) {
                    service.getUser()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Github>() {
                            @Override
                            public final void onCompleted() {
                                // do nothing
                            }

                            @Override
                            public final void onError(Throwable e) {
                                Log.e("GithubDemo", e.getMessage());
                            }

                            @Override
                            public final void onNext(Github response) {
                                service.getObject(response.getQuery().getRandom().get(0).getTitle()).subscribeOn(Schedulers.newThread())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Subscriber<Github>(){
                                            @Override
                                            public final void onCompleted() {
                                                // do nothing
                                            }

                                            @Override
                                            public final void onError(Throwable e) {
                                                Log.e("GithubDemo", e.getMessage());
                                            }

                                            @Override
                                            public final void onNext(Github response) {
                                                rr = realm.where(PageObject.class).findAll();
                                                PageObject po = new PageObject();
                                                po.setId(getNextKey());
                                                Log.d("GithubDemo22", getNextKey() + "");
                                                po.setHtmlText(response.parse.text.text);
                                                po.setTitle(response.parse.getTitle());
                                                realm.beginTransaction();
                                                realm.copyToRealmOrUpdate(po);
                                                realm.commitTransaction();
                                                mCardAdapter.addData(po);



                                                //mCardAdapter.addData(response);

                                            }

                                                   });

                                                Log.d("GithubDemo", response.getQuery().getRandom().size() + "");
                                //mCardAdapter.addData(response);


                            }
                        });
                }


//                realm.beginTransaction();
//                rr = realm.where(PageObject.class).findAll();
//                realm.commitTransaction();
//                for (int i=0; i<rr.size(); i++) {
//
//                    mCardAdapter.addData(rr.get(1));
//                }
            }
        });
        /**
         * END: button set up
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public int getNextKey()
    {
        realm.beginTransaction();
        rr = realm.where(PageObject.class).findAll();
        realm.commitTransaction();
        if(rr.size() < 1)
            return 0;
        else
            return realm.where(PageObject.class).max("id").intValue() + 1;

    }
}
