package com.github.ybq.android.loading;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.Wave;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Page2Fragment extends Fragment implements Colors {

    private Wave mWaveDrawable;
    private Circle mCircleDrawable;
    private ChasingDots mChasingDotsDrawable;
    private DoubleBounce doubleBounce;

    private Button but_image, but_video, but_audio, but_other;

    public static Page2Fragment newInstance() {
        return new Page2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page2, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        but_image = (Button) view.findViewById(R.id.image);
        doubleBounce = new DoubleBounce();
        doubleBounce.setBounds(0, 0, 100, 100);//noinspection deprecation
        doubleBounce.setColor(colors[7]);
        but_image.setCompoundDrawables(doubleBounce, null, null, null);

        but_video = (Button) view.findViewById(R.id.video);
        mWaveDrawable = new Wave();
        mWaveDrawable.setBounds(0, 0, 100, 100);//noinspection deprecation
        mWaveDrawable.setColor(getResources().getColor(R.color.colorAccent));
        but_video.setCompoundDrawables(mWaveDrawable, null, null, null);

        but_audio = (Button) view.findViewById(R.id.audio);
        mCircleDrawable = new Circle();
        mCircleDrawable.setBounds(0, 0, 100, 100);//noinspection deprecation
        mCircleDrawable.setColor(getResources().getColor(R.color.colorAccent));
        but_audio.setCompoundDrawables(mCircleDrawable, null, null, null);

        but_other = (Button) view.findViewById(R.id.other);
        mChasingDotsDrawable = new ChasingDots();
        mChasingDotsDrawable.setBounds(0, 0, 100, 100);//noinspection deprecation
        mChasingDotsDrawable.setColor(Color.BLUE);
        but_other.setCompoundDrawables(mChasingDotsDrawable, null, null, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mWaveDrawable.start();
        mCircleDrawable.start();
        mChasingDotsDrawable.start();
        doubleBounce.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mWaveDrawable.stop();
        mCircleDrawable.stop();
        mChasingDotsDrawable.stop();
        doubleBounce.stop();
    }
}
