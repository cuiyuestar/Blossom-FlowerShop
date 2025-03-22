package com.blossom.controller.admin;

import com.blossom.result.Result;
import com.blossom.service.ReportService;
import com.blossom.vo.OrderReportVO;
import com.blossom.vo.SalesTop10ReportVO;
import com.blossom.vo.TurnoverReportVO;
import com.blossom.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Slf4j
@Api(tags="数据统计相关接口")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/turnoverStatistics")
    @ApiOperation("查询营业额统计")
    public Result<TurnoverReportVO> turnoverStatistics(
            //设置日期显示格式
            @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern ="yyyy-MM-dd")LocalDate end){
        log.info("营业额统计：{}~{}",begin,end);
        return Result.success(reportService.getTurnoverStatistics(begin,end));
    }

    @GetMapping("/userStatistics")
    @ApiOperation("用户量统计")
    public Result<UserReportVO> userStatistics(
            //设置日期显示格式
            @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern ="yyyy-MM-dd")LocalDate end){
        log.info("用户量统计：{}~{}",begin,end);
        return Result.success(reportService.getUserStatistics(begin,end));
    }


    @GetMapping("/ordersStatistics")
    @ApiOperation("订单统计")
    public Result<OrderReportVO> ordersStatistics(
            //设置日期显示格式
            @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern ="yyyy-MM-dd")LocalDate end){
        log.info("订单统计：{}~{}",begin,end);
        return Result.success(reportService.getOrdersStatistics(begin,end));
    }

    @GetMapping("/top10")
    @ApiOperation("销量排名")
    public Result<SalesTop10ReportVO> top10(
            //设置日期显示格式
            @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern ="yyyy-MM-dd")LocalDate end){
        log.info("订单统计：{}~{}",begin,end);
        return Result.success(reportService.getSalesTop10(begin,end));
    }

}
