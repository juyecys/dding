package cn.com.dingduoduo.dao.audio;

import cn.com.dingduoduo.dao.common.BaseDAO;
import cn.com.dingduoduo.entity.audio.AudioCourse;
import org.springframework.stereotype.Component;

/**
 * Created by jeysine on 2018/3/16.
 */
@Component
public interface AudioCourseDAO extends BaseDAO<AudioCourse, AudioCourse>{
    Integer countMinSequence();
}
