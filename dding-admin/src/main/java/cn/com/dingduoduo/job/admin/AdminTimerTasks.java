package cn.com.dingduoduo.job.admin;

import cn.com.dingduoduo.entity.audio.AudioCourseDTO;
import cn.com.dingduoduo.entity.wechat.user.WechatUser;
import cn.com.dingduoduo.entity.wechat.user.WechatUserDTO;
import cn.com.dingduoduo.entity.wechatuser.LocalWechatUserDTO;
import cn.com.dingduoduo.job.DistributedExclusiveTask;
import cn.com.dingduoduo.service.audio.AudioCourseService;
import cn.com.dingduoduo.service.keyword.LocalWechatKeyWordService;
import cn.com.dingduoduo.service.wechat.user.WechatUserService;
import cn.com.dingduoduo.service.wechatuser.LocalWechatUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Component("adminTimerTasks")
@DistributedExclusiveTask("admin-timertasks")
public class AdminTimerTasks {

    @Autowired
    private AudioCourseService audioCourseService;

    private Logger logger = LoggerFactory.getLogger(AdminTimerTasks.class);

    @Scheduled(cron = "${timer.task.audio.course.add.people}")
    public void addAudioCoursePeople() throws Exception {
        List<AudioCourseDTO> list = audioCourseService.findByCondition(new AudioCourseDTO());
        for (AudioCourseDTO one: list) {
            Long peopleCount = one.getPeopleCount() == null? 0: one.getPeopleCount();
            one.setPeopleCount(peopleCount + 100 - new Random().nextInt(50));
            audioCourseService.createOrUpdate(one);
        }

    }
}
