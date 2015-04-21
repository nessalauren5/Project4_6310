package com.scheduler.service;

import com.scheduler.dao.CourseDAO;
import com.scheduler.dao.UserDAO;
import com.scheduler.dbmodel.*;
import com.scheduler.math.Solver;
import com.scheduler.model.Course;
import com.scheduler.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vlanderson on 4/13/15.
 */
@Service
public class BusinessService {

    @Autowired
    private UserDAO userdao;

    @Autowired
    private CourseDAO coursedao;


    public ResultObject<User> authUser(User user){
        ResultObject<User> response = new ResultObject<User>();
        User u = userdao.getUserByCredentials(user);

        if(u==null || !user.getPassword().equals(user.getPassword())){
            response.setStatus("failed");
        }
        else {
            response.setStatus("success");
            response.setObject(u);
        }
        return response;
    }

    public ResultObject<CourseModel> setCourseForUser(User user, CourseModel c){
        ResultObject<CourseModel> response = new ResultObject<CourseModel>();

        if(coursedao.setPriorityForUser(user, c)){
            response.setStatus("success");
        }
        else{
            response.setStatus("failed");
        }
        response.setObject(c);
        return response;
    }

    public ResultObject<ArrayList<CourseModel>> getCourseList(User user){
        ResultObject<ArrayList<CourseModel>> response = new ResultObject<ArrayList<CourseModel>>();
        response.setObject(coursedao.getCourseListForUser(user));
        if(response.getObject()!=null){
            response.setStatus("success");
        }
        else{
            response.setStatus("failed");
        }
        return response;
    }

    public ResultObject< ArrayList<Course>> generateRecommendationFor(String studentID) {

        List<StudentPrefs> stuList = userdao.getAllStudents();
        List<CourseModel> courseList = coursedao.getAllCourses();


        HashMap<String,Student> students = new HashMap<>();
        HashMap<String,Course> courses = new HashMap<>();

        for(StudentPrefs p : stuList){
            Student s = new Student();
            s.setCredits(p.getCredits());
            s.setStudentID(String.valueOf(p.getStudentID()));
            s.setStudentPreferences(p.getCourseStr());

            students.put(s.getStudentID(),s);
        }
        for(CourseModel cm : courseList){
            Course c = new Course(String.valueOf(cm.getCourseID()), cm.getName());

            courses.put(c.getCourseID(),c);
        }

        Solver solv = new Solver(students,courses,studentID);
        solv.optimize();

        String[] coursesTaken = solv.createResult();
        ArrayList<Course> takenList = new ArrayList<>();

        for(String s : coursesTaken){
            Course c = courses.get(s);
            takenList.add(c);
        }


        ResultObject<ArrayList<Course>> response = new ResultObject<ArrayList<Course>>();
        response.setObject(takenList);
        return response;
    }
}
