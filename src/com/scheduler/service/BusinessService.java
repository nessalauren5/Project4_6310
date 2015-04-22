package com.scheduler.service;

import com.scheduler.dao.CourseDAO;
import com.scheduler.dao.UserDAO;
import com.scheduler.dbmodel.*;
import com.scheduler.math.Solver;
import com.scheduler.model.Course;
import com.scheduler.model.Student;
import javassist.bytecode.stackmap.TypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vlanderson on 4/13/15.
 */
@Service
public class BusinessService {

    @Autowired
    private UserDAO userdao;

    @Autowired
    private CourseDAO coursedao;

    private static final Logger log = Logger.getLogger( TypeData.ClassName.class.getName() );

    public ResultObject<User> authUser(User user){
        ResultObject<User> response = new ResultObject<User>();
        log.log(Level.INFO, user.getUsername() + " is attemping to login");
        try {
            User u = userdao.getUserByCredentials(user);
            if(u==null || !user.getPassword().equals(user.getPassword())) {
                response.setStatus("failed");
                response.addError("Password is incorrect.");
                log.log(Level.SEVERE, "Password is incorrect.");
            }
            else {
                response.setStatus("success");
                response.setObject(u);
                log.log(Level.INFO, u.getUsertype() + " " +  u.getUserID() + " logged in successfully");
            }
        }catch(NoResultException e){
            response.setStatus("failed");
            response.addError("User not found.");
                log.log(Level.SEVERE, "User not found.");
        }

        return response;
    }

    public ResultObject<CourseModel> setCoursesForUser(User user, CourseModel c){
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

    public ResultObject<List<CourseModel>> getCourseList(){
        log.log(Level.INFO, "Retrieving course list");
        ResultObject<List<CourseModel>> response = new ResultObject<List<CourseModel>>();
        try {
            List<CourseModel> list = coursedao.getAllCourses();
            response.setObject(list);
            if (response.getObject() != null) {
                response.setStatus("success");
            } else {
                response.setStatus("failed");
            }
        }catch(NoResultException e){
            response.setStatus("failed");
            response.addError("No results returned.");
            log.log(Level.SEVERE, "No course results found.");
        }
        return response;
    }

    public ResultObject<List<CourseModel>> getPriorityList(String studentID){
        log.log(Level.INFO, "Retrieving priority course list for " + studentID);
        StudentInfoView sp = userdao.getUserDetails(studentID);
        String [] courses = sp.getCourseStr().split(",");

        ResultObject<List<CourseModel>> response = new ResultObject<List<CourseModel>>();

        if(courses==null || courses.length == 0){
            response.setStatus("SUCCESS");
            response.setMessage("No Courses found for user.");
        }
        else {
            for(int i =0; i<courses.length; i++){
                log.log(Level.INFO,"Searching for course: " + courses[i]);
            }
            try {
                List<CourseModel> list = coursedao.getCourseListForUser(courses);
                log.log(Level.INFO, "Found " + list.size() + " courses");
                        response.setObject(list);
                if (response.getObject() != null) {
                    response.setStatus("success");
                } else {
                    response.setStatus("failed");
                }
            } catch (NoResultException e) {
                response.setStatus("failed");
                response.addError("No results returned.");
                log.log(Level.SEVERE, "No course results found.");
            }
        }
        return response;
    }

    public ResultObject< ArrayList<Course>> generateRecommendationFor(String studentID, String numCourses, StudentInfoView sp) {

        ResultObject<ArrayList<Course>> response = new ResultObject<ArrayList<Course>>();


        if(numCourses.isEmpty()){
            numCourses = "2";
        }
        log.log(Level.INFO, "User " + studentID + " is requesting course recommendation." + sp.getCourseStr());
        List<StudentInfoView> stuList = userdao.getAllStudents();
        try{
            userdao.setCoursePreferences(studentID,sp.getCourseStr());
        }catch(Exception e){
            log.log(Level.SEVERE, "Error updating preferences" + e.getMessage());
        }

        for(StudentInfoView pf :stuList){
            if(pf.getUserIDStr().equals(studentID)){
                pf.setNumCourses(Integer.parseInt(numCourses));
            }
        }

        List<CourseModel> courseList = coursedao.getAllCourses();


        HashMap<String,Student> students = new HashMap<>();
        HashMap<String,Course> courses = new HashMap<>();

        for(StudentInfoView p : stuList){
            Student s = new Student();
            s.setCredits(p.getCredits());
            s.setStudentID(String.valueOf(p.getUserID()));
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

        if(coursesTaken==null || coursesTaken.length==0){
            response.setStatus("failed");
            response.setMessage("Course(s) unavailable for you in the next semester. Please try again with different preferencs.");
        }
        else {
            for (String s : coursesTaken) {
                Course c = courses.get(s);
                takenList.add(c);
            }
        }



        response.setObject(takenList);
        return response;
    }

    public ResultObject<StudentInfoView> getUser(String studentID) {
        log.log(Level.INFO," finding user details: " + studentID );
        ResultObject<StudentInfoView> response;
        try{
            StudentInfoView u = userdao.getUserDetails(studentID);
             response = new ResultObject<StudentInfoView>();
            response.setObject(u);
        }catch(NoResultException e){
           response = new ResultObject<StudentInfoView>();
            response.addError("No user found with that ID.");
        }
        return response;
    }
}
