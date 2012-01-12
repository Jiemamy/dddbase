/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2011/11/05
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
import java.util.UUID;

/**
 * {@link UUIDEntityRef}のデフォルト実装クラス。
 * 
 * @param <E> エンティティの型
 * @version $Id$
 * @author daisuke
 */
public class DefaultUUIDEntityRef<E extends UUIDEntity> extends DefaultEntityRef<E, UUID> implements UUIDEntityRef<E> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param <T> 参照対象オブジェクトの型
	 * @param referentId 参照先のモデルID
	 * @return 参照オブジェクト
	 * @since 1.0.0
	 */
	public static <T extends UUIDEntity, ID extends Serializable>DefaultUUIDEntityRef<T> of(UUID referentId) {
		return new DefaultUUIDEntityRef<T>(referentId);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param referent 定義オブジェクト
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数に{@code referent.getId()}が{@code null}であるエンティティを与えた場合
	 * @since 1.0.0
	 */
	public DefaultUUIDEntityRef(E referent) {
		super(referent);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param referentId 参照先のモデルID
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	public DefaultUUIDEntityRef(UUID referentId) {
		super(referentId);
	}
}
