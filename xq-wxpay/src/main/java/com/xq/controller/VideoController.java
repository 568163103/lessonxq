package com.xq.controller;

import com.xq.domain.Video;
import com.xq.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2019-11-05 14:52:22
 * @Version
 **/
@RestController
@RequestMapping("/api/v1/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @GetMapping("/page")
    public Object findAll() {

        return videoService.findAll();
    }


    @GetMapping("find_by_id")
    public Object findById(@RequestParam(value = "video_id", required = true) int id) {
        return videoService.findById(id);
    }


}
