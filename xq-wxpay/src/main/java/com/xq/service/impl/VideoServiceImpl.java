package com.xq.service.impl;

import com.github.pagehelper.PageHelper;
import com.xq.domain.Video;
import com.xq.mapper.VideoMapper;
import com.xq.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date  2019-11-05 14:52:22
 * @Version
 **/
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;


    @Override
    public List<Video> findAll() {
        return videoMapper.findAll();
    }

    @Override
    public Video findById(int id) {
        return videoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateVideo(Video video) {
        return videoMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    public int delVideo(int id) {
        return videoMapper.deleteByPrimaryKey(id);
    }
}
