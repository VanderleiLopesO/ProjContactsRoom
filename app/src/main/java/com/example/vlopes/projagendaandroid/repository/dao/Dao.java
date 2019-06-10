package com.example.vlopes.projagendaandroid.repository.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.vlopes.projagendaandroid.entity.Contact;

import java.util.List;

@android.arch.persistence.room.Dao
public abstract class Dao {

    @Insert
    public abstract long insertContact(Contact contact);

    @Delete
    public abstract int deleteContact(Contact contact);

    @Update
    public abstract int updateContact(Contact contact);

    @Query("SELECT * FROM Contact")
    public abstract List<Contact> getContacts();

}
