package com.learn.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Пример работы шедулера quartz
 * @author Kot
 * @ created 21.02.2018
 * @ $Author$
 * @ $Revision$
 */
public class MyJob implements Job
{
	public static void runScheduler()
	{
		try
		{
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			JobDetail job = newJob(MyJob.class).withIdentity("MyJob", "MyGroup").build();
			Trigger trigger = newTrigger().withIdentity("trig1", "TrigMyGroup").startNow()
				.withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever()).build();
			scheduler.scheduleJob(job, trigger);
		}
		catch (SchedulerException e)
		{
			e.printStackTrace();
		}
	}


	@Override public void execute(JobExecutionContext context) throws JobExecutionException
	{
		System.out.println("Job job");
	}
}
