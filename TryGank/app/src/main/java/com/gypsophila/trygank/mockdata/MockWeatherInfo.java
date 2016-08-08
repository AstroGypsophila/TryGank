package com.gypsophila.trygank.mockdata;

import com.alibaba.fastjson.JSON;
import com.gypsophila.commonlib.net.Response;
import com.gypsophila.trygank.entity.WeatherInfo;

public class MockWeatherInfo extends MockService {	
	@Override
	public String getJsonData() {
		WeatherInfo weather = new WeatherInfo();
		weather.setCity("Beijing");
		weather.setCityid("10000");

		Response response = getSuccessResponse();
		response.setResult(JSON.toJSONString(weather));
		return JSON.toJSONString(response);
	}
}
