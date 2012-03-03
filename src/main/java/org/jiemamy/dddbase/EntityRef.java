/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
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

import java.util.UUID;

import org.apache.commons.lang.Validate;

/**
 * 参照オブジェクトのデフォルト実装。
 * 
 * @param <E> 参照対象オブジェクトの型
 * @version $Id$
 * @author daisuke
 * @since 1.0.0
 */
public class EntityRef<E extends Entity> {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param <T> 参照対象オブジェクトの型
	 * @param referentId 参照先のモデルID
	 * @return 参照オブジェクト
	 * @since 1.0.0
	 */
	public static <T extends Entity>EntityRef<T> of(UUID referentId) {
		return new EntityRef<T>(referentId);
	}
	
	
	final UUID referentId;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param referent 定義オブジェクト
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @throws IllegalArgumentException 引数に{@code referent.getId()}が{@code null}であるエンティティを与えた場合
	 * @since 1.0.0
	 */
	public EntityRef(E referent) {
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
	public EntityRef(UUID referentId) {
		Validate.notNull(referentId);
		this.referentId = referentId;
	}
	
	/**
	 * 参照先要素の同一性を調べる。
	 * 
	 * @param obj 比較対象
	 * @return 同じIDの要素を参照している場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0.0
	 */
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
		return referentId.equals(((EntityRef<E>) obj).getReferentId());
	}
	
	/**
	 * 実体を特定する記述子としてのモデルIDを取得する。
	 * 
	 * @return 実体を特定する記述子としてのモデルID
	 * @since 1.0.0
	 */
	public UUID getReferentId() {
		return referentId;
	}
	
	@Override
	public int hashCode() {
		return referentId.hashCode();
	}
	
	/**
	 * この参照オブジェクトが引数{@code target}の参照かどうか調べる。
	 * 
	 * <p>引数の型である{@link Entity}型は、T型であるとよりタイプセーフとなるが、
	 * {@link Entity}型の抽象度で、「この参照の実体である」ことがチェックできると
	 * 有効なケースがあるため、敢えて{@link Entity}型としている。
	 * ただし、偶然同じIDを持ってしまったが型が異なる{@link Entity}が存在してしまった場合は
	 * その後の処理で{@link ClassCastException}が発生する可能性があるが、
	 * {@link UUID} の衝突率は無視できるほど小さい。</p>
	 * 
	 * @param target 対象{@link Entity}
	 * @return この参照オブジェクトが引数{@code target}の参照の場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0.0
	 */
	public boolean isReferenceOf(Entity target) {
		return referentId.equals(target.getId());
	}
	
	@Override
	public String toString() {
		return "Ref(" + referentId.toString().substring(0, 8) + ")";
	}
}
