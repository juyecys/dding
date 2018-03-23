package cn.com.dingduoduo.api.admin.keyword;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.entity.keyword.LocalWechatKeyWord;
import cn.com.dingduoduo.service.keyword.LocalWechatKeyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jeysine on 2018/3/23.
 */
@RestController
@RequestMapping(value = { "/ding/wechat/private/keyword","/ding/wechat/public/keyword" }, produces = "application/json")
public class PrivateAdminKeyWordController {

    @Autowired
    private LocalWechatKeyWordService localWechatKeyWordService;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getPage(@RequestBody LocalWechatKeyWord localWechatKeyWord) throws Exception {
        Page<LocalWechatKeyWord> keyWordList = localWechatKeyWordService.findByConditionPage(localWechatKeyWord);
        return new ResponseEntity<>(ApiResult.success(keyWordList), HttpStatus.OK);
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody LocalWechatKeyWord localWechatKeyWord) throws Exception {
        localWechatKeyWord = localWechatKeyWordService.createOrUpdate(localWechatKeyWord);
        return new ResponseEntity<>(ApiResult.success(localWechatKeyWord), HttpStatus.OK);
    }
}
