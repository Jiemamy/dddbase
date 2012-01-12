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
import java.util.List;
import java.util.Set;

/**
 * {@link OrderedEntity}を管理する REPOSITORY インターフェイス。
 * 
 * @param <E> 管理するエンティティの型
 * @param <ID> IDの型
 * @version $Id$
 * @author daisuke
 * @since 1.2.0
 */
public interface OrderedRepository<E extends OrderedEntity<ID>, ID extends Serializable> extends Repository<E, ID> {
	
	/**
	 * 管理している実体を {@link List} として返す。
	 * 
	 * <p>{@link List}の順序は、{@link #store(Entity)}した順序となる。</p>
	 * 
	 * <p>返される{@link List}やその要素{@link Entity}は他に
	 * 影響を及ぼさない独立したインスタンスである。つまりこの{@link List}や
	 * その要素{@link Entity}へのミューテーションは、リポジトリに影響を及ぼさない。</p>
	 * 
	 * @return 管理 {@link Entity} の {@link Set}
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 * @since 1.2.0
	 */
	List<E> getEntitiesAsList() throws RepositoryException;
	
	/**
	 * {@inheritDoc}
	 * 
	 * <p>このインターフェイスの実装では、{@code entity}のリストインデックス
	 * （{@link OrderedEntity#getIndex()}）によって適切な順序をリポジトリが保持する。</p>
	 * 
	 * <ul>
	 *   <li>リポジトリが既に{@code entity}とIDが同じエンティティを管理している場合は、
	 * リストインデックスの値にかかわらず、既存の同IDエンティティの位置に上書きを行う。</p>
	 *   <li>リストインデックスが負数だった場合、または、保持エンティティ数以上の数だった場合は、
	 * 既存の保持エンティティリストの最後尾に{@code entity}を追加する。</li>
	 *   <li>リストインデックスが上記以外だった場合、既存の保持エンティティリストの中で
	 * {@code entity}のリストインデックスが示す位置に{@code entity}を挿入する。</li>
	 * </ul>
	 * 
	 * <p>ストアに成功した場合、引数{@code entity}のインデックスを
	 * 挿入されたリストインデックスの値に再設定する。</p>
	 * 
	 * @since 1.2.0
	 */
	E store(E entity) throws RepositoryException;
	
	/**
	 * 保持エンティティのリストの順序を入れ替える。
	 * 
	 * @param index1 the index of one {@link OrderedEntity} to be swapped.
	 * @param index2 the index of the other {@link OrderedEntity} to be swapped.
	 * @throws IndexOutOfBoundsException if either {@code index1} or {@code index2}
	 *         is out of range (index1 &lt; 0 || index1 &gt;= list.size()
	 *         || index2 &lt; 0 || index2 &gt;= list.size()).
	 * @since 1.2.0
	 */
	void swap(int index1, int index2);
}
