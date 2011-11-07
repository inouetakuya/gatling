/**
 * Copyright 2011 eBusiness Information, Groupe Excilys (www.excilys.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.excilys.ebi.gatling.http.check.body

import com.excilys.ebi.gatling.core.context.Context
import com.excilys.ebi.gatling.core.util.StringHelper.EMPTY
import com.excilys.ebi.gatling.http.check.HttpCheckBuilder
import com.excilys.ebi.gatling.http.request.HttpPhase.CompletePageReceived
import com.excilys.ebi.gatling.core.check.strategy.EqualityCheckStrategy
import com.excilys.ebi.gatling.core.check.strategy.ExistenceCheckStrategy
import com.excilys.ebi.gatling.core.check.strategy.NonEqualityCheckStrategy
import com.excilys.ebi.gatling.core.check.strategy.NonExistenceCheckStrategy
import com.excilys.ebi.gatling.core.check.strategy.CheckStrategy

object HttpBodyXPathCheckBuilder {

	def xpathEquals(what: Context => String, expected: String) = new HttpBodyXPathCheckBuilder(what, Some(EMPTY), EqualityCheckStrategy, Some(expected))
	def xpathEquals(expression: String, expected: String): HttpBodyXPathCheckBuilder = xpathEquals((c: Context) => expression, expected)

	def xpathNotEquals(what: Context => String, expected: String) = new HttpBodyXPathCheckBuilder(what, Some(EMPTY), NonEqualityCheckStrategy, Some(expected))
	def xpathNotEquals(expression: String, expected: String): HttpBodyXPathCheckBuilder = xpathNotEquals((c: Context) => expression, expected)

	def xpathExists(what: Context => String) = new HttpBodyXPathCheckBuilder(what, Some(EMPTY), ExistenceCheckStrategy, None)
	def xpathExists(expression: String): HttpBodyXPathCheckBuilder = xpathExists((c: Context) => expression)

	def xpathNotExists(what: Context => String) = new HttpBodyXPathCheckBuilder(what, Some(EMPTY), NonExistenceCheckStrategy, None)
	def xpathNotExists(expression: String): HttpBodyXPathCheckBuilder = xpathNotExists((c: Context) => expression)

	def xpath(what: Context => String) = xpathExists(what)
	def xpath(expression: String) = xpathExists(expression)
}

class HttpBodyXPathCheckBuilder(what: Context => String, to: Option[String], strategy: CheckStrategy, expected: Option[String])
		extends HttpCheckBuilder[HttpBodyXPathCheckBuilder](what, to, strategy, expected, CompletePageReceived) {

	def newInstance(what: Context => String, to: Option[String], checkType: CheckStrategy, expected: Option[String]) = {
		new HttpBodyXPathCheckBuilder(what, to, checkType, expected)
	}

	def build = new HttpBodyXPathCheck(what, to, strategy, expected)
}
