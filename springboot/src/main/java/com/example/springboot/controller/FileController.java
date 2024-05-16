package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("file")
public class FileController {
    @Value("${ip:localhost}") //默认值Localhost,
    private String ip;

    @Value("${server.port}")
    private String port;
    @Value("${myapp.global.staticVar.ip}")

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";//文件根目录

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();//获取文件的完整名称
        String mainName = FileUtil.mainName(originalFilename);//获取文件名称
        String extName = FileUtil.extName(originalFilename);//获取文件拓展名
        if (!FileUtil.exist(ROOT_PATH)) {
            FileUtil.mkdir(ROOT_PATH);
        }

        String randomFileName;
        do {
            randomFileName = IdUtil.randomUUID() + "." + extName;
        } while (FileUtil.exist(ROOT_PATH + File.separator + randomFileName));

        File saveFile = new File(ROOT_PATH + File.separator + randomFileName);
        file.transferTo(saveFile);
        String url = "http://localhost:" + port + "/file/download/" + randomFileName;
        return Result.success(url);//返回文件下载链接
    }

    @AuthAccess//下载不需要权限
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {//路径传参，获取上传接口返回路径里的文件名参数
        String filePath=ROOT_PATH+File.separator+fileName;
        if(!FileUtil.exist(filePath)){//文件不存在
            return ;
        }
        byte[] bytes=FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

}
