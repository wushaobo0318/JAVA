package com.ghqkl.schedule.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ghqkl.schedule.model.TaskBean;

@Mapper
public interface TaskDao extends BaseDao<TaskBean>{
	
}