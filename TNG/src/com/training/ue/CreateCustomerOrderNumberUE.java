package com.training.ue;

import java.util.Map;
import java.util.Random;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSUserExitException;
import com.yantra.yfs.japi.ue.YFSGetOrderNoUE;

public class CreateCustomerOrderNumberUE implements YFSGetOrderNoUE {

	@Override
	public String getOrderNo(YFSEnvironment env, Map arg1) throws YFSUserExitException {
		Random random = new Random();
		int randomNum = random.nextInt(10000);
		return String.valueOf("DRAFT"+randomNum);
	}
}

