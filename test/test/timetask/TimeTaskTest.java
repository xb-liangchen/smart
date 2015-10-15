package test.timetask;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 多线程
 * @author Administrator
 *
 */
public class TimeTaskTest {

	public static void main(String[] args) {
		
		
		Timer time1=new Timer();
		TimerTask task1=new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("执行1.。。。。。。");
			}
		};
		long delay=1000;//延时
		long period=1000*60;//周期  1分钟执行一次
		time1.schedule(task1,delay,period);
		
		Timer time2=new Timer();
		TimerTask task2=new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("执行2.。。。。。。");
			}
		};
		
		time2.schedule(task2,delay);
	}
}
