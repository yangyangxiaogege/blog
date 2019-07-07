package com.bdqn.controller.foreground;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdqn.entity.Comment;
import com.bdqn.service.CommentService;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

	@Resource
	private CommentService commentService;
	
	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	@ResponseBody
	@PostMapping("/addComment")
	public Object addComment(Comment comment) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		int count = commentService.addComment(comment);
		
		if (count > 0 ) {
			map.put("success", true);
			
		}else {
			map.put("success", false);
		}
		return map;
	}
}

