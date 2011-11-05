/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
 * Created on 2009/01/20
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
 * {@link Entity}に対する参照（実体参照）インターフェイス。
 * 
 * <p>このインターフェイスの実装は、イミュータブルでなければならない(must)。</p>
 * 
 * @param <E> 実体のモデル型
 * @param <ID> IDの型
 * @version $Id$
 * @author daisuke
 * @since 1.0.0
 * @see Entity
 */
public interface EntityRef<E extends Entity<ID>, ID extends Serializable> extends ValueObject {
	
	/**
	 * 参照先要素の同一性を調べる。
	 * 
	 * @param obj 比較対象
	 * @return 同じIDの要素を参照している場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0.0
	 */
	boolean equals(Object obj);
	
	/**
	 * 実体を特定する記述子としてのモデルIDを取得する。
	 * 
	 * @return 実体を特定する記述子としてのモデルID
	 * @since 1.0.0
	 */
	ID getReferentId();
	
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
	boolean isReferenceOf(Entity<ID> target);
}
