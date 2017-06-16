package app.adapter;

import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.activity.MainActivity;
import app.activity.WikiPageActivity;
import app.domain.PageObject;
import app.model.Github;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

import com.example.githubdemo.app.R;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    List<PageObject> mItems;

    //List<Github> mItems;
    Realm realm;

    public CardAdapter() {
        super();
        //mItems = new ArrayList<Github>();
        mItems = new ArrayList<PageObject>();
        realm = Realm.getDefaultInstance();
    }

    //public void addData(Github github) {
    public void addData (PageObject po){
        mItems.add(po);
       // mItems.add(github);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        PageObject po = mItems.get(i);
        viewHolder.login.setText(po.getTitle());
       // viewHolder.repos.setText("repos: " + github.getPublicRepos());
       // viewHolder.blog.setText("blog: " + github.getBlog());
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView login;
        public TextView repos;
        public TextView blog;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
           // itemView.setOnLongClickListener(this);
            login = (TextView) itemView.findViewById(R.id.login);
            repos = (TextView) itemView.findViewById(R.id.repos);
            blog = (TextView) itemView.findViewById(R.id.blog);
        }




        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext())
                    .setTitle("Artigo: " + mItems.get(getAdapterPosition()).getTitle())
                    .setPositiveButton("LER", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(itemView.getContext(),WikiPageActivity.class);
                            i.putExtra("STRING_I_NEED",mItems.get(getAdapterPosition()).getHtmlText());
                            itemView.getContext().startActivity(i);

                           // Toast.makeText(itemView.getContext(), "LER", Toast.LENGTH_SHORT).show();

                        }
                    })
                .setNegativeButton("EXCLUIR", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    RealmResults<PageObject> rr = realm.where( PageObject.class ).equalTo("title",mItems.get(getAdapterPosition()).getTitle()).findAll();
                    PageObject po = rr.first();
                    //Log.d("DEGLONG",rr.get(getAdapterPosition()).getId()+"");
                    realm.beginTransaction();
                    po.removeFromRealm();
                    mItems.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    realm.commitTransaction();
                    //realm.close();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
//            final Button btpositive = (Button)dialog.findViewById(AlertDialog.BUTTON_POSITIVE);
//            LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) btpositive.getLayoutParams();
//            positiveButtonLL.gravity = Gravity.CENTER;
//            btpositive.setLayoutParams(positiveButtonLL);

//            Intent i = new Intent(view.getContext(),WikiPageActivity.class);
//            i.putExtra("STRING_I_NEED",mItems.get(getAdapterPosition()).getHtmlText());
//            view.getContext().startActivity(i);
        }


//
//        @Override
//        public boolean onLongClick(View view) {
//            RealmResults<PageObject> rr = realm.where( PageObject.class ).equalTo("title",mItems.get(getAdapterPosition()).getTitle()).findAll();
//            PageObject po = rr.get(0);
//            //Log.d("DEGLONG",rr.get(getAdapterPosition()).getId()+"");
//            realm.beginTransaction();
//            po.removeFromRealm();
//            mItems.remove(getAdapterPosition());
//            realm.commitTransaction();
//            return false;
//        }
    }




}