package com.scheduler.restful;

import com.scheduler.dbmodel.ResultObject;
import com.scheduler.dbmodel.StudentInfoView;
import com.scheduler.dbmodel.User;
import com.scheduler.dbmodel.CourseModel;
import com.scheduler.model.Course;
import com.scheduler.service.BusinessService;
import javassist.bytecode.stackmap.TypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/")
@Controller
public class SchedulerWebService {
	private static final Logger log = Logger.getLogger( TypeData.ClassName.class.getName() );
	@Autowired
	private BusinessService bs;

	@GET
	@Produces("application/json")
	@Path("test")
	public Response testService() {
		return Response.ok().build();
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("recc/{studentID}/{numCourses}")
	public Response getRecommendation(@PathParam(value = "studentID") String studentID, @PathParam(value = "numCourses") String numCourses, StudentInfoView sp){
		ResultObject<ArrayList<Course>> resp = bs.generateRecommendationFor(studentID, numCourses, sp);
		return Response.ok(resp, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Produces("application/json")
	@Path("courses/priorityList/{studentID}")
	public Response getCoursePriorityList(@PathParam(value = "studentID") String studentID){
		ResultObject<List<CourseModel>> resp = bs.getPriorityList(studentID);
		return Response.ok(resp, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Produces("application/json")
	@Path("courses")
	public Response availCourses(){
		ResultObject<List<CourseModel>> res = new ResultObject<>();
		try {
					res = bs.getCourseList();
			return Response.ok(res, MediaType.APPLICATION_JSON).build();
		}catch(Exception e){
			res.setStatus("failed");
			log.log(Level.SEVERE, e.toString());
			return Response.ok(res, MediaType.APPLICATION_JSON).build();
		}
	}
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("user/{studentID}")
	public Response getUser(@PathParam(value = "studentID") String studentID){
		try {
			ResultObject<StudentInfoView> res = bs.getUser(studentID);
			return Response.ok(res, MediaType.APPLICATION_JSON).build();
		}catch(Exception e){
			ResultObject<StudentInfoView> res = new ResultObject<>();
			res.addError("error, user data corrupt.");
			return Response.ok(res, MediaType.APPLICATION_JSON).build();
		}

	}
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("login")
	public Response login(User user){
		try {
			ResultObject<User> res = bs.authUser(user);
			return Response.ok(res, MediaType.APPLICATION_JSON).build();
		}catch(Exception e){
			log.log(Level.SEVERE, e.toString());
			ResultObject<User> res = new ResultObject<>();
			return Response.ok(res, MediaType.APPLICATION_JSON).build();
		}

	}

}