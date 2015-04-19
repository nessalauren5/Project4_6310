package com.scheduler.dao;

import com.scheduler.dbmodel.User;
/**
 * Created by vlanderson on 4/13/15.
 */
public interface UserDAO {

    public User getUserByCredentials(User user);
}
