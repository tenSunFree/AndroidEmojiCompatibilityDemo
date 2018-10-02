package com.home.androidemojicompatibilitydemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.home.androidemojicompatibilitydemo.view.Custom3DAnimation;

public class Main2Activity extends AppCompatActivity {

    private int cumulativeFlops, isSpecialModeNumber, currentMilliSecond;
    private boolean isSpecialMode, isSuccessfulAnimation, ispositive, ispositive1, ispositive2,
            ispositive3, ispositive4, ispositive5, ispositive6, ispositive7, ispositive8, onPlaying;

    private Custom3DAnimation anim, anim1;
    private FrameLayout firstOpenFrameLayout, secondOpenFrameLayout, frameLayout1, frameLayout2,
            frameLayout3, frameLayout4, frameLayout5, frameLayout6, frameLayout7, frameLayout8;
    private EmojiCompat.Config config;
    private EmojiTextView firstOpenEmojiTextView, secondOpenEmojiTextView, emojiTextView1,
            emojiTextView2, emojiTextView3, emojiTextView4, emojiTextView5, emojiTextView6,
            emojiTextView7, emojiTextView8;
    private TimeCountingAsyncTask timeCountingAsyncTask;
    private TextView minuteTextView, secondTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializationEmojiCompat();
        setContentView(R.layout.activity_main2);

