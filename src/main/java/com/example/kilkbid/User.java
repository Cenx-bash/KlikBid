package com.example.kilkbid;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String email;
    public String passwordHash;
    public boolean isAdmin; // NEW
}
