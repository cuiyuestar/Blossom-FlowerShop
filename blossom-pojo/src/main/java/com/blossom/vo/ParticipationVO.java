package com.blossom.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * 活动参加结果（返回给前端，包含用户参与的活动与购买的促销鲜花信息，这些数据用于给前端作为参数传入购物车接口的，即服务于后续购买流程）
 */
public class ParticipationVO implements Serializable {

    String content;

    Integer limitPer;

    BigDecimal originalPrice;

    BigDecimal discountPrice;

    Integer stock;

    Integer sale;

}
