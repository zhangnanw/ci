package org.yansou.ci.schedule.ciimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yansou.ci.common.time.TimeStat;
import org.yansou.ci.storage.ciimp.CorvDlzbToPlanBuild;
import org.yansou.ci.storage.ciimp.CorvToBidding;
import org.yansou.ci.storage.ciimp.CorvToPlanBuild;

@Component
public class CiimpScheduleTaskList {
	@Autowired
	private CorvToPlanBuild corvToPlanBuidl;
	@Autowired
	private CorvToBidding corvToBidding;
	@Autowired
	private CorvDlzbToPlanBuild corvDlzbPlanBuild;

	public CiimpScheduleTaskList() {
		System.out.println("CiimpScheduleTaskList init.");
	}

	@Scheduled(cron = "0 0 * * * *")
	public void runImportPlanBuild() {
		TimeStat ts = new TimeStat();
		corvToPlanBuidl.run();
		ts.buriePrint("runImportPlanBuild done. use time:{}");
	}

	@Scheduled(cron = "0 20 * * * *")
	public void runImportDlzbPlanBuild() {
		TimeStat ts = new TimeStat();
		corvDlzbPlanBuild.run();
		ts.buriePrint("runImportDlzbPlanBuild done. use time:{}");
	}

	@Scheduled(cron = "0 40 * * * *")
	public void runImportBidding() {
		TimeStat ts = new TimeStat();
		corvToBidding.run();
		ts.buriePrint("runImportBidding done. use time:{}");
	}
}
