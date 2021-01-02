//@ sourceURL=login_ajax.js
$(function(){
	//记住用户名
	$("#username").val(getCookie("loginName"));
    $.validator.setDefaults({
     submitHandler: function() {
      var username = $("#username").val();
      var password = $("#password").val();
      var remember=$("#rememberMe").get(0).checked;
      $.ajax({
        url: '/api/login',
        type: 'post',
        contentType: 'application/json',
        //contentType:"application/x-www-form-urlencoded; charset=UTF-8",
        data: JSON.stringify({username:username,password:password}),
        //dataType: 'text',
        success: function (res) {
          if(res.code==200){
     		 $.cookie('username', username, { expires: 7 ,path:'/'});
             //var uname = $.cookie('username');
             window.location='/index.html';
             if(remember){
            	 if(getCookie("loginName")==""){
            		 addCookie("loginName",username,24);
            	 }
             }else{
            	  delCookie("loginName");
             }
          }else{
            $("#msg").text(res.message);
          }
        },
		error:function(){
			alert("请求失败!");
		}
      })
     }
    });
	
});

$().ready(function() {
  var validator =  $("#form1").validate();
  $("#closeButton").click(function() {
    validator.resetForm();
  });
 });
