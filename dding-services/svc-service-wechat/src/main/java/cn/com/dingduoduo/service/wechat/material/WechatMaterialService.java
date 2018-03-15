package cn.com.dingduoduo.service.wechat.material;

import org.springframework.web.multipart.MultipartFile;
import cn.com.dingduoduo.entity.wechat.material.WechatMaterial;

import java.io.IOException;

/**
 * Created by jeysine on 2018/2/9.
 */
public interface WechatMaterialService {
    WechatMaterial createForeverMaterial(MultipartFile file, String type) throws IOException;
}
