/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sixin.police.market.network.okserver.download;

import com.sixin.police.market.network.okserver.task.PriorityBlockingQueue;
import com.sixin.police.market.network.okserver.task.XExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author malx
 * @date ：2018/11/16
 * 描    述：下载管理的线程池
 */
public class DownloadThreadPool {
    // 最大线程池的数量
    private static final int MAX_POOL_SIZE = 5;
    // 存活的时间
    private static final int KEEP_ALIVE_TIME = 1;
    // 时间单位
    private static final TimeUnit UNIT = TimeUnit.HOURS;
    // 核心线程池的数量，同时能执行的线程数量，默认3个
    private int corePoolSize = 3;
    // 线程池执行器
    private XExecutor executor;

    public XExecutor getExecutor() {
        if (executor == null) {
            synchronized (DownloadThreadPool.class) {
                if (executor == null) {
                    executor = new XExecutor(corePoolSize, MAX_POOL_SIZE, KEEP_ALIVE_TIME, UNIT,
                                // 无限容量的缓冲队列
                                new PriorityBlockingQueue<Runnable>(),
                                // 线程创建工厂
                                Executors.defaultThreadFactory(),
                                // 继续超出上限的策略，阻止
                                new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        return executor;
    }

    /** 必须在首次执行前设置，否者无效 ,范围1-5之间 */
    public void setCorePoolSize(int corePoolSize) {
        if (corePoolSize <= 0) {
            corePoolSize = 1;
        }
        if (corePoolSize > MAX_POOL_SIZE) {
            corePoolSize = MAX_POOL_SIZE;
        }
        this.corePoolSize = corePoolSize;
    }

    /** 执行任务 */
    public void execute(Runnable runnable) {
        if (runnable != null) {
            getExecutor().execute(runnable);
        }
    }

    /** 移除线程 */
    public void remove(Runnable runnable) {
        if (runnable != null) {
            getExecutor().remove(runnable);
        }
    }
}
