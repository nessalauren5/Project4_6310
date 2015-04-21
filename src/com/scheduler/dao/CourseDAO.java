package com.scheduler.dao;

import com.scheduler.dbmodel.CourseModel;
import com.scheduler.dbmodel.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlanderson on 4/13/15.
 */
public interface CourseDAO {
    public boolean setPriorityForUser(User u, CourseModel c);
    public ArrayList<CourseModel> getCourseListForUser(User u);

    public List<CourseModel> getAllCourses();
}
