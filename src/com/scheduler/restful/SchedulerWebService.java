package com.scheduler.restful;

import com.scheduler.dbmodel.ResultObject;
import com.scheduler.dbmodel.StudentPrefs;
import com.scheduler.dbmodel.User;
import com.scheduler.dbmodel.CourseModel;
import com.scheduler.model.Course;
import com.scheduler.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/")
@Controller
public class SchedulerWebService {

	@Autowired
	private BusinessService bs;

	@GET
	public Response printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello world, welcome to our scheduler web app!");
		return Response.ok(model,MediaType.APPLICATION_XHTML_XML_TYPE).build();
	}

	@GET
	@Produces("application/json")
	@Path("test")
	public Response testService() {
		return Response.ok().build();
	}

	@POST
	@Produces("application/json")
	@Path("recc")
	public Response getRecommendation(){
		ResultObject<ArrayList<Course>> resp = bs.generateRecommendationFor("53064629");
		return Response.ok(resp, MediaType.APPLICATION_JSON).build();
//		@PathParam(value = "studentID") String studentID
	}

	@POST
	@Produces("application/json")
	@Path("/user/courses")
	public Response availCourses(User user){
		return Response.ok().build();
	}

	@POST
	@Produces("application/json")
	@Path("login")
	public Response login(User user){

		ResultObject<User> res = bs.authUser(user);
		return Response.ok(res, MediaType.APPLICATION_JSON).build();
	}

//	@POST
//	@Produces("application/json")
//	@Path("courses")
//	public Response getCourseList(User u){
//
//		ResultObject<ArrayList<Course>> res = bs.getCourseList(u);
//		return Response.ok(res, MediaType.APPLICATION_JSON).build();
//	}

//	@POST
//	@Produces("application/json")
//	@Path("courses/schedule")
//	public Response setCoursePriority(User u, Course c){
//
//		ResultObject<Course> res = bs.setCourseForUser(u,c);
//		return Response.ok(res, MediaType.APPLICATION_JSON).build();
//	}


}