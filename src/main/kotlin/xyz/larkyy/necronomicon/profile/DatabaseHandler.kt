package xyz.larkyy.necronomicon.profile

import com.google.gson.Gson
import xyz.larkyy.necronomicon.NecroNomicon
import xyz.larkyy.necronomicon.util.sendConsoleMessage
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import java.util.concurrent.CompletableFuture

class DatabaseHandler(private val plugin: NecroNomicon) {


    private val databaseFile: File = File(plugin.dataFolder, "database.db")
    private var activeConnection: Connection? = null

    init {
        setup()
    }

    private fun setup() {
        Class.forName("org.sqlite.JDBC")
        plugin.dataFolder.mkdirs()
        databaseFile.createNewFile()
        val connection = getConnection()
        val statement = connection!!.createStatement()
        statement.execute(
            """CREATE TABLE IF NOT EXISTS playerprofiles_profile (
                    UniqueID NVARCHAR(64) UNIQUE PRIMARY KEY,
                    Username NVARCHAR(64) NOT NULL,
                    Badges text NOT NULL,
                    Status NVARCHAR(64),
                    Theme NVARCHAR(64),
                    Background NVARCHAR(64),
                    Reputation INT NOT NULL
                );"""
        )
        statement.close()
    }

    fun loadProfile(uuid: UUID): CompletableFuture<PlayerProfile?> {
        return CompletableFuture.supplyAsync {
            plugin.sendConsoleMessage("LOADING PROFILE")
            val connection = getConnection()
            try {
                connection!!.prepareStatement(
                    """
            SELECT Username, Badges, Status, Theme, Background, Reputation
            FROM playerprofiles_profile
            WHERE UniqueID = ?;
            """
                ).use { ps ->
                    ps.setString(1, uuid.toString())

                    val rs = ps.executeQuery()

                    if (rs.next()) {
                        val userName = rs.getString("Username")
                        val badgesJson = rs.getString("Badges")
                        val status = rs.getString("Status")
                        val theme = rs.getString("Theme")
                        val background = rs.getString("Background")
                        val repuration = rs.getInt("Reputation")

                        val badges = Gson().fromJson(badgesJson, ArrayList<String>().javaClass)

                        plugin.sendConsoleMessage("RETURNING PROFILE")
                        return@supplyAsync PlayerProfile(uuid, userName, badges, status, theme, background, repuration)
                    } else {
                        plugin.sendConsoleMessage("LOADING NULL")
                        return@supplyAsync null
                    }

                }
            } catch (e: SQLException) {
                e.printStackTrace()
                throw java.lang.RuntimeException(e)
            }
        }.exceptionally {ex ->
            ex.printStackTrace()
            null
        }
    }

    fun loadProfile(username: String): CompletableFuture<PlayerProfile?> {
        return CompletableFuture.supplyAsync {
            val connection = getConnection()
            try {
                connection!!.prepareStatement(
                    """
            SELECT UniqueID, Username, Badges, Status, Theme, Background, Reputation
            FROM playerprofiles_profile
            WHERE Username = ?;
            """.trimIndent()
                ).use { ps ->
                    ps.setString(1, username)

                    val rs = ps.executeQuery()

                    if (rs.next()) {
                        val uuid = UUID.fromString(rs.getString("UniqueID"))
                        val userName = rs.getString("Username")
                        val badgesJson = rs.getString("Badges")
                        val status = rs.getString("Status")
                        val theme = rs.getString("Theme")
                        val background = rs.getString("Background")
                        val repuration = rs.getInt("Reputation")

                        val badges = Gson().fromJson(badgesJson, ArrayList<String>().javaClass)

                        return@supplyAsync PlayerProfile(uuid, userName, badges, status, theme, background, repuration)
                    } else {
                        return@supplyAsync null
                    }

                }
            } catch (e: SQLException) {
                throw java.lang.RuntimeException(e)
            }
        }
    }

    fun saveProfile(playerProfile: PlayerProfile): CompletableFuture<Void> {
        plugin.sendConsoleMessage("Saving new profile")
        return CompletableFuture.supplyAsync {
            getConnection().use { connection ->
                connection?.prepareStatement("" +
                        "INSERT OR REPLACE INTO playerprofiles_profile (UniqueID, Username, Badges, Status, Theme, Background, Reputation) VALUES (?, ?, ?, ?, ?, ?, ?);")
                    ?.use { ps ->
                        ps.setString(1,playerProfile.uuid.toString())
                        ps.setString(2,playerProfile.userName)
                        ps.setString(3,Gson().toJson(playerProfile.badges))
                        ps.setString(4,playerProfile.status)
                        ps.setString(5,playerProfile.theme)
                        ps.setString(6,playerProfile.background)
                        ps.setInt(7,playerProfile.reputation)

                        ps.execute()

                        return@supplyAsync null
                    }
            }
        }
    }

    private fun getConnection(): Connection? {
        try {
            if (activeConnection != null && !activeConnection!!.isClosed) return this.activeConnection
            Class.forName("org.sqlite.SQLiteDataSource")
            this.activeConnection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseFile.getPath())
        } catch (e: SQLException) {
            throw RuntimeException(e)
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        }
        return this.activeConnection
    }

}