package com.lx.xqgg.util;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

/**
 * Created by xiarh on 2017/12/15.
 */

public class CountDownTimerUtils extends CountDownTimer {

    private Button button;

    private Handler handler;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtils(Button button, long millisInFuture, long countDownInterval, Handler handler) {
        super(millisInFuture, countDownInterval);
        this.button = button;
        this.handler = handler;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        Log.e("zlz",millisUntilFinished+"");
        Log.e("zlz",((millisUntilFinished / 1000) % 60)+"");
        button.setClickable(false);
        button.setText(millisUntilFinished / 1000 + "s");//设置倒计时时间
        if ((millisUntilFinished / 1000)== 0) {//等于60秒就关闭
            handler.sendEmptyMessage(0);
        }
    }

    @Override
    public void onFinish() {
        Log.e("zlz","finish");
        button.setClickable(true);
        button.setText("获取验证码");
    }

}
