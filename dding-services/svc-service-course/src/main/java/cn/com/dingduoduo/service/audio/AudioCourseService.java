package cn.com.dingduoduo.service.audio;

import cn.com.dingduoduo.entity.audio.AudioCourse;
import cn.com.dingduoduo.entity.audio.AudioCourseDTO;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.service.common.BaseService;

import java.util.List;

/**
 * Created by jeysine on 2018/3/16.
 */
public interface AudioCourseService extends BaseService<AudioCourse, AudioCourseDTO> {
    Integer countMinSequence();
    List<AudioCourseDTO> findNeedOrderBySequence(AudioCourseDTO audioCourse);
}
