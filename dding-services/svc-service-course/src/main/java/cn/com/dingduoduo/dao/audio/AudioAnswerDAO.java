package cn.com.dingduoduo.dao.audio;

import cn.com.dingduoduo.dao.common.BaseDAO;
import cn.com.dingduoduo.entity.audio.AudioAnswer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by jeysine on 2018/3/16.
 */
@Component
public interface AudioAnswerDAO extends BaseDAO<AudioAnswer, AudioAnswer> {
    Long countAudioAnswerByCourseId(@Param("courseId") String courseId);
}
