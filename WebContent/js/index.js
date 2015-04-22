$(document).ready(function () {
	 $("#submit").click(
	  function (e) {
	  e.preventDefault();
		login();
		
	  }
	); 	
	
	function login(){
	var creds = {};
var usr = $('#username').val();
var pwd = $('#password').val(); 

	console.log("logging in: " + creds);
var uri = "http://localhost:8080/services/";
	$.ajax({
			url : uri + "login",
			type : "POST",
			contentType: "application/json",
			dataType:"json",
			data: JSON.stringify({username:usr,password:pwd}),
			success : function(data) {
				$(location).attr('href',"/landingPage"+data.username);
			},
			error: function (request, error) {
       		 console.log(arguments);
       		 alert(" Can't do because: " + error);
    		}
		});
}
});


	
	

