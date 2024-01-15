package xyz.larkyy.necronomicon.profile

import java.util.ArrayList
import java.util.UUID

data class PlayerProfile(
    val uuid: UUID,
    val userName: String,
    val badges: ArrayList<String>,
    var status: String?,
    var theme: String?,
    var background: String?,
    var reputation: Int
)
