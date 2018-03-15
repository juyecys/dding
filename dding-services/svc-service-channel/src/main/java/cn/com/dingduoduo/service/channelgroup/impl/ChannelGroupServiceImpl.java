package cn.com.dingduoduo.service.channelgroup.impl;

import cn.com.dingduoduo.utils.common.StringUtil;
import cn.com.dingduoduo.dao.channelgroup.ChannelGroupDAO;
import cn.com.dingduoduo.service.common.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.dingduoduo.entity.channelgroup.ChannelGroup;
import cn.com.dingduoduo.entity.channelgroup.ChannelGroupDTO;
import cn.com.dingduoduo.service.channelgroup.ChannelGroupService;

/**
 * Created by jeysine on 2018/2/21.
 */
@Service("channelGroupService")
public class ChannelGroupServiceImpl extends BaseServiceImpl<ChannelGroup, ChannelGroupDTO> implements ChannelGroupService {
    @Autowired
    private ChannelGroupDAO dao;

    @Autowired
    public void setDao(ChannelGroupDAO dao) {
        this.dao = dao;
        super.setDAO(dao);
    }

    @Override
    public ChannelGroup createOrUpdate(ChannelGroup entity) throws Exception {
        if (entity.getId() == null) {
            entity.setScene(StringUtil.numRandom(6));
            return create(entity);
        }

        return update(entity);
    }
}
