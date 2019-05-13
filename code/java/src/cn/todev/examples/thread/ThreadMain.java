package cn.todev.examples.thread;

public class ThreadMain {

	public static void main(String[] args) {
		Friend zhangsan = new Friend("张三");
		Friend lisi = new Friend("李四");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				zhangsan.bow(lisi);
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				lisi.bow(zhangsan);
			}

		}).start();

	}

}
