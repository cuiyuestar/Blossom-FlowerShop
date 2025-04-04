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

package com.blossom.handler.filter.Comment.add;

import com.blossom.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 评论分页查询数据校验处理器
 */
@Component
@RequiredArgsConstructor
public class AddCommentIdChainFilter implements CommentAddChainFilter<CommentDTO> {

    /**
     * 判断鲜花id是否为空
     * @param requestParam 责任链执行入参
     */
    @Override
    public void handler(CommentDTO requestParam) {

        if(requestParam.getFlowerId()==null){
            throw new RuntimeException("评论对象不存在");
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
