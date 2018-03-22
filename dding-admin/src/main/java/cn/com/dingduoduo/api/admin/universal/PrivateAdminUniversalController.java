package cn.com.dingduoduo.api.admin.universal;


import cn.com.dingduoduo.api.common.ApiResult;
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
        String url;
        if (Objects.isNull(path)) {
            throw new AdminException(AdminException.AdminErrorCode.ERROR_PARAMETER);
        }
        try {
            InputStream inputStream = file.getInputStream();
            url = path + StringUtil.random(9).toUpperCase() + "_" + System.currentTimeMillis() + "." + FileUtils.getExtend(file.getOriginalFilename());
            aliyunContentStorageService.store(url, inputStream, file.getContentType());
        } catch (IOException | AliyunContentStorageException e) {
            logger.error("error: {}", e);
            throw e;
        }
        url = AliyunContentStorageUtils.getFullAccessUrlForKey(url);
        File file1 = new File();
        file1.setOriginName(file.getOriginalFilename());
        file1.setUrl(url);
        return new ResponseEntity<>(ApiResult.success(file1), HttpStatus.OK);
    }

    public class File {
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
}
