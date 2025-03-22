package com.blossom.service.impl;


import com.blossom.dto.GoodsSalesDTO;
import com.blossom.entity.Orders;
import com.blossom.mapper.OrderMapper;
import com.blossom.mapper.UserMapper;
import com.blossom.service.ReportService;
import com.blossom.vo.OrderReportVO;
import com.blossom.vo.SalesTop10ReportVO;
import com.blossom.vo.TurnoverReportVO;
import com.blossom.vo.UserReportVO;
import org.apache.commons.lang3.StringUtils;
//import org.apache.maven.surefire.shade.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 统计指定时间区间内的营业额
     * @param begin
     * @param end
     * @return
     */
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList=new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)){ //如果begin自增到end，则退出循环
            begin=begin.plusDays(1);
            dateList.add(begin);
        }

        List<Double> turnoverList=new ArrayList<>(); //存放每天的营业额

        //遍历日期列表，查询每个日期对应的营业额（营业额：状态为“已完成”的订单金额合计）
        for(LocalDate date:dateList){
            //设置该日的起始时间与结束时间
            LocalDateTime beginTime=LocalDateTime.of(date, LocalTime.MIN); //00:00:00
            LocalDateTime endTime=LocalDateTime.of(date, LocalTime.MAX); //23:59:59

            Map map=new HashMap();
            map.put("begin",beginTime);
            map.put("end",endTime);
            map.put("status", Orders.COMPLETED);

            Double turnover = orderMapper.sumByMap(map); //计算该日的营业额turnover
            //如果查不到营业额，会返回null，需要转为0
            turnover=turnover==null ? 0.0 : turnover;

            turnoverList.add(turnover);
        }

        String dateListStr= StringUtils.join(dateList,","); //将日期列表构造成字符串，日期之间用逗号隔开
        String turnoverListStr=StringUtils.join(turnoverList,","); //将营业额列表构造成字符串，营业额之间用逗号隔开

        //将查询到的营业额数据封装到VO对象并返回
        return TurnoverReportVO
                .builder()
                .dateList(dateListStr)//将日期列表字符串封装到TurnoverReportVO对象中
                .turnoverList(turnoverListStr) //将营业额列表字符串封装到TurnoverReportVO对象中
                .build();
    }

    /**
     * 统计指定时间区间内的用户数量
     * @param begin
     * @param end
     * @return
     */
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList=new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)){
            begin=begin.plusDays(1);
            dateList.add(begin);
        }
        //创建存放每日用户量的列表
        List<Integer> newUserList=new ArrayList<>();//select count(*) from user where create_time between begin and end
        List<Integer> totalUserList=new ArrayList<>(); //select count(*) from user where create_time < end

        for(LocalDate date:dateList){
            //设置该日的起始时间与结束时间
            LocalDateTime beginTime=LocalDateTime.of(date, LocalTime.MIN); //00:00:00
            LocalDateTime endTime=LocalDateTime.of(date, LocalTime.MAX); //23:59:59

            Map map=new HashMap();
            map.put("end",endTime); //先传end到map里，用于计算总用户数 (总用户数只需end来形成时间区间)
            //统计总用户数
            Integer totalUser=userMapper.countByMap(map);

            map.put("begin",beginTime); //接着再传begin到map里，用于计算新增用户数 (新增用户数需要begin/end来形成时间区间)
            //统计新增用户数
            Integer newUser=userMapper.countByMap(map);

            totalUserList.add(totalUser);
            newUserList.add(newUser);
        }

        String dateListStr=StringUtils.join(dateList,",");
        String newUserListStr=StringUtils.join(newUserList,",");
        String totalUserListStr=StringUtils.join(totalUserList,",");

        return UserReportVO.builder()
                .dateList(dateListStr)
                .newUserList(newUserListStr)
                .totalUserList(totalUserListStr)
                .build();
    }

    /**
     * 统计指定时间区间内的订单数据
     * @param begin
     * @param end
     * @return
     */
    public OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end) {

        List<LocalDate> dateList=new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)){
            begin=begin.plusDays(1);
            dateList.add(begin);
        }

        List<Integer> orderCountList=new ArrayList<>();
        List<Integer> validOrderCountList=new ArrayList<>();

        for(LocalDate date:dateList){
            //查询每日订单总数 select count(*) from orders where order_time between begin and end
            LocalDateTime beginTime=LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime=LocalDateTime.of(date, LocalTime.MAX);
            Integer orderCount=getOrderCount(beginTime,endTime,null); //每日订单总数

            //查询每日有效订单总数 select count(*) from orders where order_time between begin and end and status=5
            //将状态5（已完成）传入，表示查询状态为“已完成”的订单总数
            Integer validOrderCount=getOrderCount(beginTime,endTime,Orders.COMPLETED);

            orderCountList.add(orderCount);
            validOrderCountList.add(validOrderCount);
        }

        //计算时间区间内的订单总数
        Integer totalOrderCount=orderCountList.stream().reduce(Integer::sum).get();
        //计算时间区间内的有效订单总数
        Integer validOrderCount=validOrderCountList.stream().reduce(Integer::sum).get();
        //计算订单完成率
        Double orderCompletionRate=0.0;
        if(totalOrderCount!=0){
            orderCompletionRate = validOrderCount.doubleValue()/totalOrderCount;
        }

        return OrderReportVO.builder()
                .orderCountList(StringUtils.join(orderCountList,","))
                .validOrderCountList(StringUtils.join(validOrderCountList,","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    /**
     * 统计指定时间区间内的销量排名
     * @param begin
     * @param end
     * @return
     */
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime=LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime=LocalDateTime.of(end, LocalTime.MAX);
        List<GoodsSalesDTO> salesTop10=orderMapper.getSalesTop10(begin,end);
        //将DTO列表对象里的各个名字取出并收集，封装到nameList列表中
        List<String> nameList=salesTop10.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        String nameListStr=StringUtils.join(nameList,",");
        //将DTO列表对象里的各个数量取出并收集，封装到numberList列表中
        List<Integer> numberList=salesTop10.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        String numberListStr=StringUtils.join(numberList,",");

        return SalesTop10ReportVO.builder()
                .nameList(nameListStr)
                .numberList(numberListStr)
                .build();
    }

    /**
     * 统计指定时间区间内订单总数
     * @return
     */
    private Integer getOrderCount(LocalDateTime begin,LocalDateTime end,Integer status){
        Map map=new HashMap();
        map.put("begin",begin);
        map.put("end",end);
        map.put("status",status);
        return orderMapper.countByMap(map);
    }

}
