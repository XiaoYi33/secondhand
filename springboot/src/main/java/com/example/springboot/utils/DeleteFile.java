package com.example.springboot.utils;

import java.io.File;

public class DeleteFile {
    public static void deleteImage(String imageUrl) throws Exception {
        // 从图片地址中提取文件名
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

        // 拼接本地文件路径，这里假设图片存放在 /path/to/your/image/directory 目录下
        String filePath = "files/" + fileName;

        File file = new File(filePath);

        // 判断文件是否存在
        if (file.exists()) {
            // 尝试删除文件
            if (!file.delete()) {
                throw new Exception("文件删除失败：" + filePath);
            }
        } else {
            throw new Exception("文件不存在：" + filePath);
        }
    }
}
