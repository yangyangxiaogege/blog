$(function(){
	$.get('/typeList',function(data){
		var blogTypeList = $('#blogTypeList');
		
		$(data).each(function(){
			var type = "<li><span><a href='/index.html?typeId="+this.id+"'>"+this.typeName+"("+this.blogCount+")</a></span></li>";
			blogTypeList.append(type);
		})
	},'json');
	
	$.get('/dateList',function(data){
		var dateList = $('#dateList');
		
		$(data).each(function(){
			var type = "<li><span><a href='/index.html?releaseDateStr="+this.releaseDateStr+"'>"+this.releaseDateStr+"("+this.blogCount+")</a></span></li>";
			dateList.append(type);
		})
	},'json')
	
})