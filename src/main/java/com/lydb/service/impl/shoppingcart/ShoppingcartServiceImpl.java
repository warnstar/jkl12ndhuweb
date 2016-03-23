package com.lydb.service.impl.shoppingcart;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lydb.service.shoppingcart.ShoppingcartServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

@Service("shoppingcartService")
@Transactional
public class ShoppingcartServiceImpl extends CommonServiceImpl implements ShoppingcartServiceI {
	
}