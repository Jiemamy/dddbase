/*
 * Copyright 2010 Daisuke Miyamoto.
 * Created on 2010/12/07
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

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * DDD における REPOSITORY を表すインターフェイス。
 * 
 * <p>{@link Entity}を保持・追跡する責務を持つ。</p> 
 * 
 * @param <T> 管理するエンティティの型
 * @version $Id$
 * @author daisuke
 */
public interface Repository<T extends Entity> extends Cloneable {
	
	/**
	 * リポジトリのクローンを取得する。
	 * 
	 * @return clone クローン
	 * @since 0.3
	 */
	Repository<T> clone();
	
	/**
	 * このリポジトリが指定した実体を管理しているかどうかを返す。
	 * 
	 * @param ref 実体参照
	 * @return このリポジトリが指定した実体を管理している場合は{@code true}、そうでない場合は{@code false}
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 */
	boolean contains(EntityRef<?> ref) throws RepositoryException;
	
	/**
	 * このリポジトリが指定した実体を管理しているかどうかを返す。
	 * 
	 * @param id ENTITY ID
	 * @return このリポジトリが指定した実体を管理している場合は{@code true}、そうでない場合は{@code false}
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 */
	boolean contains(UUID id) throws RepositoryException;
	
	/**
	 * 参照が指す実体（{@link Entity}）をリポジトリの管理下から外す。
	 * 
	 * <p>この {@link Entity} の子エンティティも同時に管理下から外す。</p>
	 * 
	 * @param ref 実体参照
	 * @return 削除した実体
	 * @throws EntityNotFoundException このリポジトリが指定した実体を管理していない場合
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 */
	T delete(EntityRef<? extends T> ref) throws RepositoryException;
	
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
	 */
	List<T> getEntitiesAsList() throws RepositoryException;
	
	/**
	 * 管理している実体を {@link Set} として返す。
	 * 
	 * <p>返される{@link Set}やその要素{@link Entity}は他に
	 * 影響を及ぼさない独立したインスタンスである。つまりこの{@link Set}や
	 * その要素{@link Entity}へのミューテーションは、リポジトリに影響を及ぼさない。</p>
	 * 
	 * @return 管理 {@link Entity} の {@link Set}
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 */
	Set<T> getEntitiesAsSet() throws RepositoryException;
	
	/**
	 * リポジトリが管理する {@link Entity} の中から、指定した参照が指す実体（{@link Entity}）を探して返す。
	 * 
	 * <p>リポジトリは、この実体のクローンを返す。従って、取得した {@link Entity}に対して
	 * ミューテーションを起こしても、ストアした実体には影響を及ぼさない。</p>
	 * 
	 * <p>検索対象は子エンティティも含む。</p>
	 * 
	 * @param <E> {@link Entity}の型
	 * @param ref 実体参照
	 * @return 見つかった{@link Entity}
	 * @throws EntityNotFoundException このリポジトリが指定した実体を管理していない場合
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 */
	<E extends Entity>E resolve(EntityRef<E> ref) throws RepositoryException;
	
	/**
	 * リポジトリが管理する {@link Entity} の中から、指定したIDを持つ実体（{@link Entity}）を探して返す。
	 * 
	 * <p>リポジトリは、この実体のクローンを返す。従って、取得した {@link Entity}に対して
	 * ミューテーションを起こしても、ストアした実体には影響を及ぼさない。</p>
	 * 
	 * <p>検索対象は子エンティティも含む。</p>
	 * 
	 * @param id ENTITY ID
	 * @return 見つかった{@link Entity}
	 * @throws EntityNotFoundException このリポジトリが指定した実体を管理していない場合
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 */
	Entity resolve(UUID id) throws RepositoryException;
	
	/**
	 * 実体（{@link Entity}）をリポジトリの管理下に置く。
	 * 
	 * <p>リポジトリは、この実体のクローンを管理する。従って、ストア後にこのエンティティに
	 * ミューテーションを起こしても、ストアした実体には影響を及ぼさない。</p>
	 * 
	 * <p>この {@link Entity} のIDと同じIDを持つ {@link Entity} が既に管理下にある場合は、
	 * 既存の {@link Entity} を削除し、今回指定した {@link Entity} を配下に置く。</p>
	 * 
	 * <p>この {@link Entity} の子エンティティも同時に管理下に置く。</p>
	 * 
	 * @param entity 実体
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 * @throws IllegalArgumentException 子エンティティのIDが、既に管理済みの{@link Entity}のIDと衝突した場合
	 */
	void store(T entity) throws RepositoryException;
}
