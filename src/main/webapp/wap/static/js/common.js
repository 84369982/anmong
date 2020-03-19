//设置相对单位


$(document).ready(function(){
	setFontSize();
});

$(window).resize(function(){
	setFontSize();
})

function setFontSize(){
	var w=$(window).width();
	if(w < 720) {
		var px=w/7.2;
	    setTimeout(function() {       	
	        $("body").css("opacity","1")
	        $("html").css("font-size",px+"px")            
	    }, 100)
	} else {
		w=720;
		var px=w/7.2;
	    setTimeout(function() {       	
	        $("body").css("opacity","1")
	        $("html").css("font-size",px+"px")            
	    }, 100)
	}

}

