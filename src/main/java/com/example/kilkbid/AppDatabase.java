package com.example.kilkbid;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "kilkbid_db"
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // Insert test admin account
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        UserDao userDao = INSTANCE.userDao();
                                        User admin = new User();
                                        admin.name = "Admin";
                                        admin.email = "admin@gmail.com";
                                        admin.passwordHash = "123"; // For testing only; hash in real apps
                                        admin.isAdmin = true;
                                        userDao.insertUser(admin);
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}