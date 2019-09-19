package com.beyeon.common.web.listener;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.hibernate.fivsdb.DmuAlarmInfo;
import com.beyeon.nvss.dmu.model.bpo.DmuAlarmInfoBpo;
import com.socket.netty.NettyChannelMap;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;
import com.socket.sip.bean.SIPRequestBean;
import com.socket.sip.process.query.CleanHistoryState;
import com.socket.sip.process.query.CollectDiskState;
import com.socket.sip.process.query.CollectEncoderState;
import com.socket.sip.process.query.CollectZabbixResState;

import io.netty.channel.socket.SocketChannel;

/**
 * 定时任务收集本级设备状态信息
 *
 * @author pc
 */
public class CollectResState implements Filter {
    /*** 时间间隔*/
    private static final long PERIOD_DAY = 60 * 1000;

    public void diskTimer() {
        Timer timer = new Timer();
        Integer period = ResourceUtil.getZabbixListenTime();
        if (period > 0) {
            timer.schedule(new TimerTask() {
                public void run() {
                    CollectDiskState collect = new CollectDiskState();
                    collect.init();
                }
            }, PERIOD_DAY, period * 1000);
        }

    }

    public void zabbixTimer() {
        Timer timer = new Timer();
        Integer period = ResourceUtil.getZabbixListenTime();
        if (period > 0) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    CollectZabbixResState collect = new CollectZabbixResState();
                    collect.init();
                }
            }, PERIOD_DAY, period * 1000);
        }

    }

    public void encoderTimer() {
        Timer timer = new Timer();
        Integer period = ResourceUtil.getEncoderListenTime();
        if (period > 0) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    CollectEncoderState collect = new CollectEncoderState();
                    collect.init();
                }
            }, PERIOD_DAY, period * 1000);
        }

    }

    public void alarmListenTimer() {
        Timer timer = new Timer();
        Integer alarmTime = ResourceUtil.getAlarmReportTime();
        if (alarmTime > 0) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    while (true) {
                        String userid = NettyChannelMap.getUserid();
                        if (StringUtils.isNotBlank(userid)) {
                            System.out.println("===上报告警信息==userid=" + userid);
                            SocketChannel socket = NettyChannelMap.get(userid);
                            if (socket != null) {
                                DmuAlarmInfoBpo dmuAlarmInfoBpo = (DmuAlarmInfoBpo) SpringInit.getApplicationContext().getBean("dmuAlarmInfoBpo");
                                List<DmuAlarmInfo> list = dmuAlarmInfoBpo.findNoneReportAlarm();
                                for (DmuAlarmInfo info : list) {
                                    String command = "ReportAlarmInfo";
                                    Map<String, Object> requestMap = new HashMap<String, Object>();
                                    requestMap.put("@command", command);
                                    HashMap<String, Object> parameters = new HashMap<String, Object>();
                                    HashMap<String, Object> group = new HashMap<String, Object>();
                                    HashMap<String, Object> url = new HashMap<String, Object>();
                                    url.put("id", info.getSourceId() + info.getAlarmType());
                                    url.put("type", info.getName());
                                    url.put("startTime", info.getBeginTime());
                                    url.put("endTime", info.getEndTime());
                                    url.put("targetInfo", info.getTargetInfo());
                                    url.put("level", info.getLevel());
                                    url.put("state", info.getState());
                                    url.put("description", info.getDescription());
                                    group.put("URL", url);
                                    parameters.put("group", group);
                                    parameters.put("dmuId", String.valueOf((long) (Math.random() * 1000000000)));
                                    requestMap.put("parameters", parameters);
                                    String localAddress = socket.localAddress().toString();
                                    String remoteAddress = socket.remoteAddress().toString();
                                    SIPRequestBean sip = SIPSocketUtil.createSIPRequest(localAddress, remoteAddress, requestMap);
                                    socket.writeAndFlush(sip.toString());

                                    dmuAlarmInfoBpo.updateStautus(info.getId(), "1");
                                }
                            }
                        }
                        try {
                            Thread.sleep(alarmTime * 1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
            }, PERIOD_DAY);
        }

    }

    public void cleanTimer() {
        Calendar calendar = Calendar.getInstance();
        // 控制时
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        // 控制分
        calendar.set(Calendar.MINUTE, 0);
        // 控制秒
        calendar.set(Calendar.SECOND, 0);
        // 得出执行任务的时间
        Date time = calendar.getTime();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int hours = new Date().getHours();
                if (hours > 0 && hours < 2) {
                    CleanHistoryState clean = new CleanHistoryState();
                    try {
                        clean.init();
                        ;
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                // 这里设定将延时每天固定执行
            }
        }, time, 1000 * 60 * 60 * 24);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String status = ResourceUtil.getZabbixStatus();
        if ("1".equals(status)) {
            this.encoderTimer();
            this.diskTimer();
            this.alarmListenTimer();
            this.zabbixTimer();
            this.cleanTimer();
        }


    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
}
