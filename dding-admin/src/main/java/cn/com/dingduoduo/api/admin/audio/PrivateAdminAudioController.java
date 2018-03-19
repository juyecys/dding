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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/ding/mg/private/audio", "/ding/mg/public/audio"}, produces = "application/json")
public class PrivateAdminAudioController {

    @Autowired
    private AudioCourseService audioCourseService;

    @Autowired
    private AudioLectureService audioLectureService;

    @Autowired
    private AudioLectureGroupService audioLectureGroupService;

    @RequestMapping(value = "/course", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveOrUpdateCourse(@RequestBody AudioCourseDTO audioCourse) throws Exception {
        //audioCourse = audioCourseService.createOrUpdate(audioCourse);
        return new ResponseEntity<>(ApiResult.success(audioCourse), HttpStatus.OK);
    }

    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getCoursePage(AudioCourseDTO audioCourse) throws Exception {
        Page<AudioCourseDTO> page = audioCourseService.findByConditionPage(audioCourse);
        return new ResponseEntity<>(ApiResult.success(page), HttpStatus.OK);
    }

    @RequestMapping(value = "/lecture", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveOrUpdateLecture(@RequestBody AudioLecture audioLecture) throws Exception {
        audioLecture = audioLectureService.createOrUpdate(audioLecture);
        return new ResponseEntity<>(ApiResult.success(audioLecture), HttpStatus.OK);
    }
}
