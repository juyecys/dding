package cn.com.dingduoduo.service.wechat.message.impl;

import cn.com.dingduoduo.utils.common.AliyunContentStorageUtils;
import cn.com.dingduoduo.utils.common.FileUtils;
import cn.com.dingduoduo.utils.common.StringUtil;
import cn.com.dingduoduo.utils.common.okhttputil.OkHttpUtils;
import cn.com.dingduoduo.contants.wechat.WechatConfigParams;
import cn.com.dingduoduo.entity.message.Article;
import cn.com.dingduoduo.entity.message.Message;
import cn.com.dingduoduo.entity.wechat.acesstoken.WechatAccessToken;
import cn.com.dingduoduo.entity.wechat.localwechatmenu.LocalWechatMenu;
import cn.com.dingduoduo.entity.wechat.message.WechatCustomMessage;
import cn.com.dingduoduo.entity.wechat.message.WechatTemplateMessage;
import cn.com.dingduoduo.entity.wechat.result.WechatCommonResult;
import cn.com.dingduoduo.service.message.MessageService;
import cn.com.dingduoduo.service.wechat.accesstoken.WechatAccessTokenService;
import cn.com.dingduoduo.service.wechat.message.WechatMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jeysine on 2018/1/25.
 */
@Service("wechatMessageService")
public class WechatMessageServiceImpl implements WechatMessageService {

    @Autowired
    private WechatAccessTokenService wechatAccessTokenService;

    @Autowired
    private MessageService messageService;

    private static final ObjectMapper mapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(WechatMessageServiceImpl.class);
    @Override
    public WechatCommonResult pushMessage(WechatCustomMessage wechatCustomMessage) throws IOException {
        WechatAccessToken accessToken = wechatAccessTokenService.getAccessToken();
        String url = WechatConfigParams.WECHAT_CUSTOM_MESSAGE.replace("ACCESS_TOKEN", accessToken.getAccessToken());
        String messageJson = mapper.writeValueAsString(wechatCustomMessage);
        logger.debug("send message to wechat user: {}", messageJson);
        String result = OkHttpUtils.postString().url(url).content(messageJson).build().execute().body().string();
        logger.debug("send message to wechat user result: {}", result);
        return mapper.readValue(result,WechatCommonResult.class);
    }

    @Override
    public WechatCommonResult pushNewsMessageByMenuEvent(String openId, LocalWechatMenu menu) throws IOException {
        WechatCustomMessage message = new WechatCustomMessage();

        WechatCustomMessage.News news = message.new News();

        WechatCustomMessage.News.Article articl = news.new Article();
        List<WechatCustomMessage.News.Article> articls = new LinkedList<>();
        articl.setDescription(menu.getContent());
        articl.setTitle(menu.getTitle());
        articl.setPicurl(AliyunContentStorageUtils.getFullAccessUrlForKey(menu.getImgUrl()));
        articl.setUrl(menu.getUrl());
        articls.add(articl);

        news.setArticles(articls);
        return pushNewsMessage(openId, articls);
    }

    @Override
    public WechatCommonResult pushTextMessageByMenuEvent(String openId, LocalWechatMenu menu) throws IOException {
        return pushTextMessage(openId, menu.getContent());
    }

    @Override
    public WechatCommonResult pushNewsMessage(String openId, List<WechatCustomMessage.News.Article> articleList) throws IOException {
        WechatCustomMessage message = new WechatCustomMessage();

        WechatCustomMessage.News news = message.new News();

        news.setArticles(articleList);

        message.setTouser(openId);
        message.setNews(news);
        message.setMsgtype(WechatCustomMessage.MsgTypeEnum.NEWS.getValue());
        return pushMessage(message);
    }


    @Override
    public WechatCommonResult pushTextMessage(String openId, String content) throws IOException {
        WechatCustomMessage message = new WechatCustomMessage();
        WechatCustomMessage.Text text = message.new Text();
        text.setContent(content);
        message.setTouser(openId);
        message.setText(text);
        message.setMsgtype(WechatCustomMessage.MsgTypeEnum.TEXT.getValue());
        return pushMessage(message);
    }

    @Override
    public WechatCommonResult pushImageMessage(String openId, String mediaId) throws IOException {
        WechatCustomMessage message = new WechatCustomMessage();
        WechatCustomMessage.Image image = message.new Image();
        image.setMediaId(mediaId);

        message.setTouser(openId);
        message.setImage(image);
        message.setMsgtype(WechatCustomMessage.MsgTypeEnum.IMAGE.getValue());
        return pushMessage(message);
    }

    @Override
    public WechatCommonResult pushTemplateMessage(WechatTemplateMessage templateMessage) throws IOException {
        WechatAccessToken accessToken = wechatAccessTokenService.getAccessToken();
        String url = WechatConfigParams.WECHAT_SEND_TEMPLATE_MESSAGE.replace("ACCESS_TOKEN", accessToken.getAccessToken());
        String messageJson = mapper.writeValueAsString(templateMessage);
        logger.debug("send message to wechat user: {}", messageJson);
        String result = OkHttpUtils.postString().url(url).content(messageJson).build().execute().body().string();
        logger.debug("send message towechat user result: {}", result);
        return mapper.readValue(result,WechatCommonResult.class);
    }