        initializationParameters();
        initializationEmojiTextView();
        initializationFrameLayout();
        initializationTimeCounting();
    }

    /**
     * 初始化EmojiCompat
     */
    private void initializationEmojiCompat() {
        config = new BundledEmojiCompatConfig(this)
                .setReplaceAll(true)
                .setEmojiSpanIndicatorEnabled(true)
                .setEmojiSpanIndicatorColor(0xffeecc8d);
        EmojiCompat.init(config);
    }

    private void initializationParameters() {
        isSuccessfulAnimation = false;
        isSpecialModeNumber = 0;
        isSpecialMode = false;
        cumulativeFlops = 0;
        ispositive1 = false;
        ispositive2 = false;
        ispositive3 = false;
        ispositive4 = false;
        ispositive5 = false;
        ispositive6 = false;
        ispositive7 = false;
        ispositive8 = false;
        onPlaying = true;
        currentMilliSecond = 0;
    }

    private void initializationEmojiTextView() {
        emojiTextView1 = findViewById(R.id.emojiTextView1);
        emojiTextView1.setText(new StringBuilder(new String(Character.toChars(0x1F62C))));
        emojiTextView2 = findViewById(R.id.emojiTextView2);
        emojiTextView2.setText(new StringBuilder(new String(Character.toChars(0x1F61B))));
        emojiTextView3 = findViewById(R.id.emojiTextView3);
        emojiTextView3.setText(new StringBuilder(new String(Character.toChars(0x1F62C))));
        emojiTextView4 = findViewById(R.id.emojiTextView4);
        emojiTextView4.setText(new StringBuilder(new String(Character.toChars(0x1F60E))));
        emojiTextView5 = findViewById(R.id.emojiTextView5);
        emojiTextView5.setText(new StringBuilder(new String(Character.toChars(0x1F60E))));
        emojiTextView6 = findViewById(R.id.emojiTextView6);
        emojiTextView6.setText(new StringBuilder(new String(Character.toChars(0x1F634))));
        emojiTextView7 = findViewById(R.id.emojiTextView7);
        emojiTextView7.setText(new StringBuilder(new String(Character.toChars(0x1F634))));
        emojiTextView8 = findViewById(R.id.emojiTextView8);
        emojiTextView8.setText(new StringBuilder(new String(Character.toChars(0x1F61B))));
    }

    private void initializationFrameLayout() {
        frameLayout1 = findViewById(R.id.frameLayout1);
        frameLayout2 = findViewById(R.id.frameLayout2);
        frameLayout3 = findViewById(R.id.frameLayout3);
        frameLayout4 = findViewById(R.id.frameLayout4);
        frameLayout5 = findViewById(R.id.frameLayout5);
        frameLayout6 = findViewById(R.id.frameLayout6);
        frameLayout7 = findViewById(R.id.frameLayout7);
        frameLayout8 = findViewById(R.id.frameLayout8);
        frameLayout1.setOnClickListener(onClickListener(frameLayout1, emojiTextView1, 1));
        frameLayout2.setOnClickListener(onClickListener(frameLayout2, emojiTextView2, 2));
        frameLayout3.setOnClickListener(onClickListener(frameLayout3, emojiTextView3, 3));
        frameLayout4.setOnClickListener(onClickListener(frameLayout4, emojiTextView4, 4));
        frameLayout5.setOnClickListener(onClickListener(frameLayout5, emojiTextView5, 5));
        frameLayout6.setOnClickListener(onClickListener(frameLayout6, emojiTextView6, 6));
        frameLayout7.setOnClickListener(onClickListener(frameLayout7, emojiTextView7, 7));
        frameLayout8.setOnClickListener(onClickListener(frameLayout8, emojiTextView8, 8));
    }

    private void initializationTimeCounting() {
        minuteTextView = findViewById(R.id.minuteTextView);
        secondTextView = findViewById(R.id.secondTextView);
        timeCountingAsyncTask = new TimeCountingAsyncTask();
        timeCountingAsyncTask.execute();
    }

    /**
     * 點擊監聽, 紀錄是否翻過面, 以及翻轉時的動畫呈現..等
     */
    @NonNull
    private View.OnClickListener onClickListener(
            final FrameLayout frameLayout, final EmojiTextView emojiTextView, final int number) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeIsPositive(number);
                anim = new Custom3DAnimation(
                        frameLayout.getWidth() / 2,
                        frameLayout.getHeight() / 2,
                        0,
                        90,
                        0,
                        Custom3DAnimation.AXIS_Y);
                anim.setDuration(500);
                anim.setAnimationListener(animationListener(frameLayout, emojiTextView));
                frameLayout.startAnimation(anim);
            }
        };
    }

    /**
     * 翻轉時的前半部動畫監聽
     */
    @NonNull
    private Animation.AnimationListener animationListener(final FrameLayout frameLayout, final EmojiTextView emojiTextView) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                allFrameLayoutNoClick();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // frameLayout.setClickable(true);
                if (ispositive == true) {
                    emojiTextView.setVisibility(View.VISIBLE);
                } else {
                    emojiTextView.setVisibility(View.INVISIBLE);
                }
                anim1 = new Custom3DAnimation(
                        frameLayout.getWidth() / 2,
                        frameLayout.getHeight() / 2,
                        -90,
                        0,
                        0,
                        Custom3DAnimation.AXIS_Y);
                anim1.setDuration(250);
                anim1.setAnimationListener(animationListener2());
                anim1.setInterpolator(new AccelerateInterpolator());
                frameLayout.startAnimation(anim1);
            }
        };
    }

    /**
     * 翻轉時的後半部動畫監聽
     */
    @NonNull
    private Animation.AnimationListener animationListener2() {
        return new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            /** 如果已經翻了兩張牌, 就比對是否一樣, 如果一樣就顯示成功動畫, 如果不一樣就讓它們變回反面 */
            @Override
            public void onAnimationEnd(Animation animation) {
                if (cumulativeFlops == 2) {
                    if (firstOpenEmojiTextView.getText().toString()
                            .equals(secondOpenEmojiTextView.getText().toString())) {
                        cumulativeFlops = 0;
                        isSuccessfulAnimation = true;
                        successfulAnimation(firstOpenFrameLayout);
                        successfulAnimation(secondOpenFrameLayout);
                    } else {
                        cumulativeFlops = 0;
                        isSpecialMode = true;
                        firstOpenFrameLayout.setClickable(true);
                        secondOpenFrameLayout.setClickable(true);
                        firstOpenFrameLayout.performClick();
                        secondOpenFrameLayout.performClick();
                    }
                }
                if (isSpecialMode) {
                    isSpecialModeNumber = isSpecialModeNumber + 1;
                }
                if (isSpecialModeNumber == 2) {
                    isSpecialMode = false;
                    isSpecialModeNumber = 0;
                }
                if (isSuccessfulAnimation) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            allFrameLayoutRestoreClick();
                        }
                    }, 1000);
                    isSuccessfulAnimation = false;
                } else {
                    allFrameLayoutRestoreClick();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
    }

    /**
     * 如果已經翻面成功的牌, 就不恢復點擊, 否則就恢復點擊
     */
    private void allFrameLayoutRestoreClick() {
        if (emojiTextView1.getVisibility() == View.INVISIBLE) {
            frameLayout1.setClickable(true);
        }
        if (emojiTextView2.getVisibility() == View.INVISIBLE) {
            frameLayout2.setClickable(true);
        }
        if (emojiTextView3.getVisibility() == View.INVISIBLE) {
            frameLayout3.setClickable(true);
        }
        if (emojiTextView4.getVisibility() == View.INVISIBLE) {
            frameLayout4.setClickable(true);
        }
        if (emojiTextView5.getVisibility() == View.INVISIBLE) {
            frameLayout5.setClickable(true);
        }
        if (emojiTextView6.getVisibility() == View.INVISIBLE) {
            frameLayout6.setClickable(true);
        }
        if (emojiTextView7.getVisibility() == View.INVISIBLE) {
            frameLayout7.setClickable(true);
        }
        if (emojiTextView8.getVisibility() == View.INVISIBLE) {
            frameLayout8.setClickable(true);
        }
    }

    /**
     * 讓全部的牌不能點擊
     */
    private void allFrameLayoutNoClick() {
        frameLayout1.setClickable(false);
        frameLayout2.setClickable(false);
        frameLayout3.setClickable(false);
        frameLayout4.setClickable(false);
        frameLayout5.setClickable(false);
        frameLayout6.setClickable(false);
        frameLayout7.setClickable(false);
        frameLayout8.setClickable(false);
    }

    /**
     * 記錄每張牌目前的狀態, 是正面或者反面
     */
    private void changeIsPositive(int number) {
        switch (number) {
            case 1:
                if (ispositive1 == true) {
                    ispositive1 = false;
                    ispositive = false;
                } else {
                    ispositive1 = true;
                    ispositive = true;
                }
                changeCumulativeFlops(number);
                break;
            case 2:
                if (ispositive2 == true) {
                    ispositive2 = false;
                    ispositive = false;
                } else {
                    ispositive2 = true;
                    ispositive = true;
                }
                changeCumulativeFlops(number);
                break;
            case 3:
                if (ispositive3 == true) {
                    ispositive3 = false;
                    ispositive = false;
                } else {
                    ispositive3 = true;
                    ispositive = true;
                }
                changeCumulativeFlops(number);
                break;
            case 4:
                if (ispositive4 == true) {
                    ispositive4 = false;
                    ispositive = false;
                } else {
                    ispositive4 = true;
                    ispositive = true;
                }
                changeCumulativeFlops(number);
                break;
            case 5:
                if (ispositive5 == true) {
                    ispositive5 = false;
                    ispositive = false;
                } else {
                    ispositive5 = true;
                    ispositive = true;
                }
                changeCumulativeFlops(number);
                break;
            case 6:
                if (ispositive6 == true) {
                    ispositive6 = false;
                    ispositive = false;
                } else {
                    ispositive6 = true;
                    ispositive = true;
                }
                changeCumulativeFlops(number);
                break;
            case 7:
                if (ispositive7 == true) {
                    ispositive7 = false;
                    ispositive = false;
                } else {
                    ispositive7 = true;
                    ispositive = true;
                }
                changeCumulativeFlops(number);
                break;
            case 8:
                if (ispositive8 == true) {
                    ispositive8 = false;
                    ispositive = false;
                } else {
                    ispositive8 = true;
                    ispositive = true;
                }
                changeCumulativeFlops(number);
                break;
        }
    }

    /**
     * 計算目前翻了幾個牌, 以及翻了那些牌
     */
    private void changeCumulativeFlops(int number) {
        if (isSpecialMode == false) {
            switch (number) {
                case 1:
                    if (cumulativeFlops == 0) {
                        cumulativeFlops = 1;
                        firstOpenEmojiTextView = emojiTextView1;
                        firstOpenFrameLayout = frameLayout1;
                    } else if (cumulativeFlops == 1) {
                        cumulativeFlops = 2;
                        secondOpenEmojiTextView = emojiTextView1;
                        secondOpenFrameLayout = frameLayout1;
                    }
                    break;
                case 2:
                    if (cumulativeFlops == 0) {
                        cumulativeFlops = 1;
                        firstOpenEmojiTextView = emojiTextView2;
                        firstOpenFrameLayout = frameLayout2;
                    } else if (cumulativeFlops == 1) {
                        cumulativeFlops = 2;
                        secondOpenEmojiTextView = emojiTextView2;
                        secondOpenFrameLayout = frameLayout2;
                    }
                    break;
                case 3:
                    if (cumulativeFlops == 0) {
                        cumulativeFlops = 1;
                        firstOpenEmojiTextView = emojiTextView3;
                        firstOpenFrameLayout = frameLayout3;
                    } else if (cumulativeFlops == 1) {
                        cumulativeFlops = 2;
                        secondOpenEmojiTextView = emojiTextView3;
                        secondOpenFrameLayout = frameLayout3;
                    }
                    break;
                case 4:
                    if (cumulativeFlops == 0) {
                        cumulativeFlops = 1;
                        firstOpenEmojiTextView = emojiTextView4;
                        firstOpenFrameLayout = frameLayout4;
                    } else if (cumulativeFlops == 1) {
                        cumulativeFlops = 2;
                        secondOpenEmojiTextView = emojiTextView4;
                        secondOpenFrameLayout = frameLayout4;
                    }
                    break;
                case 5:
                    if (cumulativeFlops == 0) {
                        cumulativeFlops = 1;
                        firstOpenEmojiTextView = emojiTextView5;
                        firstOpenFrameLayout = frameLayout5;
                    } else if (cumulativeFlops == 1) {
                        cumulativeFlops = 2;
                        secondOpenEmojiTextView = emojiTextView5;
                        secondOpenFrameLayout = frameLayout5;
                    }
                    break;
                case 6:
                    if (cumulativeFlops == 0) {
                        cumulativeFlops = 1;
                        firstOpenEmojiTextView = emojiTextView6;
                        firstOpenFrameLayout = frameLayout6;
                    } else if (cumulativeFlops == 1) {
                        cumulativeFlops = 2;
                        secondOpenEmojiTextView = emojiTextView6;
                        secondOpenFrameLayout = frameLayout6;
                    }
                    break;
                case 7:
                    if (cumulativeFlops == 0) {
                        cumulativeFlops = 1;
                        firstOpenEmojiTextView = emojiTextView7;
                        firstOpenFrameLayout = frameLayout7;
                    } else if (cumulativeFlops == 1) {
                        cumulativeFlops = 2;
                        secondOpenEmojiTextView = emojiTextView7;
                        secondOpenFrameLayout = frameLayout7;
                    }
                    break;
                case 8:
                    if (cumulativeFlops == 0) {
                        cumulativeFlops = 1;
                        firstOpenEmojiTextView = emojiTextView8;
                        firstOpenFrameLayout = frameLayout8;
                    } else if (cumulativeFlops == 1) {
                        cumulativeFlops = 2;
                        secondOpenEmojiTextView = emojiTextView8;
                        secondOpenFrameLayout = frameLayout8;
                    }
                    break;
            }
        }
    }

    /**
     * 翻面成功的動畫
     */
    private void successfulAnimation(final View view) {
        view.animate()
                .scaleX(1.22f)
                .scaleY(1.22f)
                .setDuration(600)
                .setInterpolator(new OvershootInterpolator())
                .start();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(400)
                        .setInterpolator(new OvershootInterpolator())
                        .start();
            }
        }, 600);
    }

    private class TimeCountingAsyncTask extends AsyncTask<String, Integer, String> {

        /**
         * 这个方法会在后台任务开始执行之间调用, 在主线程执行, 用于进行一些界面上的初始化操作, 比如显示一个进度条对话框等
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 更新UI
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        /**
         * 执行耗时操作
         */
        @Override
        protected String doInBackground(String... params) {
            while (true) {
                if (isCancelled()) {
                    break;
                }
                if (onPlaying) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            formatTime(currentMilliSecond);
                            currentMilliSecond = currentMilliSecond + 1000;
                        }
                    });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "finished";
        }

        /**
         * doInBackground结束后执行本方法, result是doInBackground方法返回的数据
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    /**
     * 將毫秒轉成分秒
     */
    public void formatTime(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;
        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
        String strDay = day < 10 ? "0" + day : "" + day;                                            // 天
        String strHour = hour < 10 ? "0" + hour : "" + hour;                                        // 小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;                                // 分钟
        String strSecond = second < 10 ? "0" + second : "" + second;                                // 秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;            // 毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        minuteTextView.setText(strMinute);
        secondTextView.setText(strSecond);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPlaying = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        onPlaying = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onPlaying = false;
        timeCountingAsyncTask.cancel(true);
    }
}
