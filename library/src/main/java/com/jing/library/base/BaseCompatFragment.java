package com.jing.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liujing on 2017/7/18.
 */

public abstract class BaseCompatFragment extends Fragment {
    protected final String TAG = getClass().getSimpleName();
    protected BaseCompatActivity activity;
    protected View contentView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "--------onAttach--------");
        activity = (BaseCompatActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "--------onCreate--------");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "--------onCreateView--------");
        contentView = inflater.inflate(getLayoutResId(), container, false);
        return contentView;
    }

    protected abstract int getLayoutResId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "--------onViewCreated--------");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "--------onActivityCreated--------");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "--------onStart--------");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "--------onResume--------");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "--------onPause--------");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "--------onStop--------");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "--------onDestroyView--------");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "--------onDestroy--------");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "--------onDetach--------");
    }

    public void openActivity(Class<?> cls) {
        openActivity(cls, null, 0);
    }

    public void openActivity(Class<?> cls, int flag) {
        openActivity(cls, null, flag);
    }

    public void openActivity(Class<?> cls, Bundle bundle) {
        openActivity(cls, bundle, 0);
    }

    public void openActivity(Class<?> cls, Bundle bundle, int flag) {
        activity.openActivity(cls, bundle, flag);
    }
}
