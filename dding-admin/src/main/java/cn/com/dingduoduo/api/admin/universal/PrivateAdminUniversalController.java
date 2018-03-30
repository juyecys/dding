package cn.com.dingduoduo.api.admin.universal;


import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.constants.AliyunOssPath;
import cn.com.dingduoduo.entity.aliyun.oss.AliyunContentStorageResult;
import cn.com.dingduoduo.exception.AdminException;
import cn.com.dingduoduo.exception.aliyun.oss.AliyunContentStorageException;
import cn.com.dingduoduo.service.aliyun.oss.AliyunContentStorageService;
import cn.com.dingduoduo.utils.common.AliyunContentStorageUtils;
import cn.com.dingduoduo.utils.common.FileUtils;
import cn.com.dingduoduo.utils.common.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping(value = { "/ding/mg/private/file","/ding/mg/public/file" }, produces = "application/json")
public class PrivateAdminUniversalController {

    @Autowired
    private AliyunContentStorageService aliyunContentStorageService;

    private Logger logger = LoggerFactory.getLogger(PrivateAdminUniversalController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) throws IOException, AliyunContentStorageException, AdminException {
        if (Objects.isNull(path)) {
            throw new AdminException(AdminException.AdminErrorCode.ERROR_PARAMETER);
        }
        AliyunContentStorageResult aliyunContentStorageResult = aliyunContentStorageService.storeCommon(file.getOriginalFilename(), file.getBytes(), file.getContentType(), path);

        UploadFileResult file1 = new UploadFileResult();
        file1.setOriginName(file.getOriginalFilename());
        file1.setUrl(aliyunContentStorageResult.getResourceUrl());
        return new ResponseEntity<>(ApiResult.success(file1), HttpStatus.OK);
    }

    @RequestMapping(value = "/ueditorUpload", method = RequestMethod.POST)
    public ResponseEntity<UEditorUploadFileResult> ueditorUploadFile(@RequestParam("upfile") MultipartFile file) throws IOException, AliyunContentStorageException, AdminException {
        String path = AliyunOssPath.UEDITOR_IMG_FILEPATH;
        AliyunContentStorageResult aliyunContentStorageResult = aliyunContentStorageService.storeCommon(file.getOriginalFilename(), file.getBytes(), file.getContentType(), path);

        UEditorUploadFileResult file1 = new UEditorUploadFileResult();
        file1.setOriginal(file.getOriginalFilename());
        file1.setTitle(aliyunContentStorageResult.getResourceUrl());
        file1.setUrl(aliyunContentStorageResult.getResourceUrl());
        file1.setSize(String.valueOf(file.getSize()));
        file1.setType(FileUtils.getExtend(file.getOriginalFilename()));
        return new ResponseEntity<>(file1, HttpStatus.OK);
    }


    public class UploadFileResult {
        private String url;
        private String originName;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getOriginName() {
            return originName;
        }

        public void setOriginName(String originName) {
            this.originName = originName;
        }

        @Override
        public String toString() {
            return "File{" +
                    "url='" + url + '\'' +
                    ", originName='" + originName + '\'' +
                    '}';
        }
    }

    public class UEditorUploadFileResult {
        private String url;
        private String original;
        private String title;
        private String state="SUCCESS";
        private String size = "32455";
        private String type = ".jpg";

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getOriginal() {
            return original;
        }

        public void setOriginal(String original) {
            this.original = original;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "UEditorUploadFileResult{" +
                    "url='" + url + '\'' +
                    ", original='" + original + '\'' +
                    ", title='" + title + '\'' +
                    ", state='" + state + '\'' +
                    ", size='" + size + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
