package com.bdqn.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bdqn.dao.CommentMapper;
import com.bdqn.entity.Comment;
import com.bdqn.service.CommentService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

	@Override
	public List<Comment> findCommentListByBlogIdAndState(int blogId, int state) {
		// TODO Auto-generated method stub
		QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("blogId", blogId);
		queryWrapper.eq("state", state);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public int addComment(Comment comment) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			// 设置发表评论用户的ip
			comment.setUserIp(InetAddress.getLocalHost().getHostAddress());
			// 设置发表时间
			comment.setCommentDate(new Date());
			// 设置评论状态
			comment.setState(1); // 待审核
			
			count = baseMapper.insert(comment);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteCommentByBlogId(int blogId) {
		// TODO Auto-generated method stub
		
		QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("blogId", blogId);
		
		return baseMapper.delete(queryWrapper);
	}

}
