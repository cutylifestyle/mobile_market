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
package com.sixin.police.market.network.okserver.task;

/**
 * @author malx
 * @date ：2018/11/16
 * 描    述：具有优先级对象的公共类
 */
public class PriorityObject<E> {

    public final int priority;
    public final E obj;

    public PriorityObject(int priority, E obj) {
        this.priority = priority;
        this.obj = obj;
    }
}
