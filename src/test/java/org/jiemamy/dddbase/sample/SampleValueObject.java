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
package org.jiemamy.dddbase.sample;

import org.jiemamy.dddbase.ValueObject;

/**
 * テスト用サンプル値オブジェクト実装。
 * 
 * @version $Id$
 * @author daisuke
 */
public final class SampleValueObject implements ValueObject {
	
	private final int number;
	
	private final String string;
	

	/**
	 * インスタンスを生成する。
	 * 
	 * @param number 数値
	 * @param string 文字列
	 */
	public SampleValueObject(int number, String string) {
		this.number = number;
		this.string = string;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SampleValueObject other = (SampleValueObject) obj;
		if (number != other.number) {
			return false;
		}
		if (string == null) {
			if (other.string != null) {
				return false;
			}
		} else if (!string.equals(other.string)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 数値を取得する。
	 * 
	 * @return 数値
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * 文字列を取得する。
	 * 
	 * @return 文字列
	 */
	public String getString() {
		return string;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + ((string == null) ? 0 : string.hashCode());
		return result;
	}
	
}
