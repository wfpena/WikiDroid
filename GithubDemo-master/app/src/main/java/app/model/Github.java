package app.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Github {

    public class Parse{
        @SerializedName("title")
        String title;

        @SerializedName("text")
        public TextWiki text;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }

    @SerializedName("query")
    private Query query;

    @SerializedName("parse")
    public Parse parse;





    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }


}

