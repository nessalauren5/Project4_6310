$(document).ready(function(){
  $('.sortable-list').sortable();

  $.QueryString = (function(a) {
    if (a == "") return {};
    var b = {};
    for (var i = 0; i < a.length; ++i)
    {
      var p=a[i].split('=');
      if (p.length != 2) continue;
      b[p[0]] = decodeURIComponent(p[1].replace(/\+/g, " "));
    }
    return b;
  })(window.location.search.substr(1).split('&'));


  var field = 'r';
  var url = window.location.href;
  var r =  $.QueryString["r"];
  if(r == 'true') {
    document.getElementById('TA').style.display = '';
  }


  var $loading = $('#loadingDiv').hide();
  $(document)
      .ajaxStart(function () {
        $loading.show();
      })
      .ajaxStop(function () {
        $loading.hide();
      });





  var id = $.QueryString["id"];

  var creds = {};
  var usr = $('#username').val();
  var pwd = $('#password').val();

  console.log("logging in: " + creds);
  var uri = "http://localhost:8080/services/";


  $.ajax({
    url : uri + "user/" + id,
    type : "GET",
    contentType: "application/json",
    dataType:"json",
    success : function(data) {
      var TA = data.result.isTA ? "true":"false";
      loadPage(data.result);
      document.getElementById("TA").href="landingPageProfessor.html?id="+data.result.userID+"&r="+TA;
    },
    error: function (request, error) {
      alert(" AJAX ERROR ");
    }
  });

  $.ajax({
    url : uri + "courses/priorityList/" + id,
    type : "GET",
    contentType: "application/json",
    dataType:"json",
    success : function(data) {
      loadCoursePriority(data.result);
      getCourseList(data.result);
    },
    error: function (request, error) {
      alert(" Can't do because: " + error);
    }
  });


  function getCourseList(preferences){

    $.ajax({
      url : uri + "courses",
      type : "GET",
      contentType: "application/json",
      dataType:"json",
      success : function(data) {
        loadCourseList(data.result, preferences);
      },
      error: function (request, error) {
        alert(" Can't do because: " + error);
      }
    });
  }



  function loadPage(user){
    if(user){
      $('#fname').text(user.firstName);
      $('#lname').text(user.lastName);
      $('#gtid').text(user.userID);
      $('#hours').text(user.credits);
      $('#courseload').val(user.numCourses);
    }
  }

  function loadCourseList(list, preferences){
    if(list && list.length>0){
      $.each(list, function(key, course) {
        found = false;
        if(preferences!=null && preferences.length>0) {
          $.each(preferences, function(key, prefcourse) {
            if(prefcourse.courseID == course.courseID){
              found = true;
            }

          });
        }
       if(!found){
          $("#RegisteredCourses").append("<tr id='" + course.courseID + "'><<td>" + course.program + "</td> <td id='course'>" + course.courseID + "</td><td id='name'>" + course.name + "</td><td style='text-align: center'> <button class='btn btn-primary' onclick='addClass(" + course.courseID + ")'>+</button></td></tr>");
        }
        else{
         $("#RegisteredCourses").append("<tr id='" + course.courseID + "'><<td>" + course.program + "</td> <td id='course'>" + course.courseID + "</td><td id='name'>" + course.name + "</td><td style='text-align: center'> <button class='btn btn-primary' onclick='removeClass(" + course.courseID + ")'>-</button></td></tr>");
       }
      });
    }
  }

  function loadCoursePriority(list){
    if(list && list.length>0){
      $.each(list, function(key, course) {
        $("#priorityList").append("<li class='sortable-item'> <span id='" + course.courseID + "' class='priority' >" + course.courseID + ":" +  course.name + "</span> </li>");

      });
    }
  }

});

function getRecommendedSchedule() {
  var uri = "http://localhost:8080/services/";

  //
  if ($('#preferences').is(":visible"))
  {
    //Call to retrieve list
    var courses = [];

    $('.priority').each(function(key, course) {
      courses.push(course.id);
    });

    var json = JSON.stringify({courseStr:courses.join(",")});
    console.log(json);

    $.ajax({
      url : uri + "recc/" +  $.QueryString["id"] + "/" + $('#courseload').val(),
      type : "POST",
      data: json,
      contentType: "application/json",
      dataType:"json",
      success : function(data) {
        loadSuggestions(data.result);
        console.log(data.result);
      },
      error: function (request, error) {
        alert(" Can't do because: " + error);
      }
    });
  }
  else
  {
    $('#toggleBtn').text("View Advised Schedule");
   $("#suggestList").html("");
    $('#preferences').show();
    $('#suggestions').hide();
  }
}

function loadSuggestions(courses){

  if(courses && courses.length>0){
    $.each(courses, function(key, course) {
      $("#courseList").append("<li class='sortable-item'> <span id='" + course.courseID + "' class='priority' >" + course.courseID + ":" +  course.name + "</span> </li>");

    });
    $('#toggleBtn').text("Change Course Preferences");
    $('#preferences').hide();
    $('#suggestions').show();
  }
}
function addClass(x) {
  var rowData = $('tr#'+x);
  var course = rowData.find('td#course').text();
  var name = rowData.find('td#name').text();

  if(!checkClassDuplicate(course)) //add a check to see if class is already taken and AND it here.
  {
    rowData.find('button').text("-");
    rowData.find('button').attr("onclick","removeClass("+course+")");

    $("#priorityList").append("<li class='sortable-item'> <span id='" + course + "' class='priority' >" + course + ":" +  name + "</span> </li>");
  }
}
function removeClass(x) {
  var rowData = $('tr#'+x);
  var course = rowData.find('td#course').text();
  $('#priorityList').find('span#'+x).parent().remove();
  rowData.find('button').text("+");
  rowData.find('button').attr("onclick","addClass("+course+")");
}
function checkClassDuplicate(x) {
  var found = false;
  $('.priority').each(function(key, course) {
    if(course.id == x) {
      found = true;
    }
  });

  return found;
}

function getSem(sel) {
  var sem = document.getElementById('semester');
  sem.innerHTML = sel.value;
}