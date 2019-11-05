package com.xq.service;

import com.xq.domain.Video;
import com.xq.service.impl.VideoServiceImpl;

import java.util.List;

/**
 * {@link  VideoServiceImpl}
 * @author mac-xq
 * @ClassName VideoService
 * @Description
 * @Date  2019-11-05 14:52:22
 * @Version
 **/
public interface VideoService {

    List<Video> findAll();

    Video findById(int id);

    int updateVideo(Video video);

    int delVideo(int id);

    int save(Video video);
}
