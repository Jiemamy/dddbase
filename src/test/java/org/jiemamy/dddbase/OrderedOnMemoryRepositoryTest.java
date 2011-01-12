/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import org.jiemamy.dddbase.sample.SampleOrderedEntity;

/**
 * {@link OrderedOnMemoryRepository}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class OrderedOnMemoryRepositoryTest {
	
	private static final UUID ID1 = UUID.randomUUID();
	
	private static final UUID ID2 = UUID.randomUUID();
	
	private static final UUID ID3 = UUID.randomUUID();
	
	private static final UUID ID4 = UUID.randomUUID();
	
	private OrderedOnMemoryRepository<SampleOrderedEntity> repos;
	

	/**
	 * テストを初期化する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Before
	public void setUp() throws Exception {
		repos = new OrderedOnMemoryRepository<SampleOrderedEntity>();
	}
	
	/**
	 * {@link OrderedOnMemoryRepository#store(OrderedEntity)}の挙動テスト。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_store() throws Exception {
		SampleOrderedEntity e1 = new SampleOrderedEntity(ID1, "e1", 3);
		SampleOrderedEntity e2 = new SampleOrderedEntity(ID2, "e2", 1);
		SampleOrderedEntity e3a = new SampleOrderedEntity(ID3, "e3a", 1);
		SampleOrderedEntity e3b = new SampleOrderedEntity(ID3, "e3b", 0);
		SampleOrderedEntity e4 = new SampleOrderedEntity(ID4, "e4");
		
		assertThat(repos.getEntitiesAsList().size(), is(0));
		
		repos.store(e1); // 3でも0になる
		assertThat(repos.getEntitiesAsList().size(), is(1));
		assertThat(repos.getEntitiesAsList().get(0).getString(), is("e1"));
		check(repos.getEntitiesAsList());
		
		repos.store(e2); // 順当後付け
		assertThat(repos.getEntitiesAsList().size(), is(2));
		assertThat(repos.getEntitiesAsList().get(0).getString(), is("e1"));
		assertThat(repos.getEntitiesAsList().get(1).getString(), is("e2"));
		check(repos.getEntitiesAsList());
		
		repos.store(e3a); // 1の位置を奪う（挿入）
		assertThat(repos.getEntitiesAsList().size(), is(3));
		assertThat(repos.getEntitiesAsList().get(0).getString(), is("e1"));
		assertThat(repos.getEntitiesAsList().get(1).getString(), is("e3a"));
		assertThat(repos.getEntitiesAsList().get(2).getString(), is("e2"));
		check(repos.getEntitiesAsList());
		
		repos.store(e3b); // いくつであっても関係ない。e3aの位置を奪う
		assertThat(repos.getEntitiesAsList().size(), is(3));
		assertThat(repos.getEntitiesAsList().get(0).getString(), is("e1"));
		assertThat(repos.getEntitiesAsList().get(1).getString(), is("e3b"));
		assertThat(repos.getEntitiesAsList().get(2).getString(), is("e2"));
		check(repos.getEntitiesAsList());
		
		repos.store(e4); // 自動後付け
		assertThat(repos.getEntitiesAsList().size(), is(4));
		assertThat(repos.getEntitiesAsList().get(0).getString(), is("e1"));
		assertThat(repos.getEntitiesAsList().get(1).getString(), is("e3b"));
		assertThat(repos.getEntitiesAsList().get(2).getString(), is("e2"));
		assertThat(repos.getEntitiesAsList().get(3).getString(), is("e4"));
		check(repos.getEntitiesAsList());
		
		repos.swap(0, 0);
		assertThat(repos.getEntitiesAsList().size(), is(4));
		assertThat(repos.getEntitiesAsList().get(0).getString(), is("e1"));
		assertThat(repos.getEntitiesAsList().get(1).getString(), is("e3b"));
		assertThat(repos.getEntitiesAsList().get(2).getString(), is("e2"));
		assertThat(repos.getEntitiesAsList().get(3).getString(), is("e4"));
		check(repos.getEntitiesAsList());
		
		repos.swap(0, 3);
		assertThat(repos.getEntitiesAsList().size(), is(4));
		assertThat(repos.getEntitiesAsList().get(0).getString(), is("e4"));
		assertThat(repos.getEntitiesAsList().get(1).getString(), is("e3b"));
		assertThat(repos.getEntitiesAsList().get(2).getString(), is("e2"));
		assertThat(repos.getEntitiesAsList().get(3).getString(), is("e1"));
		check(repos.getEntitiesAsList());
		
		repos.swap(1, 3);
		assertThat(repos.getEntitiesAsList().size(), is(4));
		assertThat(repos.getEntitiesAsList().get(0).getString(), is("e4"));
		assertThat(repos.getEntitiesAsList().get(1).getString(), is("e1"));
		assertThat(repos.getEntitiesAsList().get(2).getString(), is("e2"));
		assertThat(repos.getEntitiesAsList().get(3).getString(), is("e3b"));
		check(repos.getEntitiesAsList());
	}
	
	/**
	 * コレクション取得2。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_コレクション取得() throws Exception {
		SampleOrderedEntity e1 = new SampleOrderedEntity(ID1, "e1");
		SampleOrderedEntity e3a = new SampleOrderedEntity(ID3, "e3a");
		SampleOrderedEntity e2 = new SampleOrderedEntity(ID2, "e2");
		SampleOrderedEntity e4 = new SampleOrderedEntity(ID4, "e4");
		SampleOrderedEntity e3b = new SampleOrderedEntity(ID3, "e3b");
		
		repos.store(e4);
		repos.store(e3a);
		repos.store(e2);
		repos.store(e1);
		repos.store(e3b); // overwirte
		
		// storeした順番が保持される。
		List<SampleOrderedEntity> list = repos.getEntitiesAsList();
		assertThat(list.size(), is(4));
		assertThat(list.get(0).getString(), is("e4"));
		assertThat(list.get(1).getString(), is("e3b"));
		assertThat(list.get(2).getString(), is("e2"));
		assertThat(list.get(3).getString(), is("e1"));
	}
	
	private void check(List<? extends OrderedEntity> list) {
		for (int i = 0; i < list.size(); i++) {
			assertThat(list.get(i).getIndex(), is(i));
		}
	}
}
