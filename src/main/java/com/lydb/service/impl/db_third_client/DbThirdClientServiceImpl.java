package com.lydb.service.impl.db_third_client;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lydb.service.db_third_client.DbThirdClientServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("dbThirdClientService")
@Transactional
public class DbThirdClientServiceImpl extends CommonServiceImpl implements DbThirdClientServiceI {
	
}