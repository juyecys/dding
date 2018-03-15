package cn.com.dingduoduo.dao.channel;

import cn.com.dingduoduo.dao.common.BaseDAO;
import org.springframework.stereotype.Component;
import cn.com.dingduoduo.entity.channel.Channel;
import cn.com.dingduoduo.entity.channel.ChannelDTO;

/**
 * Created by jeysine on 2018/2/21.
 */
@Component
public interface ChannelDAO extends BaseDAO<Channel, ChannelDTO> {
}
