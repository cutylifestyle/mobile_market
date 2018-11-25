package com.sixin.police.market.bean.appdetailinfo.appintroduceinfo;


import com.sixin.police.market.bean.BaseBean;

/**
 * 应用介绍的实体类
 * */
public class AppIntroduceEntity{

    private PreImageEntity preImageEntity;

    private AppInfoEntity appInfoEntity;

    private AppSummaryEntity appSummaryEntity;

    private AppUpdateLogEntity appUpdateLogEntity;

    private AppLabelEntity appLabelEntity;

    private AppDeveloperInfoEntity appDeveloperInfoEntity;

    public PreImageEntity getPreImageEntity() {
        return preImageEntity;
    }

    public void setPreImageEntity(PreImageEntity preImageEntity) {
        this.preImageEntity = preImageEntity;
    }

    public AppInfoEntity getAppInfoEntity() {
        return appInfoEntity;
    }

    public void setAppInfoEntity(AppInfoEntity appInfoEntity) {
        this.appInfoEntity = appInfoEntity;
    }

    public AppSummaryEntity getAppSummaryEntity() {
        return appSummaryEntity;
    }

    public void setAppSummaryEntity(AppSummaryEntity appSummaryEntity) {
        this.appSummaryEntity = appSummaryEntity;
    }

    public AppUpdateLogEntity getAppUpdateLogEntity() {
        return appUpdateLogEntity;
    }

    public void setAppUpdateLogEntity(AppUpdateLogEntity appUpdateLogEntity) {
        this.appUpdateLogEntity = appUpdateLogEntity;
    }

    public AppLabelEntity getAppLabelEntity() {
        return appLabelEntity;
    }

    public void setAppLabelEntity(AppLabelEntity appLabelEntity) {
        this.appLabelEntity = appLabelEntity;
    }

    public AppDeveloperInfoEntity getAppDeveloperInfoEntity() {
        return appDeveloperInfoEntity;
    }

    public void setAppDeveloperInfoEntity(AppDeveloperInfoEntity appDeveloperInfoEntity) {
        this.appDeveloperInfoEntity = appDeveloperInfoEntity;
    }
}
