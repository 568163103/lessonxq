package com.mingsoft.nvssauthor.configuration;

import com.mingsoft.nvssauthor.domain.AlarmCpuInfo;
import com.mingsoft.nvssauthor.mapper.AlarmCpuInfoMapper;
import com.mingsoft.nvssauthor.mapper.AlarmDiskInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2019-12-24 14:24
 * @Version
 **/
@Component
public class ScheduledTasks {
    @Autowired
    private AlarmDiskInfoMapper alarmDiskInfoMapper;

    @Autowired
    private AlarmCpuInfoMapper alarmCpuInfoMapper;

//    @Scheduled(fixedRate = 18000000)
//    public void clearCpuInfo() {
//        System.out.println("Clear cpu info start");
//
//        alarmCpuInfoMapper.delAllCpuInfo();
//        System.out.println("Clear cpu info end");
//    }
//
//    @Scheduled(fixedRate = 18000000)
//    public void clearDiskInfo() {
//        System.out.println("Clear disk info start");
//
//        alarmDiskInfoMapper.delAllAlarmDiskInfo();
//
//        System.out.println("Clear disks info end");
//    }
}
