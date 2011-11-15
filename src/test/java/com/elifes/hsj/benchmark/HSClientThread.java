package com.elifes.hsj.benchmark;

import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

import com.elifes.hsj.HSJManager;
import com.elifes.hsj.exception.HSJException;

public class HSClientThread extends Thread {

	private final CyclicBarrier barrier;
	
	private final HSJManager hsjManager;

	private final int repeats;

	private final String index;
	
	private final int id;

	private final String remark;

	public HSClientThread(CyclicBarrier barrier, HSJManager hsjManager, int id, int repeats, String index,
			 String remark) {
		super();
		this.barrier = barrier;
		this.hsjManager = hsjManager;
		this.id = id;
		this.repeats = repeats;
		this.index = index;
		this.remark = remark;
	}

	@Override
	public void run() {
		try {
			this.barrier.await();
		} catch (Exception e) {
			// ignore
		}
		for (int i = 0; i < this.repeats; i++) {
			String postfix = this.index + "_" + i;
			final String[] values = new String[11];
			values[0] = String.valueOf(Integer.valueOf(this.id) * this.repeats + i);
			values[1] = "hs_first_name_" + postfix;
			values[2] = "last_name_" + postfix;
			values[3] = "myduty_" + postfix;
			String phone = String.valueOf(i);
			values[4] = phone;
			values[5] = phone;
			values[6] = phone;
			values[7] = phone;
			values[8] = "my_home_address_" + postfix;
			values[9] = "my_office_address_" + postfix;
			values[10] = this.remark;
			try {
				hsjManager.insert(index, Arrays.asList(values));
			} catch (HSJException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			this.barrier.await();
		} catch (Exception e) {
			// ignore
		}

	}
}
