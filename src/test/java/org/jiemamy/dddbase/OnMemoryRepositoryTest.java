/*
 * Copyright 2007-2010 Jiemamy Project and the Others.
 * Created on 2010/12/17
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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Iterables;

import org.junit.Before;
import org.junit.Test;

import org.jiemamy.dddbase.sample.SampleMainEntity;
import org.jiemamy.dddbase.sample.SampleSubEntity;

/**
 * {@link OnMemoryRepository}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class OnMemoryRepositoryTest {
	
	private static final UUID ID0 = UUID.randomUUID();
	
	private static final UUID ID1 = UUID.randomUUID();
	
	private static final UUID ID2 = UUID.randomUUID();
	
	private static final UUID ID3 = UUID.randomUUID();
	
	private static final UUID ID4 = UUID.randomUUID();
	
	private OnMemoryRepository<SampleMainEntity> repos;
	

	/**
	 * テストを初期化する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Before
	public void setUp() throws Exception {
		repos = new OnMemoryRepository<SampleMainEntity>();
	}
	
	/**
	 * エンティティを保存削除して、保存エンティティ数が正常に動いていることを確認。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_エンティティを保存削除して_保存エンティティ数が正常に動いていることを確認() throws Exception {
		assertThat(repos.managedMainEntityCount(), is(0));
		assertThat(repos.managedSubEntityCount(), is(0));
		
		SampleMainEntity e1 = new SampleMainEntity(ID1);
		repos.store(e1);
		
		assertThat(repos.managedMainEntityCount(), is(1));
		assertThat(repos.managedSubEntityCount(), is(0));
		
		repos.delete(e1.toReference());
		
		assertThat(repos.managedMainEntityCount(), is(0));
		assertThat(repos.managedSubEntityCount(), is(0));
		
		e1.addChild(new SampleSubEntity(ID2));
		repos.store(e1);
		
		assertThat(repos.managedMainEntityCount(), is(1));
		assertThat(repos.managedSubEntityCount(), is(1));
		
		repos.delete(e1.toReference());
		
		assertThat(repos.managedMainEntityCount(), is(0));
		assertThat(repos.managedSubEntityCount(), is(0));
	}
	
	/**
	 * IDから実体を解決できる。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_referenceから実体を解決できる() throws Exception {
		SampleMainEntity e1 = new SampleMainEntity(ID1);
		SampleSubEntity se1 = new SampleSubEntity(ID2);
		e1.addChild(se1);
		repos.store(e1);
		
		assertThat(repos.resolve(ID1), is(equalTo((Entity) e1)));
		assertThat(repos.resolve(ID2), is(equalTo((Entity) se1)));
	}
	
	/**
	 * referenceから実体を解決できる。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test03_referenceから実体を解決できる() throws Exception {
		SampleMainEntity e1 = new SampleMainEntity(ID1);
		SampleSubEntity se1 = new SampleSubEntity(ID2);
		e1.addChild(se1);
		repos.store(e1);
		
		assertThat(repos.resolve(e1.toReference()), is(equalTo(e1)));
		assertThat(repos.resolve(se1.toReference()), is(equalTo(se1)));
	}
	
	/**
	 * リポジトリにストアしたオブジェクトと、リゾルブで得たオブジェクトは等価だが、同じインスタンスではない。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test04_リポジトリにストアしたオブジェクトと_リゾルブで得たオブジェクトは等価だが_同じインスタンスではない() throws Exception {
		SampleMainEntity e1 = new SampleMainEntity(ID1);
		SampleSubEntity se1 = new SampleSubEntity(ID2);
		e1.addChild(se1);
		repos.store(e1);
		
		Entity resolved1 = repos.resolve(ID1);
		Entity resolved2 = repos.resolve(ID2);
		
		assertThat(resolved1, is(equalTo((Entity) e1)));
		assertThat(resolved2, is(equalTo((Entity) se1)));
		assertThat(resolved1, is(not(sameInstance((Entity) e1))));
		assertThat(resolved2, is(not(sameInstance((Entity) se1))));
	}
	
	/**
	 * 削除しようとしたエンティティが見つからない場合は例外。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test(expected = EntityNotFoundException.class)
	public void test05_削除しようとしたエンティティが見つからない場合は例外() throws Exception {
		repos.delete(new DefaultEntityRef<SampleMainEntity>(ID0));
	}
	
	/**
	 * 同じIDのエンティティを複数回storeした場合は後勝ちの上書きになる。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test06_同じIDのエンティティを複数回storeした場合は後勝ちの上書きになる() throws Exception {
		SampleMainEntity e1old = new SampleMainEntity(ID1);
		SampleSubEntity se1old = new SampleSubEntity(ID2);
		e1old.getValues().add("foo");
		e1old.addChild(se1old);
		repos.store(e1old); // 新規CREATE
		
		assertThat(Iterables.getOnlyElement(e1old.getValues()), is("foo"));
		assertThat(e1old.getSubEntities().size(), is(1));
		assertThat(Iterables.getOnlyElement(repos.resolve(e1old.toReference()).getValues()), is("foo"));
		
		SampleMainEntity e1new = new SampleMainEntity(ID1);
		e1new.setString("bar");
		repos.store(e1new); // 上書きUPDATE
		
		assertThat(Iterables.getOnlyElement(e1old.getValues()), is("foo"));
		assertThat(e1old.getSubEntities().size(), is(1));
		assertThat(e1new.getString(), is("bar"));
		assertThat(e1new.getSubEntities().size(), is(0));
		assertThat(repos.resolve(e1old.toReference()).getString(), is("bar"));
		assertThat(repos.resolve(e1old.toReference()).getSubEntities().size(), is(0));
		assertThat(repos.resolve(e1new.toReference()).getString(), is("bar"));
		assertThat(repos.resolve(e1new.toReference()).getSubEntities().size(), is(0));
	}
	
	/**
	 * store時にサブエンティティ同士のIDが衝突する場合はIAEをスローする1。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test07_store時にサブエンティティ同士のIDが衝突する場合はIAEをスローする1() throws Exception {
		SampleMainEntity e1 = new SampleMainEntity(ID1);
		SampleSubEntity se1a = new SampleSubEntity(ID2);
		e1.addChild(se1a);
		
		SampleMainEntity e2 = new SampleMainEntity(ID3);
		SampleSubEntity se1b = new SampleSubEntity(ID2);
		e2.addChild(se1b);
		
		repos.store(e1);
		repos.store(e2);
	}
	
	/**
	 * store時にサブエンティティ同士のIDが衝突する場合はIAEをスローする2。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test08_store時にサブエンティティ同士のIDが衝突する場合はIAEをスローする2() throws Exception {
		SampleMainEntity e1 = new SampleMainEntity(ID1);
		SampleSubEntity se1a = new SampleSubEntity(ID2);
		e1.addChild(se1a);
		
		SampleMainEntity e2old = new SampleMainEntity(ID3);
		SampleSubEntity se2 = new SampleSubEntity(ID4);
		e2old.addChild(se2);
		
		SampleMainEntity e2new = new SampleMainEntity(ID3);
		SampleSubEntity se1b = new SampleSubEntity(ID2);
		e2new.addChild(se1b);
		
		repos.store(e1);
		repos.store(e2old);
		repos.store(e2new);
	}
	
	/**
	 * store時にサブエンティティ同士のIDが衝突する場合はIAEをスローする2。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test09_store時にサブエンティティ同士のIDが衝突しても_更新でで問題ない場合は通る() throws Exception {
		SampleMainEntity e1 = new SampleMainEntity(ID1);
		SampleSubEntity se2 = new SampleSubEntity(ID4);
		e1.addChild(se2);
		
		SampleMainEntity e2old = new SampleMainEntity(ID3);
		SampleSubEntity se1a = new SampleSubEntity(ID2);
		e2old.addChild(se1a);
		
		SampleMainEntity e2new = new SampleMainEntity(ID3);
		SampleSubEntity se1b = new SampleSubEntity(ID2);
		e2new.addChild(se1b);
		
		repos.store(e1);
		repos.store(e2old);
		repos.store(e2new);
	}
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test10_コレクション取得() throws Exception {
		SampleMainEntity e1 = new SampleMainEntity(ID1);
		SampleMainEntity e3 = new SampleMainEntity(ID3);
		SampleMainEntity e2 = new SampleMainEntity(ID2);
		SampleMainEntity e4 = new SampleMainEntity(ID4);
		
		repos.store(e4);
		repos.store(e3);
		repos.store(e2);
		repos.store(e1);
		
		// どちらも同要素のコレクションであるべき
		assertThat(repos.getEntitiesAsSet(), hasItems(e1, e2, e3, e4));
		assertThat(repos.getEntitiesAsList(), hasItems(e1, e2, e3, e4));
		
		// storeした順番が保持される。
		List<SampleMainEntity> list = repos.getEntitiesAsList();
		assertThat(list.get(0), is(equalTo(e4)));
		assertThat(list.get(1), is(equalTo(e3)));
		assertThat(list.get(2), is(equalTo(e2)));
		assertThat(list.get(3), is(equalTo(e1)));
	}
}
