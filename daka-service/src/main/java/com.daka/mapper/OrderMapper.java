package com.daka.mapper;

import com.daka.model.OrderModel;
import com.daka.model.UserModel;
import com.daka.pageinator.PageList;
import com.daka.pageinator.support.PageBounds;

import java.util.List;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2019/5/13 17:07
 * @Version 1.0
 */
public interface OrderMapper {

    int addOrder(OrderModel order);

    PageList<OrderModel> queryOrderList(PageBounds pageBounds, OrderModel order);
}
