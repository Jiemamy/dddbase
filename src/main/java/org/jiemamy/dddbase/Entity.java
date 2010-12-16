/*
 * Copyright 2007-2010 Jiemamy Project and the Others.
 * Created on 2010/12/10
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

import java.util.Collection;
import java.util.UUID;

/**
 * DDD における ENTITY を表すインターフェイス。
 * 
 * <p>ENTITYは、JavaObjectのライフサイクル（new〜GC）を越えうる独自のライフサイクルを持つ。
 * また、ENTITYはIDを持ち、そのIDはENTITYのライフサイクルを通じて不変である。
 * {@link EntityRef}により参照可能なオブジェクトでもある。</p>
 * 
 * @see EntityRef
 * @version $Id$
 * @author daisuke
 */
public interface Entity extends Cloneable {
	
	/**
	 * エンティティのクローンを取得する。
	 * 
	 * <p>プロパティとして保持する可変オブジェクト(主に{@link Collection})もクローンし、
	 * {@link Collection}の要素も可変オブジェクトである場合は、その要素もクローンする。</p>
	 * 
	 * <p>この型のサブタイプは、必ずこのメソッドを再定義し、戻り値型を自分自身の型に共変して宣言
	 * すべきである(should)。例えば、{@code FooEntity extends Entity} という型を宣言したら、
	 * そのメソッドとして {@code FooEntity clone()} というシグネチャのメソッドを再定義
	 * すべきである(should)。</p>
	 * 
	 * @return clone クローンオブジェクト
	 * @since 0.3
	 * @see Object#clone()
	 */
	Entity clone();
	
	/**
	 * ENTITY IDの等価性(equals)を以て、ENTITYの同一性を比較する。
	 * 
	 * <p>実装型が異なったとしても、{@code obj}が{@link Entity}型であり、
	 * そのIDが一致した場合は同一とする。</p>
	 * 
	 * @param obj 比較対象オブジェクト
	 * @return 同じIDを持つ場合は{@code true}、そうでない場合は{@code false}
	 */
	boolean equals(Object obj);
	
	/**
	* ENTITY IDを取得する。
	* 
	* <p>IDは、ENTITYとしてのライフサイクル開始時に指定または自動生成され、ライフサイクルを通して
	* 一貫していなければならない。ライフサイクルの終了と共にIDは削除される。</p>
	* 
	* @return ENTITY ID
	*/
	UUID getId();
	
	/**
	 * 子エンティティの集合を取得する。
	 * 
	 * <p>このエンティティが保持するエンティティであり、 {@link Repository} では直接管理されない
	 * エンティティ。（間接的には {@link Repository} が自動的に管理する）</p>
	 * 
	 * @return 子エンティティの集合
	 */
	Collection<? extends Entity> getSubEntities();
	
	/**
	 * 参照オブジェクトを返す。
	 * 
	 * @return 参照オブジェクト
	 * @since 0.3
	 */
	EntityRef<? extends Entity> toReference();
}
