package com.scheduler.dao;

import com.scheduler.dbmodel.Course;
import com.scheduler.dbmodel.User;

import java.util.ArrayList;

/**
 * Created by vlanderson on 4/13/15.
 */
public interface CourseDAO {
    public boolean setPriorityForUser(User u, Course c);
    public ArrayList<Course> getCourseListForUser(User u);
}
