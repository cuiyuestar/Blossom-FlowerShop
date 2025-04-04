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

package com.blossom.handler.filter.Activity.create;

import com.blossom.dto.ActivityDTO;
import com.blossom.dto.CommentDTO;
import com.blossom.exception.ActivityException;
import com.blossom.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 活动创建数据校验处理器
 */
@Component
@RequiredArgsConstructor
public class ActivityCreateTimeChainFilter implements ActivityCreateChainFilter<ActivityDTO> {

    /**
     * 处理逻辑
     * @param requestParam
     */
    public void handler(ActivityDTO requestParam) {
        if(requestParam.getEndTime().isBefore(requestParam.getStartTime())){
            throw new ActivityException("活动时间异常");
        }
    }

    /**
     * 设置该处理器优先级
     * @return
     */
    @Override
    public int getOrder() {
        return 5;
    }
}
