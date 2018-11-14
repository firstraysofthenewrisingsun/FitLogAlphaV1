package com.example.anameplease.fitlogalpha;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    List<Notes> getAll();

    @Query("SELECT * FROM notes WHERE info_name =:selectedName")
    Notes searchByName(String selectedName);

    @Query("SELECT * FROM notes WHERE info_id =:selectedID")
    Notes searchByID(Integer selectedID);

    @Query("SELECT * FROM notes WHERE info_id =:checkedID")
    List<Notes> getAllByID(Integer checkedID);

    @Update
    void updateNote (Notes item);

    @Insert
    void insert(Notes item);

    @Delete
    void delete(Notes item);
}
