package com.nick.topbook.module.life.model

class LifeRepository {

//	private val gao: Gson by lazy {
//		Gson()
//	}
//
//	fun getLifeTopics(start: Int, limit: Int): ArrayList<LifeTopic>? {
//		return UrlConstant.LIFE_TOPIC_PAGE
//			.get()
//			.addQuery("start", "$start")
//			.addQuery("limit", "$limit")
//			.send()
//			.getSuccess { string: String ->
//				run {
//					val json = JsonParser.parseString(string).asJsonObject.getAsJsonObject("data").getAsJsonArray("items").toString()
//					gao.fromJson(json, object : TypeToken<List<LifeTopic>>() {}.type)
//				}
//			}
//	}
//
//	fun getLifeTopicDetail(topicId: Long): LifeTopic? {
//		return UrlConstant.LIFE_TOPIC_DETAIL
//			.get()
//			.addQuery("topic_id", "$topicId")
//			.send()
//			.getSuccess { string: String ->
//				run {
//					val json = JsonParser.parseString(string).asJsonObject.getAsJsonObject("data").toString()
//					return@run gao.fromJson(json, LifeTopic::class.java)
//				}
//			}
//	}
//
//	fun getLifeTopicComments(topicId: Long, start: Int, limit: Int): ArrayList<LifeTopicComment>? {
//		return UrlConstant.LIFE_TOPIC_PAGE
//			.get()
//			.addQuery("topic_id", "$topicId")
//			.addQuery("start", "$start").addQuery("limit", "$limit")
//			.send()
//			.getSuccess { string: String ->
//				run {
//					val json = JsonParser.parseString(string).asJsonObject.getAsJsonObject("data").getAsJsonArray("items").toString()
//					return@run gao.fromJson(json, object : TypeToken<List<LifeTopicComment>>() {}.type)
//				}
//			}
//	}
}