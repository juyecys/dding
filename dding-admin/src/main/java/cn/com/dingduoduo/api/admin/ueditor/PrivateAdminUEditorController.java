package cn.com.dingduoduo.api.admin.ueditor;

import cn.com.dingduoduo.api.common.ApiResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jeysine on 2018/3/29.
 */
@RestController
@RequestMapping(value = { "/ding/mg/private/ueditor", "/ding/mg/public/ueditor" }, produces = "application/json")
public class PrivateAdminUEditorController {
    @Value("${ueditor.config}")
    private String ueditorConfig;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> getConfig() {

        return new ResponseEntity<>(ueditorConfig, HttpStatus.OK);
    }
}
