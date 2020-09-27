/*package com.clouds.druid.main;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.clouds.druid.pojo.DruidResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.zapr.druid.druidry.Interval;
import in.zapr.druid.druidry.aggregator.CountAggregator;
import in.zapr.druid.druidry.aggregator.DruidAggregator;
import in.zapr.druid.druidry.client.DruidClient;
import in.zapr.druid.druidry.client.DruidConfiguration;
import in.zapr.druid.druidry.client.DruidJerseyClient;
import in.zapr.druid.druidry.client.exception.ConnectionException;
import in.zapr.druid.druidry.dimension.DruidDimension;
import in.zapr.druid.druidry.dimension.SimpleDimension;
import in.zapr.druid.druidry.filter.AndFilter;
import in.zapr.druid.druidry.filter.SelectorFilter;
import in.zapr.druid.druidry.filter.searchQuerySpec.InsensitiveContainsSearchQuerySpec;
import in.zapr.druid.druidry.filter.searchQuerySpec.SearchQuerySpec;
import in.zapr.druid.druidry.granularity.Granularity;
import in.zapr.druid.druidry.granularity.PredefinedGranularity;
import in.zapr.druid.druidry.granularity.SimpleGranularity;
import in.zapr.druid.druidry.query.aggregation.DruidTimeSeriesQuery;
import in.zapr.druid.druidry.query.aggregation.DruidTopNQuery;
import in.zapr.druid.druidry.query.search.DruidSearchQuery;
import in.zapr.druid.druidry.topNMetric.SimpleMetric;
import in.zapr.druid.druidry.topNMetric.TopNMetric;

public class DRUIDMain {

	*//**
	 * Aggregation Example
	 *//*
	public void runExample1() {
		DruidClient client = null;
		try {
			SelectorFilter selectorFilter1 = new SelectorFilter("channel", "#en.wikipedia");
			SelectorFilter selectorFilter2 = new SelectorFilter("cityName", "Tokyo");

			AndFilter filter = new AndFilter(Arrays.asList(selectorFilter1, selectorFilter2));

			DruidAggregator aggregator1 = new CountAggregator("count");

			DateTime startTime = new DateTime(2015, 9, 11, 0, 0, 0, DateTimeZone.forID("Asia/Shanghai"));
			DateTime endTime = new DateTime(2020, 9, 13, 0, 0, 0, DateTimeZone.forID("Asia/Shanghai"));
			Interval interval = new Interval(startTime, endTime);

			Granularity granularity = new SimpleGranularity(PredefinedGranularity.MINUTE);

			DruidTimeSeriesQuery query = DruidTimeSeriesQuery.builder().dataSource("wikiticker-2015-09-12-sampled")
					.descending(true).granularity(granularity).filter(filter).aggregators(Arrays.asList(aggregator1))
					.intervals(Collections.singletonList(interval)).build();

			ObjectMapper mapper = new ObjectMapper();
			String requiredJson = mapper.writeValueAsString(query);
			System.out.println("requiredJson : " + requiredJson);

			DruidConfiguration config = DruidConfiguration.builder().host("localhost").port(8888).endpoint("druid/v2/")
					.build();

			client = new DruidJerseyClient(config);

			client.connect();

//			String res = client.query(query);
//			System.out.println("ress : " + res);

			List<DruidResponse> responses = client.query(query, DruidResponse.class);

			for (DruidResponse timeseriesResponse : responses) {
				System.out.println("timeseriesResponse:{}" + timeseriesResponse.toString());
			}

			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}

	}

	*//**
	 * Top Query Example
	 *//*
	public void runExample2() throws Exception {
		DruidAggregator aggregator1 = new CountAggregator("count");

		DateTime startTime = new DateTime(2015, 9, 11, 0, 0, 0, DateTimeZone.forID("Asia/Shanghai"));
		DateTime endTime = new DateTime(2015, 9, 13, 0, 0, 0, DateTimeZone.forID("Asia/Shanghai"));
		Interval interval = new Interval(startTime, endTime);

		Granularity granularity = new SimpleGranularity(PredefinedGranularity.MINUTE);

		DruidDimension dimension = new SimpleDimension("channel");
		TopNMetric metric = new SimpleMetric("count");

		DruidTopNQuery query = DruidTopNQuery.builder().dataSource("wikiticker-2015-09-12-sampled").dimension(dimension)
				.threshold(2).topNMetric(metric).granularity(granularity).aggregators(Arrays.asList(aggregator1))
				.intervals(Collections.singletonList(interval)).build();

		ObjectMapper mapper = new ObjectMapper();
		String requiredJson = mapper.writeValueAsString(query);
		System.out.println("requiredJson:{}" + requiredJson);

		DruidConfiguration config = DruidConfiguration.builder().host("localhost").port(8888).endpoint("druid/v2/")
				.build();

		DruidClient client = new DruidJerseyClient(config);
		try {
			client.connect();
			List<DruidResponse> responses = client.query(query, DruidResponse.class);

			for (DruidResponse timeseriesResponse : responses) {
				System.out.println("timeseriesResponse:{}" + timeseriesResponse.toString());
			}

			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}
	}

	public void runExample3() throws JsonProcessingException {
		DateTime startTime = new DateTime(2015, 9, 11, 0, 0, 0, DateTimeZone.forID("Asia/Shanghai"));
		DateTime endTime = new DateTime(2015, 9, 13, 0, 0, 0, DateTimeZone.forID("Asia/Shanghai"));
		Interval interval = new Interval(startTime, endTime);

		SelectorFilter selectorFilter1 = new SelectorFilter("channel", "#en.wikipedia");
		SelectorFilter selectorFilter2 = new SelectorFilter("cityName", "Tokyo");

		AndFilter filter = new AndFilter(Arrays.asList(selectorFilter1, selectorFilter2));

		Granularity granularity = new SimpleGranularity(PredefinedGranularity.MINUTE);

		DruidDimension dimension1 = new SimpleDimension("channel");
		DruidDimension dimension2 = new SimpleDimension("modifytime");
		DruidDimension dimension3 = new SimpleDimension("cityName");

		SearchQuerySpec searchQuerySpec = new InsensitiveContainsSearchQuerySpec("DCS2.20LAC20CT308");

		DruidSearchQuery query = DruidSearchQuery.builder().dataSource("wikiticker-2015-09-12-sampled")
				.searchDimensions(Arrays.asList(dimension1, dimension2, dimension3)).granularity(granularity).limit(20)
				.intervals(Collections.singletonList(interval)).filter(filter).query(searchQuerySpec).build();
//		DruidSelectQuery query = DruidSelectQuery.builder().dataSource("wikiticker-2015-09-12-sampled")
//				.dimensions(Arrays.asList(dimension1, dimension2, dimension3)).granularity(granularity)
//				.pagingSpec(pagingSpec).intervals(Collections.singletonList(interval)).build();

		ObjectMapper mapper = new ObjectMapper();
		String requiredJson = mapper.writeValueAsString(query);
		System.out.println("requiredJson:{}" + requiredJson);

		DruidConfiguration config = DruidConfiguration.builder().host("localhost").port(8888).endpoint("druid/v2/")
				.build();

		DruidClient client = new DruidJerseyClient(config);
		try {
			client.connect();
			List<DruidResponse> responses = client.query(query, DruidResponse.class);

			for (DruidResponse timeseriesResponse : responses) {
				System.out.println("timeseriesResponse:{}" + timeseriesResponse.toString());
			}

			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (ConnectionException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws Exception {
		DRUIDMain druidMain = new DRUIDMain();
		System.out.println("Running First Example......");
		druidMain.runExample1();
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("Running Second Example......");
		druidMain.runExample2();
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("Running Third Example......");
		druidMain.runExample3();
	}
}
*/