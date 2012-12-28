//
// 都要用到的js都在这里
//

!function($) {
	
	$(function(){

		// make code pretty
		window.prettyPrint && prettyPrint()
		
		// 主页的slide
		$('#activityCarousel').carousel()
		
		//题目列表来源弹出效果
		$('.info-problem-source').tooltip()
		
	})
}(window.jQuery)

