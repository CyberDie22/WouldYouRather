package me.cyberdie22.wouldyourather

import kotlinx.serialization.Serializable

@Serializable
data class GameConfig(val name: String, val questions: List<Question>)
