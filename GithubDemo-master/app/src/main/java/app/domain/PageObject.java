package app.domain;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

//Objeto que representa a página, para ser armazenada no Banco de Dados do Realm
public class PageObject extends RealmObject {
    @PrimaryKey
    private long id;
    private String title;
    private String htmlText;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }
}
