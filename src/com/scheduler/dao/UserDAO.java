package com.scheduler.dao;

import com.scheduler.dbmodel.StudentPrefs;
import com.scheduler.dbmodel.User;
import java.util.List;

/**
 * Created by vlanderson on 4/13/15.
 */
public interface UserDAO {

    public User getUserByCredentials(User user);

    public List<StudentPrefs> getAllStudents();

    User getUser(String studentID);

    public StudentPrefs getUserDetails(String id);
}
