package com.nick.topbook.module.life.model

import java.io.Serializable

data class LifeTopic(var topicId: Long, var abstract: String, var avatarUrl: String, var createTime: String,
                     var nickname: String, var title: String, var userId: String, var viewpointId: Long, var content: String,
                     var viewpointsTotal: Long, var viewpoints: List<ViewPoint>
) : Serializable

data class LifeTopicComment(var viewpointId: Long, var topicId: Long, val content: String, var totalLike: Int,
                            var stick: Int, var like: Boolean, var userId: String, var createTime: String,
                            var nickname: String, var avatarUrl: String
) : Serializable

data class ViewPoint(val viewpointId: Long, val content: String, val createTime: String, val avatarUrl: String, val nickname: String, val userId: String) : Serializable