package com.example.purpleapex.news.data.network

import com.example.purpleapex.news.domain.NewsArticle
import com.example.purpleapex.news.domain.NewsClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.*

class KtorNewsClient(
    private val client: HttpClient
) : NewsClient {

    override suspend fun getLatest(): List<NewsArticle> {
        val url = "https://site.api.espn.com/apis/site/v2/sports/racing/f1/news"
        val root: JsonObject = client.get(url).body()
        val articles = (root["articles"] as? JsonArray) ?: return emptyList()
        return articles.mapNotNull { it.asArticleOrNull() }
    }

    private fun JsonElement?.asArticleOrNull(): NewsArticle? {
        val obj = this as? JsonObject ?: return null
        val id = obj["dataSourceIdentifier"]?.asString() ?: obj["id"]?.asString() ?: return null
        val headline = obj["headline"]?.asString() ?: return null
        val description = obj["description"]?.asString()
        val published = obj["published"]?.asString() ?: obj["lastModified"]?.asString()
        val linkUrl = obj["links"]?.jsonObject?.get("web")?.jsonObject?.get("href")?.asString()
            ?: obj["link"]?.asString()
        val imageUrl = chooseImageUrl(obj)
        return NewsArticle(
            id = id,
            headline = headline,
            description = description,
            published = published,
            imageUrl = imageUrl,
            linkUrl = linkUrl
        )
    }

    private fun chooseImageUrl(obj: JsonObject): String? {
        // ESPN articles may have images array or image object
        val images = obj["images"]
        if (images is JsonArray) {
            // Prefer larger images
            val first = images.firstOrNull()?.jsonObject
            val href = first?.get("url")?.asString() ?: first?.get("href")?.asString()
            if (href != null) return href
        }
        val imageObj = obj["image"] as? JsonObject
        val href = imageObj?.get("url")?.asString() ?: imageObj?.get("href")?.asString()
        if (href != null) return href
        // Sometimes nested in links.images[0].href
        val fromLinks = (obj["links"] as? JsonObject)
            ?.get("images") as? JsonArray
        val firstLinkImage = fromLinks?.firstOrNull()?.jsonObject?.get("href")?.asString()
        return firstLinkImage
    }

    private fun JsonElement.asString(): String? = when (this) {
        is JsonNull -> null
        is JsonObject -> this["text"]?.let { it.safeContent() } ?: this["href"]?.let { it.safeContent() }
        else -> safeContent()
    }

    private fun JsonElement.safeContent(): String? = try {
        this.jsonPrimitive.content
    } catch (e: Exception) {
        null
    }
}
