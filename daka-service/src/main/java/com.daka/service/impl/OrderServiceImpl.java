package com.daka.service.impl;

import com.daka.mapper.OrderMapper;
import com.daka.model.OrderModel;
import com.daka.model.ResponseModel;
import com.daka.pageinator.PageList;
import com.daka.pageinator.support.PageBounds;
import com.daka.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2019/5/17 15:58
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ResponseModel addOrder(OrderModel order) {
        ResponseModel responseModel = new ResponseModel();
        orderMapper.addOrder(order);
        return responseModel;
    }

    @Override
    public ResponseModel queryOrderList(OrderModel order) {
        ResponseModel responseModel = new ResponseModel();
        PageBounds pageBounds = new PageBounds();
        PageList<OrderModel> list = orderMapper.queryOrderList(pageBounds,order);

        responseModel.setRespData(list.getPageResult().getData());

        return responseModel;
    }
}
