package LeetCode.QuestionBase;

import java.util.concurrent.Semaphore;

/**
 * 现在有两种线程，氢 oxygen 和氧 hydrogen，你的目标是组织这两种线程来产生水分子。
 * <p>
 * 存在一个屏障（barrier）使得每个线程必须等候直到一个完整水分子能够被产生出来。
 * <p>
 * 氢和氧线程会被分别给予 releaseHydrogen 和 releaseOxygen 方法来允许它们突破屏障。
 */
public class The1117Question {
    private Semaphore s1, s2, s3, s4;

    public The1117Question() {
        s1 = new Semaphore(2); // H线程信号量
        s2 = new Semaphore(1); // O线程信号量

        s3 = new Semaphore(0); // 反应条件信号量
        s4 = new Semaphore(0); // 反应条件信号量
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        s1.acquire(); // 保证只有2个H线程进入执行
        s3.release(); // 释放H原子到达信号
        s4.acquire(); // 等待O原子到达
        releaseHydrogen.run();
        s1.release(); // 相当于唤醒1个H线程
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        s2.acquire(); // 保证只有1个O线程进入执行
        s4.release(2); // 释放O原子到达信号，因为有2个H线程等待所以释放2个
        s3.acquire(2); // 等待H原子到达，2个原因同上
        releaseOxygen.run();
        s2.release(); // 相当于唤醒1个O线程
    }

    public static void main(String[] args) {
        The1117Question h2O = new The1117Question();
        int n = 5;
        new Thread(() -> {
            for (int i = 0; i < 2 * n; i++) {
                try {
                    h2O.hydrogen(() -> System.out.println("H"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < n; i++) {
                try {
                    h2O.oxygen(() -> System.out.println("O"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