    @Override
    public void pushMessageByMessage(String openId, Message message) throws IOException {
        Message.MsgTypeEnum msgTypeEnum = Message.MsgTypeEnum.getEnumByValue(message.getMsgType());
        switch (msgTypeEnum) {
            case TEXT:
                pushTextMessage(openId, message.getContent());
                break;
            case IMAGE:
                pushImageMessage(openId, message.getMediaId());
                break;
            case MUSIC:
                break;
            case VIDEO:
                break;
            case VOICE:
                break;
            case MPNEWS:
                break;
            case WXCARD:
                break;
            case ARTICLE:
            case ARTICLE_LIST:
                List<WechatCustomMessage.News.Article> list = generateArticleList(message.getArticleList());
                pushNewsMessage(openId, list);
                break;
        }
    }

    @Override
    public void pushMessageListByMessage(String openId, List<Message> messageList) throws IOException {
        if (!StringUtil.isEmpty(openId) && !messageList.isEmpty()) {
            for(Message message: messageList) {
                pushMessageByMessage(openId, message);
            }
        }
    }

    @Override
    public void pushSubscribeMessage(String openid) throws IOException {
        List<Message> messageList = messageService.findByStatusAndTypeOrderBySequence(true, Message.TypeEnum.SUBSCRIBE.name());
        logger.debug("subscribe messageList: {}", messageList);
        pushMessageListByMessage(openid, messageList);
    }

    @Override
    public void pushKeyWordMessage(String openid, String keyWord) throws IOException {
        List<Message> messageList = messageService.findByStatusAndKeyWordOrderBySequence(true, keyWord);
        logger.debug("keyword messageList: {}", messageList);
        pushMessageListByMessage(openid, messageList);
    }

    @Override
    public void pushChannelsMessage(String openId, String qrCodeScene) throws IOException {
        List<Message> messageList = messageService.findByStatusAndTypeAndQrCodeSceneOrderBySequence(true, Message.TypeEnum.CHANNEL.name(), qrCodeScene);
        logger.debug("channel messageList: {}", messageList);
        pushMessageListByMessage(openId, messageList);
    }

    private List<WechatCustomMessage.News.Article> generateArticleList(List<Article> articles) {
        WechatCustomMessage wechatCustomMessage = new WechatCustomMessage();
        WechatCustomMessage.News news = wechatCustomMessage.new News();
        WechatCustomMessage.News.Article article = news.new Article();
        List<WechatCustomMessage.News.Article> list = new LinkedList<>();
        for (Article one: articles) {
            article.setUrl(one.getUrl());
            article.setDescription(one.getDescription());
            article.setPicurl(one.getPicUrl());
            article.setTitle(one.getTitle());
            list.add(article);
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        //System.out.println(data);
        String[] dataArr = FileUtils.readFileAsString("C:\\Users\\Administrator\\Desktop\\openIdList.json").split(",");
        //System.out.println(dataArr.toString());
        String url = "https://api.weixin.qq.com/cgi-bin/cn.com.dingduoduo.api.admin.adminuser.message/template/send?access_token=7_As0Xx_njdhgXoxk82w3iX3s4heAAlGoXMjqAE8YiCnPJ0SKWgjCc_0RoKCT_sB-L5D6uyqBk-0UMrrX46vUgfu8Cknr9xx9_1SFye3jTSBucBoDueEOk8pcE1cwV341tq_fwUqfg-ShYI2BoOQCaAAAWYK";
        WechatTemplateMessage wechatTemplateMessage = new WechatTemplateMessage();
        WechatTemplateMessage.Data data = wechatTemplateMessage.new Data();
        WechatTemplateMessage.DataContent first = wechatTemplateMessage.new DataContent();
        WechatTemplateMessage.DataContent keyword1 = wechatTemplateMessage.new DataContent();
        WechatTemplateMessage.DataContent keyword2 = wechatTemplateMessage.new DataContent();
        WechatTemplateMessage.DataContent keyword3 = wechatTemplateMessage.new DataContent();


        first.setValue("橙医生联合了21名三甲医院名医开展心脑微课堂，准备好小板凳速度进场吧~\n");
        keyword1.setValue("多学点心脑健康知识，让你多活10年");
        keyword2.setValue("3月9日 18:00");
        keyword3.setValue("橙医生心脑课堂");

        data.setFirst(first);
        data.setKeyword1(keyword1);
        data.setKeyword2(keyword2);
        data.setKeyword3(keyword3);
        wechatTemplateMessage.setTemplateId("hjVDgLDjDYqZgywplHM8Bz1nl1YIxfWOlS3T0laaY8k");

        wechatTemplateMessage.setData(data);
        wechatTemplateMessage.setUrl("http://weike.fm/sXiKA3");

        for (int i = 44661; i < dataArr.length ; i++) {
            try {
                String one = dataArr[i].replaceAll("\"", "");
                wechatTemplateMessage.setTouser(one);
                String dataJson = mapper.writeValueAsString(wechatTemplateMessage);
                //System.out.println(dataJson);
                String result = OkHttpUtils.postString().url(url)
                        .content(dataJson)
                        .build().execute().body().string();
                System.out.println(result + ",count: "+ i + ",openid: " + one);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
