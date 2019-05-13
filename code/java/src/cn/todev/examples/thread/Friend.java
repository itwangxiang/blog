package cn.todev.examples.thread;

public class Friend {

	private final String name;

	public Friend(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public synchronized void bow(Friend firend) {
		System.out.format("%s: %s 向 %s 鞠躬! %n", Thread.currentThread().getName(), this.name, firend.getName());
		firend.bowBack(this);
	}

	public synchronized void bowBack(Friend bower) {
		System.out.format("%s: %s 向 %s 回礼! %n", Thread.currentThread().getName(), this.name, bower.getName());
	}

}
