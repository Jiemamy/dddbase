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

/**
 * リポジトリ内でのモデルの整合性が維持できない場合。
 * 
 * @version $Id$
 * @author daisuke
 * @since 1.2.0
 */
@SuppressWarnings("serial")
public class ConsistencyException extends IllegalArgumentException {
	
	/**
	 * インスタンスを生成する。
	 */
	public ConsistencyException() {
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param message 例外メッセージ
	 */
	public ConsistencyException(String message) {
		super(message);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param message 例外メッセージ
	 * @param cause 起因例外
	 */
	public ConsistencyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param cause 起因例外
	 */
	public ConsistencyException(Throwable cause) {
		super(cause);
	}
}
