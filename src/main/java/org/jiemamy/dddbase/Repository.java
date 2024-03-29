/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
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

import java.util.Set;

/**
 * DDD における REPOSITORY を表すインターフェイス。
 * 
 * <p>{@link Entity}を保持・追跡する責務を持つ。</p> 
 * 
 * @param <E> 管理するエンティティの型
 * @version $Id$
 * @author daisuke
 * @since 1.0.0
 */
public interface Repository<E extends Entity> extends EntityResolver {
	
	/**
	 * 指定したリスナを登録する。
	 * 
	 * @param listener 登録するリスナ
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	void addListener(RepositoryEventListener listener);
	
	/**
	 * 指定したリスナを、通知の判断を行う戦略と共に登録する。
	 * 
	 * @param listener 登録するリスナ
	 * @param strategy リスナに対してEDITコマンドの通知を行うかどうかを判断する戦略。{@code null}の場合はデフォルト戦略を利用する。
	 * @throws IllegalArgumentException 引数{@code listener}に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	void addListener(RepositoryEventListener listener, DispatchStrategy strategy);
	
	/**
	 * 参照が指す実体（{@link Entity}）をリポジトリの管理下から外す。
	 * 
	 * <p>この {@link Entity} の子エンティティも同時に管理下から外す。</p>
	 * 
	 * @param ref 実体参照
	 * @return 削除した実体
	 * @throws EntityNotFoundException このリポジトリが指定した実体を管理していない場合
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	E delete(EntityRef<? extends E> ref) throws RepositoryException;
	
	/**
	 * 発生したイベントをリスナに通知する。
	 * 
	 * @param event 発生したイベント
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	void fireEvent(RepositoryEvent<?> event);
	
	/**
	 * 管理している主たる実体を{@link Set}として返す。
	 * 
	 * <p>返される{@link Set}やその要素{@link Entity}は他に
	 * 影響を及ぼさない独立したインスタンスである。つまりこの{@link Set}や
	 * その要素{@link Entity}へのミューテーションは、リポジトリに影響を及ぼさない。</p>
	 * 
	 * @return 管理 {@link Entity} の {@link Set}
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 * @since 1.0.0
	 */
	Set<E> getEntitiesAsSet() throws RepositoryException;
	
	/**
	 * 指定されたリスナを削除する。
	 * 
	 * @param listener 削除するリスナ
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	void removeListener(RepositoryEventListener listener);
	
	/**
	 * リスナに対する通知が必要かどうかを判断するためのデフォルト戦略を設定する。
	 * 
	 * @param defaultStrategy デフォルトで適用される{@link DispatchStrategy}の実装インスタンス
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	void setDefaultStrategy(DispatchStrategy defaultStrategy);
	
	/**
	 * {@link Entity}をリポジトリの管理下に置く。
	 * 
	 * <p>リポジトリは、この実体のクローンを管理する。従って、ストア後に{@code entity}に
	 * ミューテーションを起こしても、ストアした実体には影響を及ぼさない。</p>
	 * 
	 * <p>リポジトリが既に{@code entity}と同じIDを持つ{@link Entity}を管理していた場合は、
	 * 既存の{@link Entity}を削除し、新たに{@code entity}を配下に置く。</p>
	 * 
	 * <p>{@code entity}の子エンティティも同時に管理下に置き、 {@link #resolve}可能になる。</p>
	 * 
	 * @param entity 実体
	 * @return リポジトリが既に{@code entity}と同じIDを持つ{@link Entity}を管理していた場合、その削除された古い{@link Entity}、そうでない場合は{@code null}
	 * @throws RepositoryException リポジトリの実装（DBやファイル等）にアクセスできない場合
	 * @throws ConsistencyException 子エンティティのIDが、既に管理済みの{@link Entity}のIDと衝突した場合
	 * @throws ConsistencyException その他、エンティティ同士が満たすべき条件を満たさない場合
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 * @since 1.0.0
	 */
	E store(E entity) throws RepositoryException;
}
