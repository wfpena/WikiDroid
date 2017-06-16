package app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 25/12/2016.
 */

public class Query {
    @SerializedName("random")
    @Expose
    private List<Random> random = null;

    public List<Random> getRandom() {
        return random;
    }

    public void setRandom(List<Random> random) {
        this.random = random;
    }
}
