/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
 * Created on 2009/01/19
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

import org.apache.commons.lang.Validate;

/**
 * 参照オブジェクトのデフォルト実装。
 * 
 * @param <E> 参照対象オブジェクトの型
 * @param <ID> IDの型
 * @version $Id$
 * @author daisuke
 * @since 1.0.0
 */
public class DefaultEntityRef<E extends Entity<ID>, ID extends Serializable> implements EntityRef<E, ID> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param <T> 参照対象オブジェクトの型
	 * @param referentId 参照先のモデルID
	 * @return 参照オブジェクト
	 * @since 1.0.0
	 */
	public static <T extends Entity<ID>, ID extends Serializable>DefaultEntityRef<T, ID> of(ID referentId) {
		return new DefaultEntityRef<T, ID>(referentId);
	}
	
	
	final ID referentId;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param referent 定義オブジェクト
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数に{@code referent.getId()}が{@code null}であるエンティティを与えた場合
	 * @since 1.0.0
	 */
	public DefaultEntityRef(E referent) {
		Validate.notNull(referent);
		Validate.notNull(referent.getId());
		referentId = referent.getId();
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param referentId 参照先のモデルID
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	public DefaultEntityRef(ID referentId) {
		Validate.notNull(referentId);
		this.referentId = referentId;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof EntityRef == false) {
			return false;
		}
		return referentId.equals(((EntityRef<E, ID>) obj).getReferentId());
	}
	
	public ID getReferentId() {
		return referentId;
	}
	
	@Override
	public int hashCode() {
		return referentId.hashCode();
	}
	
	public boolean isReferenceOf(Entity<ID> target) {
		return referentId.equals(target.getId());
	}
	
	@Override
	public String toString() {
		return "Ref(" + referentId.toString().substring(0, 8) + ")";
	}
}
