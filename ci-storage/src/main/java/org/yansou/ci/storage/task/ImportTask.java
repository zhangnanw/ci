// package org.yansou.ci.storage.task;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.yansou.ci.common.time.TimeStat;
// import org.yansou.ci.storage.ciimp.CorvToBidding;
// import org.yansou.ci.storage.ciimp.CorvToPlanBuild;
//
/// **
// * 导入数据定时任务
// *
// * @author n.zhang
// *
// */
// @Deprecated
// public class ImportTask {
// @Autowired
// private CorvToPlanBuild corvToPlanBuidl;
// @Autowired
// private CorvToBidding corvToBidding;
//
// /**
// * 导入并更新拟在建信息
// */
// @Scheduled(cron = "0 0 * * * *")
// public void runImportPlanBuild() {
// TimeStat ts = new TimeStat();
// corvToPlanBuidl.run();
// ts.buriePrint("runImportPlanBuild done. use time:{}");
// }
//
// /**
// * 导入
// */
// @Scheduled(cron = "0 30 * * * *")
// public void runImportBidding() {
// TimeStat ts = new TimeStat();
// corvToBidding.run();
// ts.buriePrint("runImportBidding done. use time:{}");
// }
// }
