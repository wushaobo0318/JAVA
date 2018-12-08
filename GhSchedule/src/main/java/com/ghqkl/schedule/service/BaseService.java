package com.ghqkl.schedule.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

public interface BaseService<T> {
	
	int insert(T t);
	
	int delete(T t);
	
	int update(T t);
	
	int count(T t);
	
	T getById(Integer Id);
	
	List<T> queryList(T t);
	
	PageInfo<T> pageList(T t,int pageNum,int pageSize);
}
