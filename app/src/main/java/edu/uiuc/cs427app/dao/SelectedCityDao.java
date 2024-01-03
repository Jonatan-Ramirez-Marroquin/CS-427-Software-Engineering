package edu.uiuc.cs427app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.uiuc.cs427app.data.SelectedCity;

/**
 * Data Access Object (DAO) for the SelectedCity table.
 * This interface defines all the operations that can be performed on the SelectedCity table.
 */
@Dao
public interface SelectedCityDao {

    /**
     * Inserts a new SelectedCity record into the database.
     *
     * @param city The SelectedCity object to be inserted.
     * @return The row ID of the newly inserted record. Returns -1 if the operation failed.
     */
    @Insert
    long insert(SelectedCity city);

    /**
     * Deletes a SelectedCity record based on the provided city ID.
     *
     * @param cityId The unique ID of the SelectedCity record to be deleted.
     */
    @Query("DELETE FROM SelectedCity WHERE uid = :cityId")
    void deleteByCityId(int cityId);

    /**
     * Retrieves a list of all SelectedCity records associated with a specific user.
     *
     * @param username The username for which to retrieve the selected cities.
     * @return A list of SelectedCity records associated with the given username.
     */
    @Query("SELECT * FROM SelectedCity WHERE username = (:username)")
    List<SelectedCity> getAll(String username);
}

