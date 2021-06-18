package me.cyberdie22.wouldyourather

import kotlinx.serialization.Serializable

@Serializable
data class Question(val name: String, val answers: Set<Answer>)
