package app.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("realm-students.realm")
                //.schemaVersion(MigrationData.VERSION)
                //.migration(new MigrationData())
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration( realmConfiguration );
    }
}
