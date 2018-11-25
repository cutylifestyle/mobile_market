package com.sixin.police.market.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.sixin.police.market.R;
import com.sixin.police.market.constants.Constants;
import com.sixin.police.market.utils.AToast;
import com.sixin.police.market.utils.Analyzing;
import com.sixin.police.market.utils.AppUtils;

import java.io.Serializable;

import butterknife.ButterKnife;

import static android.text.TextUtils.isEmpty;

/**
 * Created by malia on 2017/4/18.
 */

public class BaseFragment extends Fragment {

    protected boolean mIsCreateView = false;

    public static BaseFragment fragment;

    protected BaseActivity mActivity;

    /**
     * 是否被建立，此方法放到OnCreateView中第一次被建立视图时被设置成true
     */
    public boolean isCreated = false;

    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean isCreated) {
        this.isCreated = isCreated;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }

    //此方法在控件初始化前调用，所以不能在此方法中直接操作控件会出现空指针
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mIsCreateView) {
            lazyLoad();
        }
    }

    public void setUserVisible(boolean isVisibleToUser) {}

    /**
     * 加载数据操作,在视图创建之前初始化
     */
    public void lazyLoad(){};

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //第一个fragment会调用
        if (getUserVisibleHint()){
            lazyLoad();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        setUserVisible(hidden);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View fragmentView;

    /**
     *
     * @param fragmentView
     *            fragment的视口所对应的View对象
     */
    public void setFragmentView(View fragmentView) {
        this.fragmentView = fragmentView;
    }

    /**
     *
     * @return fragmentView fragment的视口所对应的View对象
     */
    public View getFragmentView() {
        return fragmentView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*return super.onCreateView(inflater, container, savedInstanceState);*/
        if (null == container){
            return null;
        }
        initView(inflater, container);
        View view = getFragmentView();
        if (view == null) {
            throw new NullPointerException(
                    "fragment view not init,you can use the method setContentView() in the method overwrited from initView()");
        }
        if (isCreated) {
            onFreshAfterCreate();
        } else {
            setCreated(true);
        }
        return view;
    }

    /**
     * 此方法为
     * onCreateView的回调方法,初始化Fragment时被调用或者从其他fragment中切换回来或者恢复视图被调用,子类都要继承此方法
     *
     * @param inflater
     * @param container
     */
    public void initView(LayoutInflater inflater, ViewGroup container) {};

    /**
     * 建立视图之后刷新方法，由Fragement自行调用
     */
    public void onFreshAfterCreate() {}

    public BaseActivity getParentActivity() {
        if (Analyzing.isEmpty(mActivity)) {
            if (getActivity() instanceof BaseActivity) {
                return (BaseActivity) getActivity();
            } else {
                throw new ClassCastException("activity must extends BaseActivity");
            }
        } else {
            if (mActivity instanceof BaseActivity) {
                return mActivity;
            } else {
                throw new ClassCastException("activity must extends BaseActivity");
            }
        }
    }

    /**
     * 添加fragment
     *
     * @param fragment
     */
    protected void addFragment(BaseFragment fragment) {
        if (!Analyzing.isEmpty(fragment)) {
            getParentActivity().addFragment(fragment);
        }
    }

    /**
     * 移除fragment
     */
    protected void removeFragment() {
        getParentActivity().removeFragment();
    }

    /**
     *
     * @return 如果没有创建实例则创建实例，如果实例存在返回旧的实例，单例模式
     */
    public static BaseFragment getInstanceWithClassName(String className) {
        if (isEmpty(className)) {
            throw new NullPointerException("fragment class name not found!");
        }
        if (fragment == null) {
            try {
                fragment = (BaseFragment) Class.forName(className).newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return fragment;
        }
        return fragment;
    };

    /**
     *
     * @return 创建一个fragment
     */
    public static BaseFragment newInstance() {
        return new BaseFragment();
    };


    /**
     * 创建一个fragment不传递参数
     *
     * @param fragmentclass
     * @return
     */
    public static BaseFragment newInstance(Class<? extends Fragment> fragmentclass) {
        return newInstance(fragmentclass, null, null);
    };

    /**
     * 创建Fragment传递参数
     *
     * @param fragmentclass
     * @param title
     * @return
     */
    public static BaseFragment newInstance(Class<? extends Fragment> fragmentclass, String title) {
        return newInstance(fragmentclass, title, null);
    };

    /**
     * 创建Fragment传递多个参数
     *
     * @param fragmentclass
     * @param title
     * @param other
     * @return
     */
    public static BaseFragment newInstance(Class<? extends Fragment> fragmentclass, String title, String other) {
        BaseFragment fragment = null;
        try {
            fragment = (BaseFragment) fragmentclass.newInstance();
            Bundle args = new Bundle();
            boolean isNodata = true;
            if (!Analyzing.isEmpty(title)) {
                args.putString(Constants.ArgumentsKey, title);
                isNodata = false;
            }
            if (!Analyzing.isEmpty(other)) {
                args.putString(Constants.ArgumentsOtherKey, other);
                isNodata = false;
            }
            if (!isNodata) {
                fragment.setArguments(args);
            }
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    };

    /**
     * 传递一个序列化对象
     *
     * @param fragmentclass
     * @param object
     * @return
     */
    public static BaseFragment newInstance(Class<? extends Fragment> fragmentclass, Object object) {
        BaseFragment fragment = null;
        try {
            fragment = (BaseFragment) fragmentclass.newInstance();
            Bundle args = new Bundle();
            args.putSerializable(Constants.ArgumentsObjectKey, (Serializable) object);
            fragment.setArguments(args);
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    };

    protected void Toasting(String msg) {
        if (getActivity() != null) {
            AToast.show(msg);
        }
    }

    /**
     *
     * @param layoutID
     *            所对应的布局文件
     * @param inflater
     *            加载布局的工具
     * @param container
     *            root容器，容纳fragmentView
     * @return
     */
    public View setContentView(int layoutID, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(layoutID, container, false);
        setFragmentView(view);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 跳转到其他activity中
     *
     * @param cls
     *            Activity.class
     * @param closeCurrentPage
     *            是否关闭当前页面
     */
    public void moveTo(Class<?> cls, boolean closeCurrentPage) {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), cls);
        this.getActivity().startActivity(intent);
        if (closeCurrentPage) {
            this.getActivity().finish();
        }
    }

    /**
     * 跳转到其他activity中
     *
     * @param cls
     *            Activity.class
     * @param closeCurrentPage
     *            是否关闭当前页面
     * @param key
     *            bundle的key
     * @param bundle
     *            数据
     */
    public void moveTo(Class<?> cls, boolean closeCurrentPage, String key, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra(key, bundle);
        intent.setClass(this.getActivity(), cls);
        this.getActivity().startActivity(intent);
        if (closeCurrentPage) {
            this.getActivity().finish();
        }
    }

    /**
     * 跳转到其他activity中ForResult
     *
     * @param cls
     *            Activity.class
     */
    public void moveToForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), cls);
        this.startActivityForResult(intent, requestCode);
    }
    /**
     * 跳转到其他activity ForResult中
     *
     * @param cls
     *            Activity.class
     *            requestCode
     *            String key
     *            bundle
     */
    @SuppressLint("NewApi")
    public void moveToForResult(Class<?> cls, int requestCode, String key, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtra(key, bundle);
        intent.setClass(this.getActivity(), cls);
        getActivity().startActivityForResult(intent, requestCode, bundle);
    }

    public int getColor(int colorId) {
        int defaultColor = Color.parseColor("#00000000");
        if (isAdded()) {
            Resources res = getResources();
            if (res != null) {
                return res.getColor(colorId);
            }
        }
        return defaultColor;
    }

    public String getStr(int strId) {
        String defaultStr = "";
        if (isAdded()) {
            Resources res = getResources();
            if (res != null) {
                return res.getString(strId);
            }
        }
        return defaultStr;
    }

    /**
     * 父Activity的Resume方法执行后，会取出当前展示的fragment触发onParentResume方法
     */
    public void onParentResume() {};

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //TODO 在基类中加了代码
    protected void toastRequestErrorInfo(Response<String> response) {
        if (response != null) {
            int code = response.code();
            if (code >= 500) {
                Toast.makeText(AppUtils.getContext(), getResources().getString(R.string.server_error_please_retry), Toast.LENGTH_SHORT).show();
            } else if (code >= 400) {
                Toast.makeText(AppUtils.getContext(),getResources().getString(R.string.client_error_please_retry),Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(AppUtils.getContext(), getResources().getString(R.string.unknown_net_error_please_retry), Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 添加fragment中软件盘的管理
     */
}
