package com.xq.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xq.domain.Video;
import com.xq.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    public Object findAll(@RequestParam(value = "page_num", defaultValue = "1") int pageNum,
                          @RequestParam(value = "page_size", defaultValue = "10") int pageSize
    ) {
        PageHelper.startPage(pageNum, pageSize);
        List<Video> list = videoService.findAll();
        PageInfo<Video> pageInfo = new PageInfo<>(list);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("page_num", pageNum);
        map.put("page_size", pageInfo.getPageSize());
        map.put("page_total", pageInfo.getTotal());
        map.put("page_pages", pageInfo.getPages());
        map.put("data", pageInfo.getList());
        return map;
    }


    @GetMapping("find_by_id")
    public Object findById(@RequestParam(value = "video_id", required = true) int id) {
        return videoService.findById(id);
    }


}
