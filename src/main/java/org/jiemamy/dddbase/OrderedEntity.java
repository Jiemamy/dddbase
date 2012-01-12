/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2011/01/12
 *
 * This file is part of Jiemamy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.jiemamy.dddbase;

import java.io.Serializable;

/**
 * リポジトリ上で順序付きで管理されるENTITY。
 * 
 * @param <ID> IDの型
 * @version $Id$
 * @author daisuke
 * @since 1.2.0
 */
public interface OrderedEntity<ID extends Serializable> extends Entity<ID> {
	
	OrderedEntity<ID> clone();
	
	/**
	 * リポジトリ内でこのエンティティが位置するリストインデックスを返す。
	 * 
	 * <p>リポジトリに管理されていないエンティティでは「ストアした際に位置すべきリストインデックス」を表す。
	 * この値の初期値（{@link #setIndex(int)}等によって明示的に値を設定していない場合）は{@code -1}とする。</p>
	 * 
	 * @return リストインデックス
	 * @see OrderedOnMemoryRepository#store(OrderedEntity)
	 * @since 1.2.0
	 */
	int getIndex();
	
	/**
	 * リポジトリ内でこのエンティティが位置するリストインデックスを設定する。
	 * 
	 * <p>リポジトリに管理されていないエンティティでは「ストアした際に位置すべきリストインデックス」を設定する。</p>
	 * 
	 * @param index リストインデックス
	 * @since 1.2.0
	 */
	void setIndex(int index);
	
	EntityRef<? extends OrderedEntity<ID>, ID> toReference();
}
