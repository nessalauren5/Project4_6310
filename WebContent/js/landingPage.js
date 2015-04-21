$(document).ready(function(){
  $('.sortable-list').sortable();

  var $loading = $('#loadingDiv').hide();
  $(document)
      .ajaxStart(function () {
        $loading.show();
      })
      .ajaxStop(function () {
        $loading.hide();
      });

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
  })(window.location.search.substr(1).split('&'))



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
      loadPage(data.result);
    },
    error: function (request, error) {
      alert(" Can't do because: " + error);
    }
  });

  $.ajax({
    url : uri + "courses",
    type : "GET",
    contentType: "application/json",
    dataType:"json",
    success : function(data) {
      loadCourseList(data.result);
    },
    error: function (request, error) {
      alert(" Can't do because: " + error);
    }
  });

  $.ajax({
    url : uri + "courses/priorityList/" + id,
    type : "GET",
    contentType: "application/json",
    dataType:"json",
    success : function(data) {
      loadCoursePriority(data.result);
    },
    error: function (request, error) {
      alert(" Can't do because: " + error);
    }
  });


  function loadPage(user){
    if(user){
      $('#fname').text(user.firstName);
      $('#lname').text(user.lastName);
      $('#gtid').text(user.userID);
      $('#hours').text(user.credits);
      $('#courseload').val(user.numCourses);
    }
  }

  function loadCourseList(list){
    if(list && list.length>0){
      $.each(list, function(key, course) {
        $("#RegisteredCourses").append("<tr id='" + course.courseID + "'><<td>" + course.program+ "</td> <td>" + course.courseID + "</td><td>" + course.name + "</td><td style='text-align: center'> <button class='btn btn-primary' onclick='addClass(" + course.courseID+")'>+</button></td></tr>");
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
  if ((document.getElementById("recommendedSchedule").style.display == 'none'))
  {
    //Call to retrieve list
    var courses = {};

    $('.priority').each(function(key, course) {
      courses.push(course.id);
    });

    var json = JSON.stringify({courseList:courses});
    console.log(json);

    $.ajax({
      url : uri + "recc/" +  $.QueryString["id"] + "/" + $('#courseload').val(),
      type : "POST",
      data: json,
      contentType: "application/json",
      dataType:"json",
      success : function(data) {
        console.log(data.result);
      },
      error: function (request, error) {
        alert(" Can't do because: " + error);
      }
    });

    //Populate list

    //List Toggle
    document.getElementById("recommendedSchedule").style.display = ''
    event.preventDefault()
  }
  else
  {
    //List Toggle
    document.getElementById("recommendedSchedule").style.display = 'none';
    event.preventDefault()
  }
}

function addClass(x) {
  var rowData = document.getElementById("course"+x);
  var selectedText = rowData.cells[0].innerHTML + ': ' + rowData.cells[1].innerHTML;

  if(checkClassDuplicate(selectedText)) //add a check to see if class is already taken and AND it here.
  {
    var list = document.getElementById('priorityList');
    var entry = document.createElement('li');
    entry.setAttribute('class', 'sortable-item ui-sortable-handle')
    var newSpan = document.createElement('span');
    entry.appendChild(newSpan);
    newSpan.innerHTML = selectedText;
    list.appendChild(entry);
  }
}

function checkClassDuplicate(x) {
  var list = document.getElementById('priorityList');
  var listItem = list.getElementsByTagName("span");
  var newNums   = [],
      duplicate = [];

  for (var i=0; i < listItem.length; i++) {
    var num = listItem[i].innerHTML;
    if (num === x) {
      alert("" + x + " is a duplicate class");
      return false;
    }
  }
  return true;
}

function getSem(sel) {
  var sem = document.getElementById('semester');
  sem.innerHTML = sel.value;
}