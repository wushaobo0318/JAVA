package com.ghqkl.schedule.dao;

import java.util.List;

public interface BaseDao<T> {
	int insert(T t);
	
	int delete(T t);
	
	int update(T t);
	
	int count(T t);
	
	T getById(Integer Id);
	
	List<T> queryList(T t);
}
