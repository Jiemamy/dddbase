/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
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

import java.io.Serializable;
import java.util.Collection;

/**
 * DDD における ENTITY を表すインターフェイス。
 * 
 * <p>ENTITYは、JavaObjectのライフサイクル（new〜GC）を越えうる独自のライフサイクルを持つ。
 * また、ENTITYはIDを持ち、そのIDはENTITYのライフサイクルを通じて不変である。
 * {@link EntityRef}により参照可能なオブジェクトでもある。</p>
 * 
 * @param <ID> IDの型
 * @version $Id$
 * @author daisuke
 * @since 1.0.0
 * @see EntityRef
 */
public interface Entity<ID extends Serializable> extends Cloneable {
	
	/**
	 * エンティティのクローンを取得する。
	 * 
	 * <p>プロパティとして保持する可変オブジェクト(主に{@link Collection})も複製し、
	 * {@link Collection}の要素も可変オブジェクトである場合は、その要素も複製する。</p>
	 * 
	 * <p>この型のサブタイプは、必ずこのメソッドを再定義し、戻り値型を自分自身の型に共変して宣言
	 * すべきである(should)。例えば、{@code FooEntity extends Entity} という型を宣言したら、
	 * そのメソッドとして {@code FooEntity clone()} というシグネチャのメソッドを再定義
	 * すべきである(should)。</p>
	 * 
	 * @return clone クローンオブジェクト
	 * @since 1.0.0
	 * @see Object#clone()
	 */
	Entity<ID> clone();
	
	/**
	 * このENTITYと比較対象オブジェクトの同一性を検証する。
	 * 
	 * <p>このインターフェイスの実装では、ENTITY IDの等価性(equals)を以て、ENTITYの同一性を比較する。
	 * 引数に{@code null}を与えた場合や、 {@link Entity}型ではないオブジェクトを与えた場合は、
	 * {@code false}となる。</p>
	 * 
	 * <p>また、実装型が異なったとしても、{@code obj}が{@link Entity}型であり、
	 * そのIDが一致した場合は同一とする。</p>
	 * 
	 * @param obj 比較対象オブジェクト
	 * @return {@code obj}が{@link Entity}型であり、同じIDを持つ場合は{@code true}、そうでない場合は{@code false}
	 * @since 1.0.0
	 */
	boolean equals(Object obj);
	
	/**
	* ENTITY IDを取得する。
	* 
	* <p>IDは、ENTITYとしてのライフサイクル開始時に指定または自動生成され、ライフサイクルを通して
	* 一貫していなければならない。</p>
	* 
	* @return ENTITY ID
	* @since 1.0.0
	*/
	ID getId();
	
	/**
	 * このENTITYのハッシュ値を返す。
	 * 
	 * <p>このインターフェイスの実装では、{@link #equals(Object)}との整合性を保つため、
	 * ENTITY IDのハッシュ値を返すべき(should)である。</p>
	 * 
	 * @return ハッシュ値
	 * @since 1.0.0
	 */
	int hashCode();
	
	/**
	 * このENTITYの参照オブジェクト（{@link EntityRef}）を返す。
	 * 
	 * @return 参照オブジェクト
	 * @throws UnsupportedOperationException エンティティが参照をサポートしない場合
	 * @since 1.0.0
	 */
	EntityRef<? extends Entity<ID>, ID> toReference();
}
