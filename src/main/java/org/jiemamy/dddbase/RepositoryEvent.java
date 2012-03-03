/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2009/02/09
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

import java.util.EventObject;

/**
 * リポジトリの {@link Entity} が追加・変更・削除されたことを表すイベント。
 * 
 * @param <E> 変更が起こったリポジトリが管理する{@link Entity}の型
 * @version $Id$
 * @author daisuke
 * @author shin1ogawa
 * @since 1.3.0
 */
@SuppressWarnings("serial")
public class RepositoryEvent<E extends Entity> extends EventObject {
	
	private final E before;
	
	private final E after;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param source イベント発生元リポジトリ 
	 * @param before 変更前の{@link Entity}（新規の場合は {@code null}）
	 * @param after 変更後の{@link Entity}（削除の場合は {@code null}）
	 */
	public RepositoryEvent(Repository<E> source, E before, E after) {
		super(source);
		this.before = before;
		this.after = after;
	}
	
	/**
	 * 変更後の{@link Entity}を取得する。
	 * 
	 * @return 変更後の{@link Entity}（削除の場合は {@code null}）
	 */
	public E getAfter() {
		return after;
	}
	
	/**
	 * 変更前の{@link Entity}を取得する。
	 * 
	 * @return 変更前の{@link Entity}（新規の場合は {@code null}）
	 */
	public E getBefore() {
		return before;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Repository<E> getSource() {
		return (Repository<E>) super.getSource();
	}
	
	@Override
	public String toString() {
		return getClass().getName() + "[source=" + getSource().getClass() + "]";
	}
}
