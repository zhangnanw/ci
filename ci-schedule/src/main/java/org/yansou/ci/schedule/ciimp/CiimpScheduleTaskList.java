package org.yansou.ci.schedule.ciimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CiimpScheduleTaskList {
	final static private String CI_STORAGE = "ci-storage";

	@Autowired
	private RestTemplate client;

	public CiimpScheduleTaskList() {
		System.out.println("CiimpScheduleTaskList init.");
	}

	@Scheduled(cron = "0 0 * * * *")
	public void runImportPlanBuild() {
		try {
			String requestUrl = "http://" + CI_STORAGE + "/importdata/planbuild";
			String response = client.getForObject(requestUrl, String.class);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "0 20 * * * *")
	public void runImportDlzbPlanBuild() {
		try {

			String requestUrl = "http://" + CI_STORAGE + "/importdata/dlzbplanbuild";
			String response = client.getForObject(requestUrl, String.class);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "0 40 * * * *")
	public void runImportBidding() {
		try {
			String requestUrl = "http://" + CI_STORAGE + "/importdata/bidding";
			String response = client.getForObject(requestUrl, String.class);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "0 15,45 * * * *")
	public void runProjectMerge() {
		try {
			String requestUrl = "http://" + CI_STORAGE + "/importdata/projectmerge";
			String response = client.getForObject(requestUrl, String.class);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "0 50 * * * *")
	public void runRecorvBidding() {
		try {
			String requestUrl = "http://" + CI_STORAGE + "/importdata/rebidding";
			String response = client.getForObject(requestUrl, String.class);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
