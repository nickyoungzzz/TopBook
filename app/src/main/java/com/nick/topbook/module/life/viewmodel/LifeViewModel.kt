package com.nick.topbook.module.life.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nick.topbook.module.life.model.LifeRepository
import com.nick.topbook.module.life.model.LifeTopic
import com.nick.topbook.module.life.model.LifeTopicComment
import javax.inject.Inject

class LifeViewModel : ViewModel() {

	val lifeTopics = MutableLiveData<ArrayList<LifeTopic>>()

	val lifeTopicDetail = MutableLiveData<LifeTopic>()

	val lifeTopicComments = MutableLiveData<ArrayList<LifeTopicComment>>()

	@set:Inject
	lateinit var lifeModel: LifeRepository

	fun getLifeTopics(start: Int, limit: Int) {
//		viewModelScope.launch(Dispatchers.IO) {
//			lifeModel.getLifeTopics(start, limit)?.let {
//				lifeTopics.postValue(it)
//			}
//		}
	}

	fun getLifeTopicDetail(topic_id: Long) {
//		viewModelScope.launch(Dispatchers.IO) {
//			lifeModel.getLifeTopicDetail(topic_id)?.let {
//				lifeTopicDetail.postValue(it)
//			}
//		}
	}

	fun getLifeTopicComments(topic_id: Long, start: Int, limit: Int) {
//		viewModelScope.launch(Dispatchers.IO) {
//			lifeModel.getLifeTopicComments(topic_id, start, limit)?.let {
//				lifeTopicComments.postValue(it)
//			}
//		}
	}
}