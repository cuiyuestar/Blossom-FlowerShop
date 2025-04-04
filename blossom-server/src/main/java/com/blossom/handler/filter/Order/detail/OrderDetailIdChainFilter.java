/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blossom.handler.filter.Order.detail;

import com.blossom.dto.OrdersSubmitDTO;
import com.blossom.entity.OrderDetail;
import com.blossom.exception.OrderBusinessException;
import com.blossom.handler.filter.Order.submit.OrderSubmitChainFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 鲜花分页查询请求校验处理器
 */
@Component
@RequiredArgsConstructor
public class OrderDetailIdChainFilter implements OrderDetailChainFilter<OrderDetail> {


    @Override
    public void handler(OrderDetail requestParam) {
        if(requestParam.getOrderId()==null){
            throw new OrderBusinessException("订单不存在");
        }

        if(requestParam.getFlowerId()==null){
            throw new OrderBusinessException("购买对象不存在");
        }
    }

    /**
     * 设置该处理器优先级
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
