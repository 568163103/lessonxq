package com.mingsoft.nvssauthor.mapper;

import com.mingsoft.nvssauthor.domain.AlarmCloudStorage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AlarmCloudStorageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlarmCloudStorage record);

    int insertSelective(AlarmCloudStorage record);

    AlarmCloudStorage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlarmCloudStorage record);

    int updateByPrimaryKey(AlarmCloudStorage record);

    List<AlarmCloudStorage> findNewAlarmInfo();
}