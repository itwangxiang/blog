package cn.todev.examples.event;

public class LifeCycleEvent {

    public Class mClass;

    public String mMethod;

    public LifeCycleEvent(Class clz, String method) {
        this.mClass = clz;
        this.mMethod = method;
    }

}
