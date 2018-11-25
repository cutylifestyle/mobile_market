package com.sixin.police.market.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lehand.adapter.BaseQuickAdapter;
import com.lehand.smartrefresh.SmartRefreshLayout;
import com.lehand.smartrefresh.api.RefreshLayout;
import com.lehand.smartrefresh.listener.OnLoadMoreListener;
import com.lehand.smartrefresh.listener.OnRefreshListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sixin.police.market.R;
import com.sixin.police.market.adapter.AppCommentMultiAdapter;
import com.sixin.police.market.base.BaseFragment;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.AppCommentBase;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.AppCommentEntity;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.AppCommentHeaderEntity;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.AppCommentTitleEntity;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.StarLevelEntity;
import com.sixin.police.market.utils.AppUtils;
import com.sixin.police.market.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AppAllCommentFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {

    private static final String TAG = "AppAllCommentFragment";

    private OnRenderRadioBtnListener mListener;

    @Nullable
    @BindView(R.id.srl_app_all_comment)
    SmartRefreshLayout mSrlAppAllComment;

    @Nullable
    @BindView(R.id.rv_app_all_comment)
    RecyclerView mRvAppAllComment;

    private AppCommentMultiAdapter mAppCommentMultiAdapter;
    private List<AppCommentBase> mAppComments = new ArrayList<>();
    /*
    * 用于存放上拉加载更多数据的临时集合
    * */
    private List<AppCommentBase> mTempAppComments = new ArrayList<>();

    private static final String TAG_REQUEST_HEADER_PANEL = "tag_request_header_panel";
    private static final String TAG_REQUEST_COMMENT_DATA = "tag_request_comment_data";
    /**
     * 网络请求总次数的累加器
     * 用于实现多个网络请求全部完成在渲染页面
     * */
    private int responseTotalCount;
    /**
     * 多个网络请求空数据的累加器
     * 用于实现多个网络请求全部完成在渲染页面
     * */
    private int emptyDataTotalCount;
    /**
     * 是否是上拉加载更多,
     * true 是；只需要请求用户评论接口就行
     * false 表示下拉刷新；需要同时请求用户评论接口以及应用评价接口
     * */
    private boolean isLoadMore;

    private String mAppCommentCount;

    //TODO 修改下拉刷新控件的样式
    public AppAllCommentFragment() {
        // Required empty public constructor
    }

    public static AppAllCommentFragment newInstance() {
        AppAllCommentFragment fragment = new AppAllCommentFragment();
        return fragment;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        super.initView(inflater, container);
        setContentView(R.layout.fragment_app_all_comment, inflater, container);
        setRefreshLayoutListener();
        configRecyclerView();
    }

    private void configRecyclerView() {
        mRvAppAllComment.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mAppCommentMultiAdapter = new AppCommentMultiAdapter(mAppComments);
        mAppCommentMultiAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRvAppAllComment.setAdapter(mAppCommentMultiAdapter);
    }

    private void setRefreshLayoutListener() {
        //TODO  保持有空面板的存在
        mSrlAppAllComment.setOnRefreshListener(this);
        mSrlAppAllComment.setOnLoadMoreListener(this);
    }

    /**
     *网络请求获取最新数据
     * */
    private void loadNewestData() {
        //TODO 暂时的处理方式是先在这儿就清空数据

        //TODO 考虑要不要吐司
        mAppComments.clear();

        if (mSrlAppAllComment != null) {
            mSrlAppAllComment.setEnableLoadMore(true);
        }

        isLoadMore = false;
        responseTotalCount = 0;
        emptyDataTotalCount = 0;

        //在请求数据前，检查网络状况是否良好
        if(!NetUtils.isNetworkConnected(getContext())){
            //注意一定要加入判空操作，否则可能会导致空指针异常
            if (mSrlAppAllComment != null) {
                mSrlAppAllComment.finishRefresh(false);
            }
            toastGravityCenter(getStr(R.string.network_not_connected));
            return;
        }

        requestHeaderData();
        requestCommentRecord();
    }

    /**
     * 通知RadioButton刷新数据
     * @param commentCount 评论总数
     * */
    public void notificationRenderBtn(String commentCount) {
        if (mListener != null) {
            mListener.onRender(commentCount);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRenderRadioBtnListener) {
            mListener = (OnRenderRadioBtnListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRenderRadioBtnListener");
        }
    }

    @Override
    public void lazyLoad() {
        mSrlAppAllComment.autoRefresh();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        loadNewestData();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        loadMoreCommentData();
    }

    /**
     * 上拉加载更多评论数据
     * */
    private void loadMoreCommentData() {

        mTempAppComments.clear();

        isLoadMore = true;
        emptyDataTotalCount = 0;
        responseTotalCount = 0;

        //在请求数据前，检查网络状况是否良好
        if(!NetUtils.isNetworkConnected(getContext())){
            //注意一定要加入判空操作，否则可能会导致空指针异常
            if (mSrlAppAllComment != null) {
                mSrlAppAllComment.finishLoadMore(false);
            }
            toastGravityCenter(getStr(R.string.network_not_connected));
            return;
        }
        requestCommentRecord();
    }

    /**
     * 用于通知DetailActivity中的评论按钮刷新评论数据
     * */
    public interface OnRenderRadioBtnListener {
        /**
         * @param commentCount 评论总数
         * */
        void onRender(String commentCount);
    }

    /**
     * 头部数据的网络请求
     * 头部数据与用户评论数据都获取到以后采取渲染页面
     * 有一个请求出现问题，就不渲染页面
     * */
    private void requestHeaderData() {
        //TODO 测试在弱网环境下会不会导致程序崩溃
        OkGo.<String>get("http://apis.juhe.cn/cook/query.php")
                .tag(TAG_REQUEST_HEADER_PANEL)
                .params("key","469fd874804b7e62c4e20da9c1b624b0")
                .params("menu","红烧肉")
                .execute(new StringCallback() {

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.d(TAG, "header--------:" + response.getException().getMessage());
                        toastRequestErrorInfo(response);
                        sendResponseMessage(true,0);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d(TAG, "header---------:" + response.body());
                        //TODO  sendMesseage方法考虑尝试先调用
                        AppCommentHeaderEntity appCommentHeaderEntity = new AppCommentHeaderEntity();
                        appCommentHeaderEntity.setTitle("应用评分");
                        appCommentHeaderEntity.setAppScore(4.5f);
                        appCommentHeaderEntity.setTotalCommentCount("1.5万");

                        List<StarLevelEntity> starLevels = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            StarLevelEntity starLevelEntity = new StarLevelEntity();
                            starLevelEntity.setStarLevel(5-i);
                            starLevelEntity.setPercent(0.3f);
                            starLevels.add(starLevelEntity);
                        }
                        appCommentHeaderEntity.setStarLevels(starLevels);
                        //TODO 测试添加到这个位置会不会导致数据发生错乱
                        mAppComments.add(0,appCommentHeaderEntity);

                        //TODO 是否添加标题的前提条件是检查有没有评论,可以更具评论人数确定是否需要添加
                        //TODO 询问后台评论总数返回的是什么类型的数据，目前来看最好返回整数类型
                        AppCommentTitleEntity appCommentTitleEntity = new AppCommentTitleEntity();
                        appCommentTitleEntity.setTitle(getString(R.string.all_comment));
                        mAppComments.add(1,appCommentTitleEntity);

                        //对评论总数设值
                        String totalCount = appCommentHeaderEntity.getTotalCommentCount();
                        if (TextUtils.isEmpty(totalCount)) {
                            totalCount = "0";
                        }
                        mAppCommentCount = totalCount;

                        sendResponseMessage(false,0);
                    }
                });
    }

    /**
     * 用户评论的网络请求
     * 头部数据与用户评论数据都获取到以后采取渲染页面
     * 有一个请求出现问题，就不渲染页面
     * */
    private void requestCommentRecord() {
        //TODO 这儿的网络请求整体流程还需要走一遍，防止出现bug
        OkGo.<String>get("http://v.juhe.cn/toutiao/index")
                .tag(TAG_REQUEST_COMMENT_DATA)
                .params("key","8ba9b86990513b9629bbf60510b39224")
                .execute(new StringCallback() {

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.d(TAG, "comment------:" + response.getException().getMessage());
                        toastRequestErrorInfo(response);
                        sendResponseMessage(true,0);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d(TAG, "comment-------:" + response.body());
                        if (isLoadMore) {
                            for (int i = 0; i < 10; i++) {
                                AppCommentEntity appCommentEntity = new AppCommentEntity();
                                appCommentEntity.setUserName("藏三");
                                appCommentEntity.setPoliceId("100000");
                                appCommentEntity.setCommentDate("2018/10/11");
                                appCommentEntity.setCommentScore(3.0f);
                                appCommentEntity.setLikeCount("50");
                                appCommentEntity.setCommentVersion("v1.2");
                                appCommentEntity.setCommentContent("xxxxxxxxx\nxxxxxxxx\nxxxxxxxxx\nxxxxxxxx\nxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                mTempAppComments.add(appCommentEntity);
                            }
                        }else{
                            for (int i = 0; i < 10; i++) {
                                AppCommentEntity appCommentEntity = new AppCommentEntity();
                                appCommentEntity.setUserName("藏三");
                                appCommentEntity.setPoliceId("100000");
                                appCommentEntity.setCommentDate("2018/10/11");
                                appCommentEntity.setCommentScore(3.0f);
                                appCommentEntity.setLikeCount("50");
                                appCommentEntity.setCommentVersion("v1.2");
                                appCommentEntity.setCommentContent("xxxxxxxxx\nxxxxxxxx\nxxxxxxxxx\nxxxxxxxx\nxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                mAppComments.add(appCommentEntity);
                            }
                        }
                        //TODO 这儿发送的0还是1需要与后台沟通错误码
                        sendResponseMessage(false,0);
                    }
                });
    }

    /**
     * 接受多个网络请求的结果信息
     * @param isError 网络请求是否发生异常 true 是 false 否
     * @param empty 网络请求的结果是不是空数据.empty的取值范围0或者1
     *              0表示不是空数据，1表示当前网络请求是空数据
     * */
    private void sendResponseMessage(boolean isError, @IntRange(from = 0,to = 1) int empty) {

        if (isError) {
            OkGo.getInstance().cancelTag(TAG_REQUEST_HEADER_PANEL);
            OkGo.getInstance().cancelTag(TAG_REQUEST_COMMENT_DATA);
            if (mSrlAppAllComment != null) {
               //TODO 这儿的状态需要进行测试
                if(!isLoadMore){
                    mSrlAppAllComment.finishRefresh(false);
                    mSrlAppAllComment.setEnableLoadMore(false);
                }else{
                    mSrlAppAllComment.finishLoadMore(false);
                }
            }
            return;
        }
        emptyDataTotalCount += empty;
        responseTotalCount++;

        if(!isLoadMore){
            if (responseTotalCount == 2) {
                if (emptyDataTotalCount == 0) {
                    if (mSrlAppAllComment != null) {
                        mSrlAppAllComment.finishRefresh(true);
                        notificationRenderBtn(mAppCommentCount);
                        mAppCommentMultiAdapter.notifyDataSetChanged();
                        if (mAppComments.size() == 1) {
                            mSrlAppAllComment.setEnableLoadMore(false);
                        }
                    }
                }else{
                    if (mSrlAppAllComment != null) {
                        mSrlAppAllComment.finishRefresh(false);
                        //TODO 当连接上服务器以后，测试上拉加载的逻辑对不对
                        mSrlAppAllComment.setEnableLoadMore(false);
                        toastGravityCenter(getStr(R.string.no_data_please_retry));
                    }
                }
            }
        }else{
            if (responseTotalCount == 1) {
                if (emptyDataTotalCount == 0) {
                    if (mSrlAppAllComment != null) {
                        mSrlAppAllComment.finishLoadMore(true);
                        mAppCommentMultiAdapter.addData(mTempAppComments);
                        //TODO 处理没有更多数据的情况，已经加载完了
                    }
                }else{
                    if (mSrlAppAllComment != null) {
                        mSrlAppAllComment.finishLoadMore(false);
                        toastGravityCenter(getStr(R.string.no_data_please_retry));
                    }
                }
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消网络请求
        OkGo.getInstance().cancelTag(TAG_REQUEST_HEADER_PANEL);
        OkGo.getInstance().cancelTag(TAG_REQUEST_COMMENT_DATA);
        //关闭recyclerView的动画
        if (mAppCommentMultiAdapter != null) {
            mAppCommentMultiAdapter.closeLoadAnimation();
        }
    }
}
