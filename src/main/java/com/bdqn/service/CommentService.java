package com.bdqn.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bdqn.entity.Comment;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
public interface CommentService extends IService<Comment> {

	/**
	 * 根据博客id和评论审核状态查询评论列表
	 * @param blogId
	 * @param state
	 * @return
	 */
	List<Comment> findCommentListByBlogIdAndState(int blogId,int state);
	
	/**
	 * 添加评论
	 * @param comment
	 * @return
	 */
	int addComment(Comment comment);
	
	/**
	 * 根据博客评论
	 * @param blogId
	 * @return
	 */
	int deleteCommentByBlogId(int blogId);
}
