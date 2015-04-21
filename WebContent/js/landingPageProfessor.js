$(document).ready(function(){
  $('.sortable-list').sortable();
});

function getRecommendedSchedule() {
  if ((document.getElementById("recommendedSchedule").style.display == 'none'))
  {
    //Call to retrieve list
    var courses = {};

    $.each('.priority',function(key, course) {
      courses.push(course.text);
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