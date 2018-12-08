package com.ghqkl.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghqkl.schedule.dao.TaskDao;
import com.ghqkl.schedule.model.TaskBean;
import com.ghqkl.schedule.service.TaskService;
import com.github.pagehelper.PageInfo;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskDao dao;

	@Override
	public int insert(TaskBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(TaskBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(TaskBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TaskBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public List<TaskBean> queryList(TaskBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public int count(TaskBean t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageInfo<TaskBean> pageList(TaskBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
