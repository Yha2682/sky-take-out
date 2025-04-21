package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "文件上传")
public class CommonController {

    private static final String FILE_UPLOAD_PATH = "E:\\SpringBoot\\sky-take-out\\images";


    @PostMapping("/upload")
    @ApiOperation("文件上传")
    @ResponseBody
    public Result<String> upload(@RequestParam("file")MultipartFile file) {
        log.info("文件上传：{}", file);
        if (file.isEmpty()) {
            return Result.error("文件夹不能为空");
        }

        File dir = new File(FILE_UPLOAD_PATH);
        if (!dir.exists() || !dir.isDirectory()) {
            boolean created = dir.mkdirs();
            if (created) {
                log.info("创建文件夹成功: {}", FILE_UPLOAD_PATH);
            } else {
                log.warn("创建文件夹失败或已经存在: {}", FILE_UPLOAD_PATH);
            }
        }
        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isEmpty()) {
            return Result.error("文件名无效");
        }

        // 获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!suffix.equalsIgnoreCase(".jpg") &&
                !suffix.equalsIgnoreCase(".jpeg") &&
                !suffix.equalsIgnoreCase(".png")) {
            return Result.error("文件格式不支持");
        }
        // 使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        // 确保文件路径安全，避免路径遍历攻击
        Path targetLocation = Paths.get(FILE_UPLOAD_PATH).resolve(fileName).normalize();
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("文件上传成功: {}", fileName);
        } catch (IOException e) {
            log.error("文件上传失败: {}", fileName, e);
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }

        // 你可以根据实际情况调整返回的文件访问链接
        String fileUrl = "http://localhost:8080/images/" + fileName;
        return Result.success(fileUrl);

    }
}
