$(document).ready(function(){
  $('.sortable-list').sortable();

  /*
  //Here is my attempt to show the link when the r is 0
  //Also, we need to add to the link all the URL parameters (id and r)
  var field = 'r';
  var url = window.location.href;
  console.log(url.indexOf('&' + field + '=') != '0');
  if(url.indexOf('&' + field + '=') != '0') {
    document.getElementById('TA').style.display = '';
    event.preventDefault();
  }*/

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


  function loadPage(user){
    if(user){
      $('#fname').text(user.firstName);
      $('#lname').text(user.lastName);
      $('#gtid').text(user.userID);
    }
  }

  function loadCourseList(list){
    if(list && list.length>0){
      $.each(list, function(key, course) {
        $("#RegisteredCourses").append("<tr id='" + course.courseID + "'><<td>" + course.program+ "</td> <td>" + course.courseID + "</td><td>" + course.name + "</td><td style='text-align: center'> <button class='btn btn-primary' onclick='courseDetails(" + course.courseID+")'>+</button></td></tr>");
      });
    }
  }

});

function getSem(sel) {
  var sem = document.getElementById('semester');
  sem.innerHTML = sel.value;
}

function courseDetails(x) {
  var rowData = document.getElementById("course"+x);
  //Course details AJAX goes here.................
}