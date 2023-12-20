package xyz.larkyy.necronomicon.profile

import java.util.ArrayList
import java.util.UUID

data class PlayerProfile(
    val uuid: UUID,
    val userName: String,
    val badges: ArrayList<String>,
    val status: String?,
    val theme: String?,
    val background: String?,
    val reputation: Int
)
