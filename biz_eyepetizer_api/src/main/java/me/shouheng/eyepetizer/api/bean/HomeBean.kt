package me.shouheng.eyepetizer.api.bean

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

/** Eye data */
open class HomeBean : Serializable, RealmObject() {
    var issueList: RealmList<Issue>? = null
    var newestIssueType: String? = null
    var nextPageUrl: String? = null
    var nextPublishTime: Long? = null
    /** We only cache the first page? = null so we can make the primary key the same. */
    @PrimaryKey var id: String = "homebean"
}

/** Eye data */
open class Issue: Serializable, RealmObject() {
    var count: Int? = null
    var date: Long? = null
    var itemList: RealmList<Item>? = null
    var publishTime: Long? = null
    var releaseTime: Long? = null
    var type: String? = null
}

/** Eye data */
open class Item: Serializable, RealmObject() {
    var `data`: Data? = null
    var adIndex: Int? = null
    var id: Int? = null
    var tag: String? = null
    var type: String? = null
}

/** Eye data */
open class Data: Serializable, RealmObject() {
    var ad: Boolean? = null
    var author: Author? = null
    var campaign: String? = null
    var category: String? = null
    var collected: Boolean? = null
    var consumption: Consumption? = null
    var cover: Cover? = null
    var dataType: String? = null
    var date: Long? = null
    var description: String? = null
    var descriptionEditor: String? = null
    var descriptionPgc: String? = null
    var duration: Int? = null
    var favoriteAdTrack: String? = null
    var id: Int? = null
    var idx: Int? = null
    var ifLimitVideo: Boolean? = null
    var label: String? = null
    var labelList: RealmList<String>? = null
    var lastViewTime: String? = null
    var library: String? = null
    var playInfo: RealmList<PlayInfo>? = null
    var playUrl: String? = null
    var played: Boolean? = null
    var playlists: String? = null
    var promotion: String? = null
    var provider: Provider? = null
    var releaseTime: Long? = null
    var remark: String? = null
    var resourceType: String? = null
    var searchWeight: Int? = null
    var shareAdTrack: String? = null
    var slogan: String? = null
    var src: String? = null
    var subtitles: RealmList<String>? = null
    var tags: RealmList<Tag>? = null
    var thumbPlayUrl: String? = null
    var title: String? = null
    var titlePgc: String? = null
    var type: String? = null
    var waterMarks: String? = null
    var webAdTrack: String? = null
    var webUrl: WebUrl? = null
}

/** Eye data */
open class PlayInfo: Serializable, RealmObject() {
    var height: Int? = null
    var name: String? = null
    var type: String? = null
    var url: String? = null
    var urlList: RealmList<Url>? = null
    var width: Int? = null
}

/** Eye data */
open class Url : Serializable, RealmObject() {
    var name: String? = null
    var size: Int? = null
    var url: String? = null
}

/** Eye data */
open class Provider : Serializable, RealmObject() {
    var alias: String? = null
    var icon: String? = null
    var name: String? = null
}

/** Eye data */
open class WebUrl : Serializable, RealmObject() {
    var forWeibo: String? = null
    var raw: String? = null
}

/** Eye data */
open class Tag : Serializable, RealmObject() {
    var actionUrl: String? = null
    var adTrack: String? = null
    var bgPicture: String? = null
    var childTagIdList: String? = null
    var childTagList: String? = null
    var communityIndex: Int? = null
    var desc: String? = null
    var headerImage: String? = null
    var id: Int? = null
    var name: String? = null
    var tagRecType: String? = null
}

/** Eye data */
open class Consumption : Serializable, RealmObject() {
    var collectionCount: Int? = null
    var replyCount: Int? = null
    var shareCount: Int? = null
}

/** Eye data */
open class Cover : Serializable, RealmObject() {
    var blurred: String? = null
    var detail: String? = null
    var feed: String? = null
    var homepage: String? = null
    var sharing: String? = null
}

/** Eye data */
open class Author : Serializable, RealmObject() {
    var adTrack: String? = null
    var approvedNotReadyVideoCount: Int? = null
    var description: String? = null
    var expert: Boolean? = null
    var follow: Follow? = null
    var icon: String? = null
    var id: Int? = null
    var ifPgc: Boolean? = null
    var latestReleaseTime: Long? = null
    var link: String? = null
    var name: String? = null
    var recSort: Int? = null
    var shield: Shield? = null
    var videoNum: Int? = null
}

/** Eye data */
open class Shield : Serializable, RealmObject() {
    var itemId: Int? = null
    var itemType: String? = null
    var shielded: Boolean? = null
}

/** Eye data */
open class Follow: Serializable, RealmObject() {
    var followed: Boolean? = null
    var itemId: Int? = null
    var itemType: String? = null
}