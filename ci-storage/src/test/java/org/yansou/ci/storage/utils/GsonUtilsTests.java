package org.yansou.ci.storage.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.yansou.ci.core.model.project.BiddingData;
import org.yansou.ci.core.model.project.WinCompany;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author liutiejun
 * @create 2017-06-01 15:34
 */
public class GsonUtilsTests {

	@Test
	public void testWinCompany() {
		List<WinCompany> winCompanyList = initWinCompany();

		Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				return false;
			}

			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return clazz.equals(BiddingData.class);
			}
		}).create();

		String winCompanyInfo = gson.toJson(winCompanyList);

		System.out.print(winCompanyInfo);
	}

	private static List<WinCompany> initWinCompany() {
		List<WinCompany> winCompanyList = new ArrayList<>();

		IntStream.range(1, 10).forEach(i -> {
			WinCompany winCompany = new WinCompany();
			winCompany.setWinAmount(100.0 + i);
			winCompany.setWinPrice(200.0 + i);
			winCompany.setWinCapacity(300.0 + i);
			winCompany.setCompanyName("cn_" + i);

			BiddingData biddingData = new BiddingData();
			biddingData.setId(10L + i);
			winCompany.setBiddingData(biddingData);

			winCompanyList.add(winCompany);
		});

		return winCompanyList;
	}

}
