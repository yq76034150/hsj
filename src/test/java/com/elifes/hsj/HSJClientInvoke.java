package com.elifes.hsj;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.elifes.hsj.client.DefaultDBLookupStrategy;
import com.elifes.hsj.exception.HSJException;
import com.elifes.hsj.impl.DefaultDBConfigLoader;
import com.elifes.hsj.impl.DefaultHSJManager;
import com.elifes.hsj.model.TableConfig;

public class HSJClientInvoke {

	@Test
	public void test() {
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

		String postfix = "this.index" + "_" + 1;
		final String[] values = new String[11];
		values[0] = String.valueOf("eeeeee");
		values[1] = "hs_first_name_" + postfix;
		values[2] = "last_name_" + postfix;
		values[3] = "myduty_" + postfix;
		String phone = String.valueOf(111111111);
		values[4] = phone;
		values[5] = phone;
		values[6] = phone;
		values[7] = phone;
		values[8] = "my_home_address_" + postfix;
		values[9] = "my_office_address_" + postfix;
		values[10] = "this.remark";
		List<String> values1 = Arrays.asList(values);
		
		//直接操作数据了
		try {
			hsjManager.insert(indexId, values1);
		} catch (HSJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		//fail("Not yet implemented");
	}

}
