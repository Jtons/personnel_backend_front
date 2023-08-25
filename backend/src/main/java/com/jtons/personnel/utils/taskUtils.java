package com.jtons.personnel.utils;

//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.testcreated.demo.entity.Attendance;
//import com.testcreated.demo.entity.UserInfo;
//import com.testcreated.demo.mapper.AttendanceMapper;
//import com.testcreated.demo.mapper.ToolOrderMapper;
//import com.testcreated.demo.mapper.UserInfoMapper;
//import com.testcreated.demo.service.AttendanceService;
//import com.testcreated.demo.service.ToolInfoLogService;
//import com.testcreated.demo.service.ToolOrderService;
//import com.testcreated.demo.vo.AttendanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class taskUtils {
//    @Resource
//    AttendanceMapper attendanceMapper;
//    @Autowired
//    AttendanceService attendanceService;
//    @Resource
//    UserInfoMapper userInfoMapper;
//    @Resource
//    ToolOrderMapper toolOrderMapper;
//    @Autowired
//    ToolOrderService toolOrderService;
//    @Autowired
//    ToolInfoLogService toolInfoLogService;
//    // 添加定时任务
//    // 每天中午12点  0 0 12 * * ?
//    @Scheduled(cron = "0 38 8 * * ?") // 7:30执行
//    public void doTask(){
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time=sdf.format(new Date());
//        System.out.println("我是定时任务~"+time);
//
//        // 昨天早上7：00
//        Calendar yestodayCalendar=Calendar.getInstance();
//        yestodayCalendar.setTime(new Date());
//        yestodayCalendar.set(Calendar.HOUR_OF_DAY, 7);
//        yestodayCalendar.set(Calendar.MINUTE, 0);
//        yestodayCalendar.set(Calendar.SECOND, 0);
//        yestodayCalendar.set(Calendar.MILLISECOND, 0);
//        yestodayCalendar.add(Calendar.DATE,-1);
//        String startTime=sdf.format(yestodayCalendar.getTime());
//
//        // 今天早上6：00
//        Calendar todayCalendar=Calendar.getInstance();
//        todayCalendar.setTime(new Date());
//        todayCalendar.set(Calendar.HOUR_OF_DAY, 6);
//        todayCalendar.set(Calendar.MINUTE, 0);
//        todayCalendar.set(Calendar.SECOND, 0);
//        todayCalendar.set(Calendar.MILLISECOND, 0);
//        String endTime=sdf.format(todayCalendar.getTime());
//        System.out.println("时间范围为："+startTime+"~"+endTime);
//        // 今日7：00
//        Calendar sevenClockCalendar=Calendar.getInstance();
//        sevenClockCalendar.setTime(new Date());
//        sevenClockCalendar.set(Calendar.HOUR_OF_DAY, 7);
//        sevenClockCalendar.set(Calendar.MINUTE, 0);
//        sevenClockCalendar.set(Calendar.SECOND, 0);
//        sevenClockCalendar.set(Calendar.MILLISECOND, 0);
//        String sevenClock=sdf.format(sevenClockCalendar.getTime());
//        //今日7：30
//        Calendar halfPastSevenCalendar=Calendar.getInstance();
//        halfPastSevenCalendar.setTime(new Date());
//        halfPastSevenCalendar.set(Calendar.HOUR_OF_DAY, 7);
//        halfPastSevenCalendar.set(Calendar.MINUTE, 30);
//        halfPastSevenCalendar.set(Calendar.SECOND, 0);
//        halfPastSevenCalendar.set(Calendar.MILLISECOND, 0);
//        String halfPastSeven=sdf.format(halfPastSevenCalendar.getTime());
//        //今日15：30
//        Calendar halfPastFifteenCalendar=Calendar.getInstance();
//        halfPastFifteenCalendar.setTime(new Date());
//        halfPastFifteenCalendar.set(Calendar.HOUR_OF_DAY, 15);
//        halfPastFifteenCalendar.set(Calendar.MINUTE, 30);
//        halfPastFifteenCalendar.set(Calendar.SECOND, 0);
//        halfPastFifteenCalendar.set(Calendar.MILLISECOND, 0);
//        String halfPastFifteen=sdf.format(halfPastFifteenCalendar.getTime());
//        //今日16：30
//        Calendar halfPastSixteenCalendar=Calendar.getInstance();
//        halfPastSixteenCalendar.setTime(new Date());
//        halfPastSixteenCalendar.set(Calendar.HOUR_OF_DAY, 16);
//        halfPastSixteenCalendar.set(Calendar.MINUTE, 30);
//        halfPastSixteenCalendar.set(Calendar.SECOND, 0);
//        halfPastSixteenCalendar.set(Calendar.MILLISECOND, 0);
//        String halfPastSixteen=sdf.format(halfPastSixteenCalendar.getTime());
//        System.out.println("几个重要的时间点--"+sevenClock+"\n"+halfPastSeven+"\n"+halfPastFifteen+"\n"+halfPastSixteen);
//        //今日
//        // 每日出勤情况
//        //1. 全打卡了，卡1时间大于当天7.30，卡2时间小于当天16.30-迟到
//        //2. 全打卡了，卡1时间大于当天7.30，卡2时间大于当天16.30-迟到
//        //3. 全打卡了，卡1时间在7.00-7.30之间，卡2时间小于当天16.30-早退
//        //4. 全打卡了，卡1时间在7.00-7.30之间，卡2时间大于当天16.30-出勤
//        //5. 全打卡了，卡1时间大于15.30-旷工
//        //10. 有卡1或者卡1卡2，type=5-事假
//        //11. 有卡1或者卡1卡2，type=6-病假
//        //12. 无打卡记录,type=5-事假
//        //13. 无打卡记录，type=6-病假
//        // -----------------前端已处理-------------------
//        //6. 只有卡1，卡1时间大于当天7.30，卡2为null，type=0,-迟到
//        //7. 只有卡1，卡1时间在7.00-7.30之间，卡2为null,type=0-早退
//        //8. 只有卡1，卡1时间大于当天15.30，type=0-旷工
//        //9. 无打卡记录，type=0-旷工
//
//        LambdaQueryWrapper<Attendance> attendanceLamb=new LambdaQueryWrapper<Attendance>();
//        attendanceLamb.isNull(Attendance::getDeleteAt);
//        attendanceLamb.eq(Attendance::getAuditStatus,2);
//        List<Attendance> attendanceList=attendanceMapper.selectList(attendanceLamb);
//        List<String> attendanceMember=attendanceList.stream().filter(item->{
//            if(item.getCardOne()!=null){
//                return startTime.compareTo(sdf.format(item.getCardOne()))<=0&&endTime.compareTo(sdf.format(item.getCardOne()))>=0;
//            }else if(item.getType()==5||item.getType()==6){
//                return startTime.compareTo(sdf.format(item.getCreateAt()))<=0&&endTime.compareTo(sdf.format(item.getCreateAt()))>=0;
//            }else if(item.getType()==4){
//                Calendar yesTodayCal=Calendar.getInstance();
//                yesTodayCal.setTime(item.getCreateAt());
//                yesTodayCal.add(Calendar.DATE,-1);
//                String yesterdayByNow= sdf.format(yesTodayCal.getTime());
//                return startTime.compareTo(yesterdayByNow)<=0&&endTime.compareTo(yesterdayByNow)>=0;
//            }else {
//                return false;
//            }
//        }).map(mapItem->{
//            return mapItem.getName();
//        }).collect(Collectors.toList());
//        attendanceList.forEach(item->{
//            if(item.getType()==0){
//                if(item.getCardOne()!=null&&item.getCardTwo()==null){//6.7.8.
//                    if(startTime.compareTo(sdf.format(item.getCardOne()))<=0&&endTime.compareTo(sdf.format(item.getCardOne()))>=0){//打卡时间在昨日7：00-今日6：00之间
//                        //进入此处意味着：type=0;有卡1；卡1时间范围在昨天7：00-今天6点之间
//                        //以下区分6.7.8.
//                        String cardOne= sdf.format(item.getCardOne());
//                        if(halfPastSeven.compareTo(cardOne)<0){//6
//                            attendanceService.changeType(item.getId(),2);
//                            System.out.println(item.getName()+"昨天仅打了卡1，且打卡时间超过7：30,视为迟到，具体打卡时间为"+cardOne);
//                        }else if(halfPastFifteen.compareTo(cardOne)<=0){//8
//                            attendanceService.changeType(item.getId(),4);
//                            System.out.println(item.getName()+"昨天仅打了卡1，且打卡时间超过15：30,视为旷工，具体打卡时间为"+cardOne);
//                        }else if(sevenClock.compareTo(cardOne)<=0&&halfPastSeven.compareTo(cardOne)>=0){//7
//                            attendanceService.changeType(item.getId(),3);
//                            System.out.println(item.getName()+"昨天仅打了卡1，打卡时间在7：00-7：30之间，由于仅打了一次卡,视为早退，具体打卡时间为"+cardOne);
//                        }
//                    }
//                }
//            }
//        });
//
//        LambdaQueryWrapper<UserInfo> userInfoLamb=new LambdaQueryWrapper<UserInfo>();
//        SimpleDateFormat sdfYMD=new SimpleDateFormat("yyyy-MM-dd");
//        userInfoLamb.isNull(UserInfo::getDeldateAt);
//        List<UserInfo> userInfoList= userInfoMapper.selectList(userInfoLamb);
//        Timestamp timestamp=new Timestamp(new Date().getTime());
//        userInfoList.forEach(item->{
//            if(item.getOutTime()==null||sdfYMD.format(item.getOutTime()).compareTo(sdfYMD.format(timestamp))>0){
//                if(attendanceMember.indexOf(item.getName())==-1&&!item.getRole().equals("contractor")){
//                    System.out.println(item.getName()+"昨天没打卡记录，视为旷工");
//                    AttendanceVo attendanceVo=new AttendanceVo();
//                    attendanceVo.setName(item.getName());
//                    attendanceVo.setAuditStatus(2);
//                    attendanceVo.setType(4);
//                    attendanceService.addAttendance(attendanceVo);
//                }
//            }
//        });
//
//    }
}
