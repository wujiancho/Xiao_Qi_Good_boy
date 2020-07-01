package com.lx.xqgg.ui.counter.bean;

public class CountBean {
    private String count;
    private double yg;
    private double ygbj;
    private double yglx;
    private double byye;

    public CountBean(String count, double yg, double ygbj, double yglx, double byye) {
        this.count = count;
        this.yg = yg;
        this.ygbj = ygbj;
        this.yglx = yglx;
        this.byye = byye;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public double getYg() {
        return yg;
    }

    public void setYg(double yg) {
        this.yg = yg;
    }

    public double getYgbj() {
        return ygbj;
    }

    public void setYgbj(double ygbj) {
        this.ygbj = ygbj;
    }

    public double getYglx() {
        return yglx;
    }

    public void setYglx(double yglx) {
        this.yglx = yglx;
    }

    public double getByye() {
        return byye;
    }

    public void setByye(double byye) {
        this.byye = byye;
    }
}
