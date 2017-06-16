package app.service;

import app.model.Github;
import app.model.Random;

import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface GithubService {
    //String SERVICE_ENDPOINT = "https://en.wikipedia.org/w/api.php?format=json&action=query&generator=random&prop=revisions&rvprop=content";
    //String SERVICE_ENDPOINT = "https://en.wikipedia.org/w/api.php?format=json&action=query&list=random&rnlimit=1";

    String SERVICE_ENDPOINT = "https://en.wikipedia.org/";

    //String SERVICE_ENDPOINT = "http://echo.jsontest.com/key/value/one/two";
//    @GET("/format/{format}/query/{query}")
//    Observable<Github> getUser(@Path("json") String login);

//    @GET("/title/{title}")
//    Observable<Github> getUser(@Query("title") String login);

    //@GET("")
    //Observable<Github> getRandom();

    @GET("w/api.php?format=json&action=query&list=random&rnlimit=1&rnnamespace=0&cmtitle=Category:Physics")
    Observable<Github> getUser();

    @GET("w/api.php?action=parse&format=json")
    Observable<Github> getObject(@Query("page") String titles);


}
