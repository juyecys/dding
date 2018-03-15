package cn.com.dingduoduo.service.channel.impl;

import cn.com.dingduoduo.dao.channel.ChannelDAO;
import cn.com.dingduoduo.entity.channel.Channel;
import cn.com.dingduoduo.service.channel.ChannelService;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.dingduoduo.entity.channel.ChannelDTO;

@Service("channelService")
public class ChannelServiceImpl extends BaseServiceImpl<Channel, ChannelDTO> implements ChannelService {
    @Autowired
    private ChannelDAO dao;

    @Autowired
    public void setDao(ChannelDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }
}
