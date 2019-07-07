package com.bdqn.dao;

import com.bdqn.entity.Blogtype;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yxy
 * @since 2019-06-18
 */
public interface BlogtypeMapper extends BaseMapper<Blogtype> {

	List<Blogtype> findBlogtypeAndCount() throws Exception;
}
