package com.example.darkhorse.dhnews.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by DarkHorse on 2017/11/16.
 */

public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) getActivity();
    }

    protected abstract void findViewById(View rootView);

    protected abstract void setListener(View rootView);

    protected abstract void init();
}
