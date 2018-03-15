package cn.com.dingduoduo.entity.channelgroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.ibatis.type.Alias;

/**
 * Created by jeysine on 2018/2/21.
 */
@Alias("ChannelGroupQM")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY )
public class ChannelGroupDTO extends ChannelGroup {
}
