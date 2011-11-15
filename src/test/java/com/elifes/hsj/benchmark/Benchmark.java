package com.elifes.hsj.benchmark;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeoutException;

import com.elifes.hsj.HSJManager;
import com.elifes.hsj.IDBConfigLoader;
import com.elifes.hsj.IDBLookupStrategy;
import com.elifes.hsj.client.DefaultDBLookupStrategy;
import com.elifes.hsj.exception.HSJException;
import com.elifes.hsj.impl.DefaultDBConfigLoader;
import com.elifes.hsj.impl.DefaultHSJManager;
import com.elifes.hsj.model.TableConfig;

/**
 * A benchmark between mysql driver and hsclient
 * 
 * @author dennis
 * @date 2010-11-27
 */
public class Benchmark {

	static final int connectionPoolSize = 100;
	static final int threads = 50;
	static final int repeats = 10000;
	static final int remarkSize = 1024;
	static final byte[] remarkData = new byte[remarkSize];

	public static void main(String[] args) throws Exception {
		//testMysql();

		testHandlerSocket();
	}

	private static void testHandlerSocket() throws IOException,
			InterruptedException, TimeoutException, 
			BrokenBarrierException {
//		HSClient hsClient = new HSClientImpl(new InetSocketAddress("10.232.20.157", 9999), 100);
//		final String[] columns = { "id", "last_name", "first_name", "duty",
//				"cellphone", "housephone", "telephone", "office_fax",
//				"home_address", "office_address", "remark" };
//		IndexSession session = hsClient.openIndexSession("test", "user",
//				"PRIMARY", columns);
		
		HSJManager hsjManager = DefaultHSJManager.getInstance();
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("id");
		columnNames.add("last_name");
		columnNames.add("first_name");
		columnNames.add("duty");
		columnNames.add("cellphone");
		columnNames.add("housephone");
		columnNames.add("telephone");
		columnNames.add("office_fax");
		columnNames.add("home_address");
		columnNames.add("office_address");
		columnNames.add("remark");
		
		List<String> filterColumnNames = null;	
		
		IDBLookupStrategy s = null;
		//方式一  初始化 根据userid可以知道操作那个数据库和那个数据表
		//lookupKey 为 userId
		//s = new shardByUserId(lookupKey);
				
		//方式二 初始化 根据配置获取 db信息；需要传入tbl 信息
		
		TableConfig tc = new TableConfig();
		IDBConfigLoader dbConfigLoader = new DefaultDBConfigLoader();
		tc.setDbConfig(dbConfigLoader.load());
		tc.setTblName("user");
		tc.setColumnNames(columnNames);
		tc.setFilterColumnNames(filterColumnNames);
		
		s = new DefaultDBLookupStrategy(tc);
		//hsjManager.set
		String indexId = null;
		try {
			indexId = hsjManager.init(s);
		} catch (HSJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		CyclicBarrier barrier = new CyclicBarrier(threads + 1);
		String remark = new String(remarkData);
		for (int i = 0; i < threads; i++) {
			HSClientThread mysqlThread = new HSClientThread(barrier, hsjManager, i, repeats,
					indexId, remark);
			mysqlThread.start();
		}
		long start = System.currentTimeMillis();
		barrier.await();
		barrier.await();
		long end = System.currentTimeMillis();
		long duration = end - start;
		long tps = repeats * threads * 1000 / duration;
		System.out.println("Concurrency " + threads + " threads,repeats="
				+ repeats + ",duration=" + duration + "ms,tps=" + tps);

		//hsClient.shutdown();
	}

//	private static void testMysql() throws IOException, InterruptedException,
//			BrokenBarrierException, SQLException {
//		Properties props = new Properties();
//		props.load(ResourcesUtils.getResourceAsStream("benchmark.properties"));
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setInitialSize(threads);
//		dataSource.setMaxActive(Integer.parseInt(props
//				.getProperty("dataSource.maxActive")));
//		dataSource
//				.setDriverClassName(props.getProperty("jdbc.driverClassName"));
//		dataSource.setUrl(props.getProperty("jdbc.url"));
//		dataSource.setUsername(props.getProperty("jdbc.username"));
//		dataSource.setPassword(props.getProperty("jdbc.password"));
//		dataSource.setMaxWait(Long.parseLong(props
//				.getProperty("dataSource.maxWait")));
//		dataSource.setMaxIdle(Integer.parseInt(props
//				.getProperty("dataSource.maxIdle")));
//		dataSource.setMinIdle(Integer.parseInt(props
//				.getProperty("dataSource.minIdle")));
//
//		CyclicBarrier barrier = new CyclicBarrier(threads + 1);
//		String remark = new String(remarkData);
//		for (int i = 0; i < threads; i++) {
//			MysqlThread mysqlThread = new MysqlThread(barrier, repeats, i,
//					dataSource, remark);
//			mysqlThread.start();
//		}
//		long start = System.currentTimeMillis();
//		barrier.await();
//		barrier.await();
//		long end = System.currentTimeMillis();
//		long duration = end - start;
//		long tps = repeats * threads * 1000 / duration;
//		System.out.println("Concurrency " + threads + " threads,repeats="
//				+ repeats + ",duration=" + duration + "ms,tps=" + tps);
//
//		dataSource.close();
//	}
}
