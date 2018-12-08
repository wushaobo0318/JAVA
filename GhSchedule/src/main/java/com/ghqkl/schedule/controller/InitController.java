package com.ghqkl.schedule.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.ghqkl.schedule.model.TaskBean;
import com.ghqkl.schedule.service.TaskService;
import com.ghqkl.schedule.task.ScheduleConfig;

@RestController
public class InitController {
	
	@Autowired
	private ScheduleConfig scheduleConfig;
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping("/init.html")
    public String init(HttpServletRequest request,HttpServletResponse response,ModelAndView mav) throws IOException {
		try {
			ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
			TaskBean task=new TaskBean();
			task.setStatus(1);
			List<TaskBean> taskList=taskService.queryList(task);
			if(taskList==null||taskList.size()==0)
			{
				response.getWriter().write("no task");
			}
			for (TaskBean t : taskList) {
				scheduleConfig.setCron((Runnable)context.getBean(t.getName()), t.getName(), t.getCron());
			}
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
    }
	
	@RequestMapping("/stop.html")
    public String stop(HttpServletRequest request,HttpServletResponse response,ModelAndView mav) throws IOException {
		String taskName=request.getParameter("taskName");
		try {
			if(taskName==null||"".equals(taskName))
			{
				scheduleConfig.stopAllCron();
			}
			else
			{
				scheduleConfig.stopCron(taskName);
			}
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}
}
