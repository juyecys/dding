package cn.com.dingduoduo.api.admin.ueditor;

import cn.com.dingduoduo.entity.ueditor.ActionEnter;
import cn.com.dingduoduo.exception.AdminException;
import cn.com.dingduoduo.exception.aliyun.oss.AliyunContentStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jeysine on 2018/3/29.
 */
@RestController
@RequestMapping(value = { "/ding/mg/private/ueditor", "/ding/mg/public/ueditor" }, produces = "application/json")
public class PrivateAdminUEditorController {

    private Logger logger = LoggerFactory.getLogger(PrivateAdminUEditorController.class);

    @RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<Object> getConfig(HttpServletRequest request, HttpServletResponse response, String action) throws AdminException, IOException, AliyunContentStorageException {
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        if("config".equals(action)){    //如果是初始化
            ActionEnter actionEnter = new ActionEnter(request, rootPath);
            String exec = actionEnter.exec();
            return new ResponseEntity<>(exec, HttpStatus.OK);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
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
