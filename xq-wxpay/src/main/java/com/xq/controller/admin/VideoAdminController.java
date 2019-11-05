package com.xq.controller.admin;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date
 * @Version
 **/

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
@RequestMapping("/admin/api/v1/video")
public class VideoAdminController {


    @Autowired
    private VideoService videoService;

    /**
     * @param id 视频ID
     * @return 删除一条视频
     */
    @DeleteMapping("del_by_id")
    public Object delById(@RequestParam(value = "video_id", required = true) int id) {
        return videoService.delVideo(id);
    }

    /**
     * 更新一条视频
     *
     * @param id 视频ID
     * @return
     */
    @PutMapping("update_by_id")
    public Object updateById(@RequestParam(value = "video_id", required = true) int id) {
        Video video = new Video();
        video.setId(id);
        video.setCreateTime(new Date());
        return videoService.updateVideo(video);
    }

    /**
     * @param video 视频实体对象
     * @return id 新增视频id
     * 上传一条视频
     */
    @PostMapping(value = "save", produces = "application/json;charset=UTF-8")
    public Object save(@RequestBody Video video) {
        video.setCreateTime(new Date());
        return videoService.save(video);
    }
}
