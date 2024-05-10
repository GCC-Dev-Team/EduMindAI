package edumindai;

public class MyThread extends Thread{

    private String name;

    public MyThread(String name) {
        this.name = name;
    }
    @Override
    public void start() {
//        System.out.println("我是线程"+name);
//
////        try {
////            Thread.sleep(10000);
////        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
////        }
//
//        System.out.println("我执行完了");

        System.out.println("我是线程" + name);

        for (int i = 0; i < 10; i++) {
            System.out.println("线程" + name + " 正在执行任务：" + i);
            // Simulate some task execution with a delay
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("我执行完了");
    }
}
