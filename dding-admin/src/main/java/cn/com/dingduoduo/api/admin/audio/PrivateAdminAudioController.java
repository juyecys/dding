package cn.com.dingduoduo.api.admin.audio;

import cn.com.dingduoduo.api.common.ApiResult;
import cn.com.dingduoduo.entity.audio.*;
import cn.com.dingduoduo.entity.common.Page;
import cn.com.dingduoduo.service.audio.AudioCourseService;
import cn.com.dingduoduo.service.audio.AudioLectureGroupService;
import cn.com.dingduoduo.service.audio.AudioLectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/ding/mg/private/audio", "/ding/mg/public/audio"}, produces = "application/json")
public class PrivateAdminAudioController {

    @Autowired
    private AudioCourseService audioCourseService;

    @Autowired
    private AudioLectureGroupService audioLectureGroupService;

    @RequestMapping(value = "/course", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveOrUpdateCourse(@RequestBody AudioCourse audioCourse) throws Exception {
        audioCourse = audioCourseService.createOrUpdate(audioCourse);
        return new ResponseEntity<>(ApiResult.success(audioCourse), HttpStatus.OK);
    }

    @RequestMapping(value = "/course/page", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getCoursePage(@RequestBody AudioCourseDTO audioCourse) throws Exception {
        Page<AudioCourseDTO> page = audioCourseService.findByConditionPage(audioCourse);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }

    @RequestMapping(value = "/lectureGroup", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveOrUpdateLecture(@RequestBody List<AudioLectureGroupDTO> audioLectureGroupList) throws Exception {
        audioLectureGroupList = audioLectureGroupService.createOrUpdateByBatch(audioLectureGroupList);
        return new ResponseEntity<>(ApiResult.success(audioLectureGroupList), HttpStatus.OK);
    }

    @RequestMapping(value = "/lectureGroup", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getLectureGroupList(AudioLectureGroupDTO audioLectureGroup) throws Exception {
        List<AudioLectureGroupDTO> audioLectureGroupList = audioLectureGroupService.findByCondition(audioLectureGroup);
        return new ResponseEntity<>(ApiResult.success(audioLectureGroupList), HttpStatus.OK);
    }
}
