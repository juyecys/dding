package cn.com.dingduoduo.service.wechat.material.impl;


import cn.com.dingduoduo.utils.common.okhttputil.OkHttpUtils;
import cn.com.dingduoduo.contants.wechat.WechatConfigParams;
import cn.com.dingduoduo.entity.wechat.material.WechatMaterial;
import cn.com.dingduoduo.service.wechat.material.WechatMaterialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import cn.com.dingduoduo.service.wechat.accesstoken.WechatAccessTokenService;

import java.io.File;
import java.io.IOException;

/**
 * Created by jeysine on 2018/2/9.
 */
@Service("wechatMaterialService")
public class WechatMaterialServiceImpl implements WechatMaterialService {
    @Autowired
    private WechatAccessTokenService wechatAccessTokenService;

    private Logger logger = LoggerFactory.getLogger(WechatMaterialServiceImpl.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public WechatMaterial createForeverMaterial(MultipartFile multipartFile, String type) throws IOException {
        File file = getFileFromMultipartFile(multipartFile);
        String url = WechatConfigParams.WECHAT_ADD_MATERIAL.replace("ACCESS_TOKEN", wechatAccessTokenService.getAccessToken().getAccessToken())
                .replace("TYPE", type);
        logger.info("create forever material url:{}", url);
        int extra = multipartFile.getOriginalFilename().lastIndexOf('.');
        String fileName = null;
        if ((extra >-1) && (extra < (multipartFile.getOriginalFilename().length() - 1))) {
            fileName = multipartFile.getOriginalFilename().substring(extra + 1);
        }

        String result = OkHttpUtils.post().url(url).addFile("media",fileName, file).build().execute().body().string();
        logger.info("create forever material result: {}", result);
        return mapper.readValue(result, WechatMaterial.class);
    }

    private File getFileFromMultipartFile(MultipartFile file) {
        CommonsMultipartFile cf = (CommonsMultipartFile)file;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        return fi.getStoreLocation();
    }
}
