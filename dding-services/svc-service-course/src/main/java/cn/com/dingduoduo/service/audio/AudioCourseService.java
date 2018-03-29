package cn.com.dingduoduo.service.audio;

import cn.com.dingduoduo.entity.audio.AudioCourse;
import cn.com.dingduoduo.entity.audio.AudioCourseDTO;
import cn.com.dingduoduo.service.common.BaseService;

/**
 * Created by jeysine on 2018/3/16.
 */
public interface AudioCourseService extends BaseService<AudioCourse, AudioCourseDTO> {
    Integer countMinSequence();
}
