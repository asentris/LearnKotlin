package other

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream

class JsonParsing {

    init {
        val inputStream = this.javaClass.classLoader.getResource("app_import.txt").openStream()
        val jsonString = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        while (inputStream.available() != 0) {
            val length = inputStream.read(buffer)
            jsonString.write(buffer, 0, length)
        }
        val string = jsonString.toString("UTF-8")
//        println(string)

        val serializer: KSerializer<DatabaseImport> = DatabaseImport.serializer()
        val jsonFormat = Json { ignoreUnknownKeys = true }
        val data = jsonFormat.decodeFromString(serializer, string)
        println(data.rarity.first())
        println(data.size.first())
        println(data.version.first())
    }
}

@Serializable
data class DatabaseImport(
    @SerialName("rarity")
    val rarity: List<Rarity>,

    @SerialName("size")
    val size: List<Size>,

    @SerialName("version")
    val version: List<Version>,
)

@Serializable
data class Rarity(
    val rowid: Int,
    val value: String,
    val modified: String,
)

@Serializable
data class Size(
    val rowid: Int,
    val value: String,
    val modified: String,
)

@Serializable
data class Version(
    @SerialName("tablename")
    val tableName: String,
    val number: Int,
)