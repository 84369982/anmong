var app=angular.module("game",[]);
app.directive("navFooter",function(){
	return {
		restrict:"ECAM",
		templateUrl:"templates/footer.html",
	}
});