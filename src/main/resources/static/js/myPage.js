/**
 * 
 */
var deleteBtn = document.querySelector(".btn-projectdelete");
let delBtnEls = document.querySelectorAll('.btn-projectdelete');
 
 $('.btn-projectdelete').on('click', function(e){
	var id = e.target.id;
	
	var data = {
		"id": id
	}
	var newdata = JSON.stringify(data);
	console.log(newdata);
	deleteAdmin(newdata);
});


function deleteAdmin(jsondata) {
	const url = "/member/myPage"
	$.ajax({
		type: "PATCH",
		url: url,
		contentType: "application/json",
		data: jsondata,
		dataType: "json",
		async: true
	}).done(function(result, status) {
		console.log(status);
		alert("프로젝트가 삭제되었습니다.");
		location.reload();
	}).fail(function(request, status, error) {
		alert("update 에러 발생 : " + error + ",  " + request);
		console.log(request);
	})

};