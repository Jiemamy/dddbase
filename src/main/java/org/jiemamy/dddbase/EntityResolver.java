/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2011/01/16
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

/**
 * 実体参照 {@link EntityRef} や ENTITY ID より、実体を引き当てる責務を表すインターフェイス。
 * 
 * @version $Id$
 * @author daisuke
 * @since 1.1.3
 */
public interface EntityResolver {
	
	/**
	 * このリゾルバが指定した実体参照を解決できる（実体を引き当てられる）かどうかを返す。
	 * 
	 * @param ref 実体参照
	 * @return 指定した実体参照を解決できる場合は{@code true}、そうでない場合は{@code false}
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.2
	 */
	boolean contains(EntityRef<?> ref) throws RepositoryException;
	
	/**
	 * このリゾルバが指定したIDを持つ実体を引き当てられるかどうかを返す。
	 * 
	 * @param id ENTITY ID
	 * @return 指定したIDを持つ実体を引き当てられる場合は{@code true}、そうでない場合は{@code false}
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.2
	 */
	boolean contains(UUID id) throws RepositoryException;
	
	/**
	 * 指定した実体参照が指す実体（{@link Entity}）を解決して返す。
	 * 
	 * <p>リポジトリは、この実体のクローンを返す。従って、取得した {@link Entity}に対して
	 * ミューテーションを起こしても、ストアした実体には影響を及ぼさない。</p>
	 * 
	 * <p>検索対象は子エンティティも含む。</p>
	 * 
	 * @param <E> {@link Entity}の型
	 * @param ref 実体参照
	 * @return 見つかった{@link Entity}
	 * @throws EntityNotFoundException このリゾルバが指定した実体を解決できない場合
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	<E extends Entity>E resolve(EntityRef<E> ref) throws RepositoryException;
	
	/**
	 * 指定したIDを持つ実体（{@link Entity}）を解決して返す。
	 * 
	 * <p>リポジトリは、この実体のクローンを返す。従って、取得した {@link Entity}に対して
	 * ミューテーションを起こしても、ストアした実体には影響を及ぼさない。</p>
	 * 
	 * <p>検索対象は子エンティティも含む。</p>
	 * 
	 * @param id ENTITY ID
	 * @return 見つかった{@link Entity}
	 * @throws EntityNotFoundException このリゾルバが指定した実体を解決できない場合
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	Entity resolve(UUID id) throws RepositoryException;
}
