package com.blossom.controller.admin;

import com.blossom.utils.AliOssUtil;
import com.blossom.constant.MessageConstant;
import com.blossom.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags="通用接口")
@Slf4j


public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}",file);
        //将文件上传到阿里云服务器
        try {
            //获取原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名后缀
            String extension=originalFilename.substring(originalFilename.lastIndexOf("."));//从文件名的最后一个.开始截取
            //构建新文件名称
            String objectName= UUID.randomUUID()+extension;
            //将文件上传到服务器后，生成文件的请求路径
            String filePath=aliOssUtil.upload(file.getBytes(),objectName);
            //返回文件请求路径给前端，前端就可以根据这个路径去阿里云服务器获取这张图片
            return Result.success(filePath);

        } catch (IOException e) { //如果抛出异常，说明上传失败
            log.error("上传失败:{}",e); //将异常信息e输出
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
