package cn.com.dingduoduo.service.audio;

import cn.com.dingduoduo.entity.audio.AudioLectureGroup;
import cn.com.dingduoduo.entity.audio.AudioLectureGroupDTO;
import cn.com.dingduoduo.service.common.BaseService;

import java.util.List;

/**
 * Created by jeysine on 2018/3/16.
 */
public interface AudioLectureGroupService extends BaseService<AudioLectureGroup, AudioLectureGroupDTO> {
    List<AudioLectureGroupDTO> createOrUpdateByBatch(List<AudioLectureGroupDTO> audioLectureGroupList) throws Exception;
}
