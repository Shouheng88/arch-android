package me.shouheng.api.bean

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

/**
 * Guokr news bean. Thr realm require the bean:
 *
 * 1. must be open;
 * 2. must extend from RealmObject or use @RealmClass annotation;
 * 3. must not use field of type [Any];
 * 4. must not use nested classes.
 *
 * Also, realm is writing in JNI, so it's not easy to custom and fix some bugs.
 */
open class GuokrNews : Serializable, RealmObject() {
    /**
     * now : 2018-06-10T14:47:55.517577+08:00
     * ok : true
     * limit : 20
     * result : [{"image":"","is_replyable":true,"channels":[],"channel_keys":[],"preface":"","id":443006,"subject":{"url":"http://www.guokr.com/scientific/subject/medicine/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"医学","key":"medicine","articles_count":1819},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"ukey":"1jodqq","is_title_authorized":true,"nickname":"飞刀断雨","master_category":"personal","amended_reliability":"0","is_exists":true,"title":"临床医学专业，玄牝之门小组管理员","url":"http://www.guokr.com/i/0093516434/","gender":"female","followers_count":221,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/1jodqq.json"},"image_description":"","is_show_summary":false,"minisite_key":"health","image_info":{"url":"https://2-im.guokr.com/bfjAMXmfv0qwjQtnzLVOpJt-vu98su4VvVcHR_sUoh9KAQAA8AAAAEpQ.jpg","width":330,"height":240},"subject_key":"medicine","minisite":{"name":"健康朝九晚五","url":"http://www.guokr.com/site/health/","introduction":"每天的朝九晚五，上几篇健康小文，有趣、靠谱，还很给力","key":"health","date_created":"2010-10-20T16:20:43+08:00","icon":"https://2-im.guokr.com/gDNaY8kFHIUnyDRLBlb1hWY3fwK9eAuV5icTVSuh7TNuAAAAWgAAAEpQ.jpg"},"tags":["转胎药","转胎丸"],"date_published":"2018-06-08T14:26:01+08:00","video_content":"","authors":[{"ukey":"1jodqq","is_title_authorized":true,"nickname":"飞刀断雨","master_category":"personal","amended_reliability":"0","is_exists":true,"title":"临床医学专业，玄牝之门小组管理员","url":"http://www.guokr.com/i/0093516434/","gender":"female","followers_count":221,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/1jodqq.json"}],"replies_count":13,"is_author_external":false,"recommends_count":0,"title_hide":"\u201c转胎丸\u201d到底是个什么玩意？","date_modified":"2018-06-08T14:59:40.953735+08:00","url":"http://www.guokr.com/article/443006/","title":"\u201c转胎丸\u201d到底是个什么玩意？","small_image":"https://2-im.guokr.com/bfjAMXmfv0qwjQtnzLVOpJt-vu98su4VvVcHR_sUoh9KAQAA8AAAAEpQ.jpg","summary":"怀孕后吃几颗药，就能保证生男孩？所谓把女胎\u201c转\u201d成男胎的\u201c转胎丸\u201d，其实已经酿成了不少悲剧。","ukey_author":"1jodqq","date_created":"2018-06-08T14:26:01+08:00","resource_url":"http://apis.guokr.com/minisite/article/443006.json"},{"image":"","is_replyable":true,"channels":[{"url":"http://www.guokr.com/scientific/channel/visual/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"视觉","key":"visual","articles_count":785}],"channel_keys":["visual"],"preface":"","id":443005,"subject":{"url":"http://www.guokr.com/scientific/subject/astronomy/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"天文","key":"astronomy","articles_count":470},"is_editor_recommend":false,"copyright":"owned_by_translate","author":{"ukey":"lnndx5","is_title_authorized":false,"nickname":"Tisney","master_category":"normal","amended_reliability":"0","is_exists":true,"title":"天文本科僧，载人航天工程网小编一枚","url":"http://www.guokr.com/i/1309511993/","gender":null,"followers_count":6,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/lnndx5.json"},"image_description":"","is_show_summary":false,"minisite_key":"sciblog","image_info":{"url":"https://1-im.guokr.com/FrZnO8axitLRoNyEBsrZDZeJ6OoPwiWtG-DFFlBjCHlKAQAAuQAAAEpQ.jpg","width":330,"height":185},"subject_key":"astronomy","minisite":{"name":"科技评论","url":"http://www.guokr.com/site/sciblog/","introduction":"科学与思考是人类前进的两只脚，协同一致，我们才能真正进步","key":"sciblog","date_created":"2010-10-20T16:20:44+08:00","icon":"https://3-im.guokr.com/wUTGS9coWHVZatYqlPTFGo1ig-2JKzSeLNm1FHweYXRuAAAAWgAAAEpQ.jpg"},"tags":["火星","好奇号","外星生命"],"date_published":"2018-06-08T14:00:53+08:00","video_content":"","authors":[{"ukey":"lnndx5","is_title_authorized":false,"nickname":"Tisney","master_category":"normal","amended_reliability":"0","is_exists":true,"title":"天文本科僧，载人航天工程网小编一枚","url":"http://www.guokr.com/i/1309511993/","gender":null,"followers_count":6,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/lnndx5.json"}],"replies_count":6,"is_author_external":false,"recommends_count":0,"title_hide":"还没找到火星人，但距离火星生命可能又近了一步","date_modified":"2018-06-08T14:00:46.893080+08:00","url":"http://www.guokr.com/article/443005/","title":"还没找到火星人，但距离火星生命可能又近了一步","small_image":"https://1-im.guokr.com/FrZnO8axitLRoNyEBsrZDZeJ6OoPwiWtG-DFFlBjCHlKAQAAuQAAAEpQ.jpg","summary":"今天凌晨，NASA召开新闻发布会，公布了好奇号的两项发现：古老的碳留存于火星地表，大气中甲烷存在季节性变化。这两项发现表明，尽管条件恶劣，曾经宜居甚至可能拥有生命的证据，在火星上保存至今的可能性更大了。","ukey_author":"lnndx5","date_created":"2018-06-08T14:00:53+08:00","resource_url":"http://apis.guokr.com/minisite/article/443005.json"},{"image":"","is_replyable":true,"channels":[],"channel_keys":[],"preface":"","id":443004,"subject":{"url":"http://www.guokr.com/scientific/subject/biology/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"生物","key":"biology","articles_count":2635},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"ukey":"up508d","is_title_authorized":false,"nickname":"恐龙杂志","master_category":"normal","amended_reliability":"0","is_exists":true,"title":"","url":"http://www.guokr.com/i/1856209261/","gender":null,"followers_count":7,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/up508d.json"},"image_description":"","is_show_summary":false,"minisite_key":"sciblog","image_info":{"url":"https://2-im.guokr.com/qh1e0_FUxD5z3FWeGS7_aPZeqXJPLL1YjdL-s3I0WSxKAQAAiwAAAEpQ.jpg","width":330,"height":139},"subject_key":"biology","minisite":{"name":"科技评论","url":"http://www.guokr.com/site/sciblog/","introduction":"科学与思考是人类前进的两只脚，协同一致，我们才能真正进步","key":"sciblog","date_created":"2010-10-20T16:20:44+08:00","icon":"https://3-im.guokr.com/wUTGS9coWHVZatYqlPTFGo1ig-2JKzSeLNm1FHweYXRuAAAAWgAAAEpQ.jpg"},"tags":[],"date_published":"2018-06-08T12:29:39+08:00","video_content":"","authors":[{"ukey":"up508d","is_title_authorized":false,"nickname":"恐龙杂志","master_category":"normal","amended_reliability":"0","is_exists":true,"title":"","url":"http://www.guokr.com/i/1856209261/","gender":null,"followers_count":7,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/up508d.json"}],"replies_count":6,"is_author_external":false,"recommends_count":0,"title_hide":"\u201c无齿\u201d和\u201c不上进\u201d才是它们躲过大灭绝的秘密武器!?","date_modified":"2018-06-08T12:29:36.747972+08:00","url":"http://www.guokr.com/article/443004/","title":"\u201c无齿\u201d和\u201c不上进\u201d才是它们躲过大灭绝的秘密武器!?","small_image":"https://2-im.guokr.com/qh1e0_FUxD5z3FWeGS7_aPZeqXJPLL1YjdL-s3I0WSxKAQAAiwAAAEpQ.jpg","summary":"现今所有的鸟类都来自白垩纪末期很小的一个分支，它们幸存的秘密武器究竟是什么呢？","ukey_author":"up508d","date_created":"2018-06-08T12:29:39+08:00","resource_url":"http://apis.guokr.com/minisite/article/443004.json"},{"image":"","is_replyable":true,"channels":[{"url":"http://www.guokr.com/scientific/channel/viewpoint/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"观点","key":"viewpoint","articles_count":3804}],"channel_keys":["viewpoint"],"preface":"","id":443003,"subject":{"url":"http://www.guokr.com/scientific/subject/society/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"社会","key":"society","articles_count":359},"is_editor_recommend":false,"copyright":"owned_by_translate","author":{"url":"","introduction":"BBC News Magazine 特写作者","nickname":" Justin Parkinson","avatar":{"small":"","large":"","normal":""}},"image_description":"","is_show_summary":false,"minisite_key":null,"image_info":{"url":"https://2-im.guokr.com/FC6Z5rL6PyaaisM49vyLwf83JgJ4SmM8ssThit5LW8lKAQAA6wAAAFBO.png","width":330,"height":235},"subject_key":"society","minisite":null,"tags":["降落伞","极限运动","谋杀"],"date_published":"2018-06-08T12:08:21.803091+08:00","video_content":"","authors":[{"introduction":"BBC News Magazine 特写作者","url":"","nickname":" Justin Parkinson","avatar":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png","ukey_author":null}],"replies_count":5,"is_author_external":true,"recommends_count":0,"title_hide":"降落伞打不开怎么办，急，在线等","date_modified":"2018-06-08T12:06:16.629236+08:00","url":"http://www.guokr.com/article/443003/","title":"降落伞打不开怎么办，急，在线等","small_image":"https://2-im.guokr.com/FC6Z5rL6PyaaisM49vyLwf83JgJ4SmM8ssThit5LW8lKAQAA6wAAAFBO.png","summary":"看命。","ukey_author":null,"date_created":"2018-06-08T12:08:21.803091+08:00","resource_url":"http://apis.guokr.com/minisite/article/443003.json"},{"image":"","is_replyable":true,"channels":[],"channel_keys":[],"preface":"","id":443001,"subject":{"url":"http://www.guokr.com/scientific/subject/society/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"社会","key":"society","articles_count":359},"is_editor_recommend":false,"copyright":"owned_by_translate","author":{"url":"","introduction":"《卫报》特约撰稿人。","nickname":"Tim Adams","avatar":{"small":"https://1-im.guokr.com/HDxt4aEkvhamotPPwV1RKJVF7GgGtzZpWiUCitryj-yMAAAAjAAAAEpQ.jpg","large":"https://1-im.guokr.com/HDxt4aEkvhamotPPwV1RKJVF7GgGtzZpWiUCitryj-yMAAAAjAAAAEpQ.jpg","normal":"https://1-im.guokr.com/HDxt4aEkvhamotPPwV1RKJVF7GgGtzZpWiUCitryj-yMAAAAjAAAAEpQ.jpg"}},"image_description":"","is_show_summary":false,"minisite_key":null,"image_info":{"url":"https://2-im.guokr.com/uWu7zor-5VxrQfLkd6hJ3Z8gms55a2fbJy6unhkpQxZKAQAA6wAAAEpQ.jpg","width":330,"height":235},"subject_key":"society","minisite":null,"tags":["科幻","未来","技术"],"date_published":"2018-06-08T11:01:31+08:00","video_content":"","authors":[{"introduction":"《卫报》特约撰稿人。","url":"","nickname":"Tim Adams","avatar":"https://1-im.guokr.com/HDxt4aEkvhamotPPwV1RKJVF7GgGtzZpWiUCitryj-yMAAAAjAAAAEpQ.jpg","ukey_author":null}],"replies_count":4,"is_author_external":true,"recommends_count":0,"title_hide":"超人类的崛起：当人与机器结合","date_modified":"2018-06-08T11:08:44.646644+08:00","url":"http://www.guokr.com/article/443001/","title":"超人类的崛起：当人与机器结合","small_image":"https://2-im.guokr.com/uWu7zor-5VxrQfLkd6hJ3Z8gms55a2fbJy6unhkpQxZKAQAA6wAAAEpQ.jpg","summary":"身体骇客们正在改造自己的血肉之躯。","ukey_author":null,"date_created":"2018-06-08T11:01:31+08:00","resource_url":"http://apis.guokr.com/minisite/article/443001.json"},{"image":"","is_replyable":true,"channels":[{"url":"http://www.guokr.com/scientific/channel/frontier/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"前沿","key":"frontier","articles_count":4208}],"channel_keys":["frontier"],"preface":"","id":443002,"subject":{"url":"http://www.guokr.com/scientific/subject/internet/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"互联网","key":"internet","articles_count":251},"is_editor_recommend":false,"copyright":"owned_by_translate","author":{"url":"","introduction":"自由记者。","nickname":"Simon Parkin","avatar":{"small":"https://2-im.guokr.com/gNdZl2ru102U6qUZN_xIDh3SpHxed4jjvsV_ea2ea0zXAAAA1wAAAFBO.png","large":"https://2-im.guokr.com/gNdZl2ru102U6qUZN_xIDh3SpHxed4jjvsV_ea2ea0zXAAAA1wAAAFBO.png","normal":"https://2-im.guokr.com/gNdZl2ru102U6qUZN_xIDh3SpHxed4jjvsV_ea2ea0zXAAAA1wAAAFBO.png"}},"image_description":"","is_show_summary":false,"minisite_key":null,"image_info":{"url":"https://2-im.guokr.com/EfUD4zmEojXB7lfAWS-GDhSdt6l-oaBof2EmozPvqONKAQAA6wAAAEpQ.jpg","width":330,"height":235},"subject_key":"internet","minisite":null,"tags":["技术","网络","隐私"],"date_published":"2018-06-08T10:56:56+08:00","video_content":"","authors":[{"introduction":"自由记者。","url":"","nickname":"Simon Parkin","avatar":"https://2-im.guokr.com/gNdZl2ru102U6qUZN_xIDh3SpHxed4jjvsV_ea2ea0zXAAAA1wAAAFBO.png","ukey_author":null}],"replies_count":6,"is_author_external":true,"recommends_count":0,"title_hide":"如何从互联网上消失，以及你为什么需要从互联网上消失","date_modified":"2018-06-08T11:18:24.630700+08:00","url":"http://www.guokr.com/article/443002/","title":"如何从互联网上消失，以及你为什么需要从互联网上消失","small_image":"https://2-im.guokr.com/EfUD4zmEojXB7lfAWS-GDhSdt6l-oaBof2EmozPvqONKAQAA6wAAAEpQ.jpg","summary":"成为一个\u201c数字游魂\u201d，是非常困难的一件事。","ukey_author":null,"date_created":"2018-06-08T10:56:56+08:00","resource_url":"http://apis.guokr.com/minisite/article/443002.json"},{"image":"","is_replyable":true,"channels":[{"url":"http://www.guokr.com/scientific/channel/hot/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"热点","key":"hot","articles_count":1921}],"channel_keys":["hot"],"preface":"","id":442999,"subject":{"url":"http://www.guokr.com/scientific/subject/medicine/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"医学","key":"medicine","articles_count":1819},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"url":"","introduction":"首都医科大学附属北京佑安医院感染综合科 副主任医师","nickname":"李侗曾","avatar":{"small":"https://3-im.guokr.com/1iWidzjbRp3tzTfLjC3HL7uVFubrpPNFXogjid00JraQAQAAkAEAAEpQ.jpg","large":"https://3-im.guokr.com/1iWidzjbRp3tzTfLjC3HL7uVFubrpPNFXogjid00JraQAQAAkAEAAEpQ.jpg","normal":"https://3-im.guokr.com/1iWidzjbRp3tzTfLjC3HL7uVFubrpPNFXogjid00JraQAQAAkAEAAEpQ.jpg"}},"image_description":"","is_show_summary":false,"minisite_key":"health","image_info":{"url":"https://2-im.guokr.com/9nveDubBoCBsFqCWebIsB8cHR1Pud-_Z5jVTnuPATs1KAQAAyAAAAEpQ.jpg","width":330,"height":200},"subject_key":"medicine","minisite":{"name":"健康朝九晚五","url":"http://www.guokr.com/site/health/","introduction":"每天的朝九晚五，上几篇健康小文，有趣、靠谱，还很给力","key":"health","date_created":"2010-10-20T16:20:43+08:00","icon":"https://2-im.guokr.com/gDNaY8kFHIUnyDRLBlb1hWY3fwK9eAuV5icTVSuh7TNuAAAAWgAAAEpQ.jpg"},"tags":[],"date_published":"2018-06-06T21:47:08+08:00","video_content":"","authors":[{"introduction":"首都医科大学附属北京佑安医院感染综合科 副主任医师","url":"","nickname":"李侗曾","avatar":"https://3-im.guokr.com/1iWidzjbRp3tzTfLjC3HL7uVFubrpPNFXogjid00JraQAQAAkAEAAEpQ.jpg","ukey_author":null}],"replies_count":7,"is_author_external":true,"recommends_count":0,"title_hide":"三种HPV疫苗，有什么区别，到底应该怎么选？","date_modified":"2018-06-07T16:24:38.680643+08:00","url":"http://www.guokr.com/article/442999/","title":"三种HPV疫苗，有什么区别，到底应该怎么选？","small_image":"https://2-im.guokr.com/9nveDubBoCBsFqCWebIsB8cHR1Pud-_Z5jVTnuPATs1KAQAAyAAAAEpQ.jpg","summary":"二价、四价和九价HPV疫苗预防的疾病范围略有不同，它们都安全可靠，按照自己的年龄和经济状况选择即可。","ukey_author":null,"date_created":"2018-06-06T21:47:08+08:00","resource_url":"http://apis.guokr.com/minisite/article/442999.json"},{"image":"","is_replyable":true,"channels":[],"channel_keys":[],"preface":"","id":443000,"subject":{"url":"http://www.guokr.com/scientific/subject/society/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"社会","key":"society","articles_count":359},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"url":"","introduction":"食品工程博士 心理学博士 商学院教授","nickname":"崔凯","avatar":{"small":"https://3-im.guokr.com/aD-5u3s3s_rFfh9oZ0LvRSU2tA6ITe2eSWbncnhjMouiAAAApAAAAEpQ.jpg","large":"https://3-im.guokr.com/aD-5u3s3s_rFfh9oZ0LvRSU2tA6ITe2eSWbncnhjMouiAAAApAAAAEpQ.jpg","normal":"https://3-im.guokr.com/aD-5u3s3s_rFfh9oZ0LvRSU2tA6ITe2eSWbncnhjMouiAAAApAAAAEpQ.jpg"}},"image_description":"","is_show_summary":false,"minisite_key":null,"image_info":{"url":"https://3-im.guokr.com/uNuse-S5JHSZkahoWm7coiib-twvRko2QN2EN34WuiBKAQAA3AAAAEpQ.jpg","width":330,"height":220},"subject_key":"society","minisite":null,"tags":[],"date_published":"2018-06-06T16:41:01+08:00","video_content":"<iframe frameborder=\"0\" width=\"640\" height=\"498\" src=\"https://v.qq.com/iframe/player.html?vid=w067813pbwp&tiny=0&auto=0\" allowfullscreen><\/iframe>","authors":[{"introduction":"食品工程博士 心理学博士 商学院教授","url":"","nickname":"崔凯","avatar":"https://3-im.guokr.com/aD-5u3s3s_rFfh9oZ0LvRSU2tA6ITe2eSWbncnhjMouiAAAApAAAAEpQ.jpg","ukey_author":null}],"replies_count":26,"is_author_external":true,"recommends_count":0,"title_hide":"关于转基因，你的担忧科学吗？","date_modified":"2018-06-06T16:46:04.112405+08:00","url":"http://www.guokr.com/article/443000/","title":"关于转基因，你的担忧科学吗？","small_image":"https://3-im.guokr.com/uNuse-S5JHSZkahoWm7coiib-twvRko2QN2EN34WuiBKAQAA3AAAAEpQ.jpg","summary":"不妨先听听最权威的科学机构怎么说？","ukey_author":null,"date_created":"2018-06-06T16:41:01+08:00","resource_url":"http://apis.guokr.com/minisite/article/443000.json"},{"image":"","is_replyable":true,"channels":[{"url":"http://www.guokr.com/scientific/channel/viewpoint/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"观点","key":"viewpoint","articles_count":3804}],"channel_keys":["viewpoint"],"preface":"","id":442995,"subject":{"url":"http://www.guokr.com/scientific/subject/biology/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"生物","key":"biology","articles_count":2635},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"ukey":"2dxj2y","is_title_authorized":false,"nickname":"vicko238","master_category":"normal","amended_reliability":"0","is_exists":true,"title":"","url":"http://www.guokr.com/i/0144331738/","gender":null,"followers_count":0,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/2dxj2y.json"},"image_description":"","is_show_summary":false,"minisite_key":"sciblog","image_info":{"url":"https://3-im.guokr.com/DkQqbsE2HEoGeU3EusujidqlScGz_ABiIgd9arB-WiFKAQAA6wAAAEpQ.jpg","width":330,"height":235},"subject_key":"biology","minisite":{"name":"科技评论","url":"http://www.guokr.com/site/sciblog/","introduction":"科学与思考是人类前进的两只脚，协同一致，我们才能真正进步","key":"sciblog","date_created":"2010-10-20T16:20:44+08:00","icon":"https://3-im.guokr.com/wUTGS9coWHVZatYqlPTFGo1ig-2JKzSeLNm1FHweYXRuAAAAWgAAAEpQ.jpg"},"tags":["铲形门齿","考古","白令陆桥","最后盛冰期"],"date_published":"2018-06-06T16:32:46+08:00","video_content":"","authors":[{"ukey":"2dxj2y","is_title_authorized":false,"nickname":"vicko238","master_category":"normal","amended_reliability":"0","is_exists":true,"title":"","url":"http://www.guokr.com/i/0144331738/","gender":null,"followers_count":0,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/2dxj2y.json"}],"replies_count":14,"is_author_external":false,"recommends_count":0,"title_hide":"门牙两边有个楞，你告诉我是东亚人变异？它有个毛用？","date_modified":"2018-06-07T18:17:12.416990+08:00","url":"http://www.guokr.com/article/442995/","title":"门牙两边有个楞，你告诉我是东亚人变异？它有个毛用？","small_image":"https://3-im.guokr.com/DkQqbsE2HEoGeU3EusujidqlScGz_ABiIgd9arB-WiFKAQAA6wAAAEpQ.jpg","summary":"排汗？保湿？补充婴儿维生素D？","ukey_author":"2dxj2y","date_created":"2018-06-06T16:32:46+08:00","resource_url":"http://apis.guokr.com/minisite/article/442995.json"},{"image":"","is_replyable":true,"channels":[],"channel_keys":[],"preface":"","id":442998,"subject":{"url":"http://www.guokr.com/scientific/subject/others/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"其他","key":"others","articles_count":291},"is_editor_recommend":false,"copyright":"owned_by_translate","author":{"url":"","introduction":"Live Science资深作者","nickname":"Mindy Weisberger ","avatar":{"small":"https://media.licdn.com/dms/image/C4D03AQF7xRtHc4noOQ/profile-displayphoto-shrink_800_800/0?e=1533772800&v=beta&t=MDD5Km5I87EaBD4nHZLfsCMrISUU1LH7lN6WlvCA7MI","large":"https://media.licdn.com/dms/image/C4D03AQF7xRtHc4noOQ/profile-displayphoto-shrink_800_800/0?e=1533772800&v=beta&t=MDD5Km5I87EaBD4nHZLfsCMrISUU1LH7lN6WlvCA7MI","normal":"https://media.licdn.com/dms/image/C4D03AQF7xRtHc4noOQ/profile-displayphoto-shrink_800_800/0?e=1533772800&v=beta&t=MDD5Km5I87EaBD4nHZLfsCMrISUU1LH7lN6WlvCA7MI"}},"image_description":"","is_show_summary":false,"minisite_key":null,"image_info":{"url":"https://2-im.guokr.com/Y3XGb0iiy4Om61rSah6LOXrO0PXklfOK2mX-Iir-VTFKAQAA6wAAAEpQ.jpg","width":330,"height":235},"subject_key":"others","minisite":null,"tags":["语言","演讲","停顿","名词","动词"],"date_published":"2018-06-06T14:20:32.834522+08:00","video_content":"","authors":[{"introduction":"Live Science资深作者","url":"","nickname":"Mindy Weisberger ","avatar":"https://media.licdn.com/dms/image/C4D03AQF7xRtHc4noOQ/profile-displayphoto-shrink_800_800/0?e=1533772800&v=beta&t=MDD5Km5I87EaBD4nHZLfsCMrISUU1LH7lN6WlvCA7MI","ukey_author":null}],"replies_count":5,"is_author_external":true,"recommends_count":2,"title_hide":"虽然人们说着不同的语言，但卡壳的地方都一样一样的","date_modified":"2018-06-05T18:20:31.303349+08:00","url":"http://www.guokr.com/article/442998/","title":"虽然人们说着不同的语言，但卡壳的地方都一样一样的","small_image":"https://2-im.guokr.com/Y3XGb0iiy4Om61rSah6LOXrO0PXklfOK2mX-Iir-VTFKAQAA6wAAAEpQ.jpg","summary":"这个地方就是：名词之前\u2026\u2026","ukey_author":null,"date_created":"2018-06-06T14:20:32.834522+08:00","resource_url":"http://apis.guokr.com/minisite/article/442998.json"},{"image":"","is_replyable":true,"channels":[],"channel_keys":[],"preface":"","id":442997,"subject":{"url":"http://www.guokr.com/scientific/subject/environment/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"环境","key":"environment","articles_count":371},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"ukey":"ew2xfi","is_title_authorized":false,"nickname":"半个书生","master_category":"normal","amended_reliability":"0","is_exists":true,"title":"","url":"http://www.guokr.com/i/0900410814/","gender":null,"followers_count":4,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/ew2xfi.json"},"image_description":"","is_show_summary":false,"minisite_key":"natural","image_info":{"url":"https://1-im.guokr.com/1xnCnvbMM30VaBFClNsHOmYeDaNE3RIka-Vg3FUeCXJKAQAA0QAAAEpQ.jpg","width":330,"height":209},"subject_key":"environment","minisite":{"name":"自然控","url":"http://www.guokr.com/site/natural/","introduction":"让我们一起来分享花草树木、鸟兽虫鱼，还有灿烂星河带来的喜悦，以博物精神之名","key":"natural","date_created":"2010-10-20T16:20:44+08:00","icon":"https://1-im.guokr.com/szAPyph4MN164v0f72VQmTKrP5u6_ADO3iTBqkDYLetuAAAAWgAAAEpQ.jpg"},"tags":[],"date_published":"2018-06-05T17:07:37+08:00","video_content":"","authors":[{"ukey":"ew2xfi","is_title_authorized":false,"nickname":"半个书生","master_category":"normal","amended_reliability":"0","is_exists":true,"title":"","url":"http://www.guokr.com/i/0900410814/","gender":null,"followers_count":4,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/ew2xfi.json"}],"replies_count":11,"is_author_external":false,"recommends_count":1,"title_hide":"保护区：哪有什么岁月静好，只有你不知道的现状","date_modified":"2018-06-05T17:12:56.237099+08:00","url":"http://www.guokr.com/article/442997/","title":"保护区：哪有什么岁月静好，只有你不知道的现状","small_image":"https://1-im.guokr.com/1xnCnvbMM30VaBFClNsHOmYeDaNE3RIka-Vg3FUeCXJKAQAA0QAAAEpQ.jpg","summary":"我们不能等到和其他物种都告别之后，才懂得孤独的真正意义。","ukey_author":"ew2xfi","date_created":"2018-06-05T17:07:37+08:00","resource_url":"http://apis.guokr.com/minisite/article/442997.json"},{"image":"","is_replyable":true,"channels":[{"url":"http://www.guokr.com/scientific/channel/hot/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"热点","key":"hot","articles_count":1921}],"channel_keys":["hot"],"preface":"","id":442996,"subject":{"url":"http://www.guokr.com/scientific/subject/society/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"社会","key":"society","articles_count":359},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"ukey":"l0q7lu","is_title_authorized":true,"nickname":"李子李子短信","master_category":"personal","amended_reliability":"0","is_exists":true,"title":"社科硕士，博物馆爱好者，果壳作者","url":"http://www.guokr.com/i/1271012610/","gender":"male","followers_count":3997,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/l0q7lu.json"},"image_description":"","is_show_summary":true,"minisite_key":null,"image_info":{"url":"https://2-im.guokr.com/1E9vgXXx2D5oYiHWxtRwAMjnOVxg0ha0-kD6uYy_74JKAQAA6wAAAEpQ.jpg","width":330,"height":235},"subject_key":"society","minisite":null,"tags":["苹果","技术","WWDC","软件","升级","IOS"],"date_published":"2018-06-05T16:57:37+08:00","video_content":"","authors":[{"ukey":"l0q7lu","is_title_authorized":true,"nickname":"李子李子短信","master_category":"personal","amended_reliability":"0","is_exists":true,"title":"社科硕士，博物馆爱好者，果壳作者","url":"http://www.guokr.com/i/1271012610/","gender":"male","followers_count":3997,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/l0q7lu.json"}],"replies_count":8,"is_author_external":false,"recommends_count":0,"title_hide":"听说昨晚苹果只发布了彩虹表带？同学，软件升级了解一下？","date_modified":"2018-06-06T13:26:02.767025+08:00","url":"http://www.guokr.com/article/442996/","title":"听说昨晚苹果只发布了彩虹表带？同学，软件升级了解一下？","small_image":"https://2-im.guokr.com/1E9vgXXx2D5oYiHWxtRwAMjnOVxg0ha0-kD6uYy_74JKAQAA6wAAAEpQ.jpg","summary":"我们不贩卖焦虑，我们是焦虑的搬运工，左手搬到右手，从手机搬到手表\u2026\u2026","ukey_author":"l0q7lu","date_created":"2018-06-05T16:57:37+08:00","resource_url":"http://apis.guokr.com/minisite/article/442996.json"},{"image":"","is_replyable":true,"channels":[],"channel_keys":[],"preface":"","id":442992,"subject":{"url":"http://www.guokr.com/scientific/subject/medicine/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"医学","key":"medicine","articles_count":1819},"is_editor_recommend":false,"copyright":"owned_by_translate","author":{"url":"","introduction":"加州大学洛杉矶分校，麻醉与术期医学临床副教授","nickname":"Karen Sibert","avatar":{"small":"https://cdn.theconversation.com/avatars/453390/width238/Karen-Sibert.jpg","large":"https://cdn.theconversation.com/avatars/453390/width238/Karen-Sibert.jpg","normal":"https://cdn.theconversation.com/avatars/453390/width238/Karen-Sibert.jpg"}},"image_description":"","is_show_summary":false,"minisite_key":"health","image_info":{"url":"https://3-im.guokr.com/DJRS-0GhDZ7Is6nlLF3UzWqjqXdgXOHuV-0u5vh8-49KAQAA6wAAAEpQ.jpg","width":330,"height":235},"subject_key":"medicine","minisite":{"name":"健康朝九晚五","url":"http://www.guokr.com/site/health/","introduction":"每天的朝九晚五，上几篇健康小文，有趣、靠谱，还很给力","key":"health","date_created":"2010-10-20T16:20:43+08:00","icon":"https://2-im.guokr.com/gDNaY8kFHIUnyDRLBlb1hWY3fwK9eAuV5icTVSuh7TNuAAAAWgAAAEpQ.jpg"},"tags":["疼痛","痛觉","麻醉","止痛药","镇痛药","种族"],"date_published":"2018-06-05T13:36:35.703241+08:00","video_content":"","authors":[{"introduction":"加州大学洛杉矶分校，麻醉与术期医学临床副教授","url":"","nickname":"Karen Sibert","avatar":"https://cdn.theconversation.com/avatars/453390/width238/Karen-Sibert.jpg","ukey_author":null}],"replies_count":7,"is_author_external":true,"recommends_count":0,"title_hide":"有种痛叫做别人觉得你不痛","date_modified":"2018-06-04T17:23:19.375812+08:00","url":"http://www.guokr.com/article/442992/","title":"有种痛叫做别人觉得你不痛","small_image":"https://3-im.guokr.com/DJRS-0GhDZ7Is6nlLF3UzWqjqXdgXOHuV-0u5vh8-49KAQAA6wAAAEpQ.jpg","summary":"生理条件不一样，疼法和待遇可能也不一样\u2026\u2026","ukey_author":null,"date_created":"2018-06-05T13:36:35.703241+08:00","resource_url":"http://apis.guokr.com/minisite/article/442992.json"},{"image":"","is_replyable":true,"channels":[{"url":"http://www.guokr.com/scientific/channel/frontier/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"前沿","key":"frontier","articles_count":4208}],"channel_keys":["frontier"],"preface":"","id":442994,"subject":{"url":"http://www.guokr.com/scientific/subject/society/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"社会","key":"society","articles_count":359},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"url":"","introduction":"","nickname":"李子李子短信","avatar":{"small":"","large":"","normal":""}},"image_description":"","is_show_summary":false,"minisite_key":null,"image_info":{"url":"https://2-im.guokr.com/fmsNyqU_vMGiiPK3ndx-qKqib_y5tZqS-QKPpL9RuUFKAQAA6wAAAFBO.png","width":330,"height":235},"subject_key":"society","minisite":null,"tags":["学术不端","数据造假","撤稿","心理学"],"date_published":"2018-06-05T13:14:26+08:00","video_content":"","authors":[{"introduction":"","url":"","nickname":"李子李子短信","avatar":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png","ukey_author":null}],"replies_count":14,"is_author_external":true,"recommends_count":1,"title_hide":"两个\u201c数据流氓\u201d如何掀翻学术大佬","date_modified":"2018-06-05T15:14:28.900475+08:00","url":"http://www.guokr.com/article/442994/","title":"两个\u201c数据流氓\u201d如何掀翻学术大佬","small_image":"https://2-im.guokr.com/fmsNyqU_vMGiiPK3ndx-qKqib_y5tZqS-QKPpL9RuUFKAQAA6wAAAFBO.png","summary":"他们把数据简单跑了一遍，发现令人哈哈哈的bug竟然满天飞？！","ukey_author":null,"date_created":"2018-06-05T13:14:26+08:00","resource_url":"http://apis.guokr.com/minisite/article/442994.json"},{"image":"","is_replyable":true,"channels":[{"url":"http://www.guokr.com/scientific/channel/viewpoint/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"观点","key":"viewpoint","articles_count":3804}],"channel_keys":["viewpoint"],"preface":"","id":442993,"subject":{"url":"http://www.guokr.com/scientific/subject/medicine/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"医学","key":"medicine","articles_count":1819},"is_editor_recommend":false,"copyright":"owned_by_cooperation","author":{"url":"","introduction":"麻州大学医学院助理教授，美国执业视光医生。","nickname":"丁娟","avatar":{"small":"http://1-im.guokr.com/V09B9eFMErb_rLUIm3MtvjNxXdI8CHI_pvy5iTj21bebAgAAmwIAAEpQ.jpg","large":"http://1-im.guokr.com/V09B9eFMErb_rLUIm3MtvjNxXdI8CHI_pvy5iTj21bebAgAAmwIAAEpQ.jpg","normal":"http://1-im.guokr.com/V09B9eFMErb_rLUIm3MtvjNxXdI8CHI_pvy5iTj21bebAgAAmwIAAEpQ.jpg"}},"image_description":"","is_show_summary":false,"minisite_key":"health","image_info":{"url":"https://1-im.guokr.com/YJ98vfWsDX3EtM-sxz1smgbG1gP9wSp6x3NgwaW3zPNKAQAA8AAAAEpQ.jpg","width":330,"height":240},"subject_key":"medicine","minisite":{"name":"健康朝九晚五","url":"http://www.guokr.com/site/health/","introduction":"每天的朝九晚五，上几篇健康小文，有趣、靠谱，还很给力","key":"health","date_created":"2010-10-20T16:20:43+08:00","icon":"https://2-im.guokr.com/gDNaY8kFHIUnyDRLBlb1hWY3fwK9eAuV5icTVSuh7TNuAAAAWgAAAEpQ.jpg"},"tags":[],"date_published":"2018-06-05T08:49:40+08:00","video_content":"","authors":[{"introduction":"麻州大学医学院助理教授，美国执业视光医生。","url":"","nickname":"丁娟","avatar":"http://1-im.guokr.com/V09B9eFMErb_rLUIm3MtvjNxXdI8CHI_pvy5iTj21bebAgAAmwIAAEpQ.jpg","ukey_author":null}],"replies_count":13,"is_author_external":true,"recommends_count":0,"title_hide":"关于近视的所有疑问，读这一篇就够了","date_modified":"2018-06-05T08:49:37.437741+08:00","url":"http://www.guokr.com/article/442993/","title":"关于近视的所有疑问，读这一篇就够了","small_image":"https://1-im.guokr.com/YJ98vfWsDX3EtM-sxz1smgbG1gP9wSp6x3NgwaW3zPNKAQAA8AAAAEpQ.jpg","summary":"怎么预防？如何治疗？哪些方法靠谱？一分钱不花也可以预防近视？发现小孩近视怎么办？在近视初期，不戴近视眼镜，或者戴度数浅的眼镜，有帮助吗？所谓\u201c逆转近视，缩短眼轴\u201d，是不是在骗人？","ukey_author":null,"date_created":"2018-06-05T08:49:40+08:00","resource_url":"http://apis.guokr.com/minisite/article/442993.json"},{"image":"","is_replyable":true,"channels":[{"url":"http://www.guokr.com/scientific/channel/viewpoint/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"观点","key":"viewpoint","articles_count":3804}],"channel_keys":["viewpoint"],"preface":"","id":442990,"subject":{"url":"http://www.guokr.com/scientific/subject/medicine/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"医学","key":"medicine","articles_count":1819},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"ukey":"07z00a","is_title_authorized":true,"nickname":"MarvinP","master_category":"personal","amended_reliability":"0","is_exists":true,"title":"遗传学博士生，科学松鼠会成员，科普专栏作者","url":"http://www.guokr.com/i/0013390282/","gender":null,"followers_count":3138,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/07z00a.json"},"image_description":"","is_show_summary":false,"minisite_key":"health","image_info":{"url":"https://3-im.guokr.com/LDuegVH3d_2EiC5ibszbOFrLYuVXHLBIcNrFp5y30JBKAQAA5gAAAEpQ.jpg","width":330,"height":230},"subject_key":"medicine","minisite":{"name":"健康朝九晚五","url":"http://www.guokr.com/site/health/","introduction":"每天的朝九晚五，上几篇健康小文，有趣、靠谱，还很给力","key":"health","date_created":"2010-10-20T16:20:43+08:00","icon":"https://2-im.guokr.com/gDNaY8kFHIUnyDRLBlb1hWY3fwK9eAuV5icTVSuh7TNuAAAAWgAAAEpQ.jpg"},"tags":[],"date_published":"2018-06-02T18:38:19+08:00","video_content":"","authors":[{"ukey":"07z00a","is_title_authorized":true,"nickname":"MarvinP","master_category":"personal","amended_reliability":"0","is_exists":true,"title":"遗传学博士生，科学松鼠会成员，科普专栏作者","url":"http://www.guokr.com/i/0013390282/","gender":null,"followers_count":3138,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/07z00a.json"}],"replies_count":9,"is_author_external":false,"recommends_count":0,"title_hide":"得了癌症以后，生活会发生巨变，但你依然是你","date_modified":"2018-06-02T20:11:16.296653+08:00","url":"http://www.guokr.com/article/442990/","title":"得了癌症以后，生活会发生巨变，但你依然是你","small_image":"https://3-im.guokr.com/LDuegVH3d_2EiC5ibszbOFrLYuVXHLBIcNrFp5y30JBKAQAA5gAAAEpQ.jpg","summary":"电影《湮灭》可以看成是关于癌症和癌症幸存者的贴切譬喻。","ukey_author":"07z00a","date_created":"2018-06-02T18:38:19+08:00","resource_url":"http://apis.guokr.com/minisite/article/442990.json"},{"image":"","is_replyable":true,"channels":[],"channel_keys":[],"preface":"","id":442989,"subject":{"url":"http://www.guokr.com/scientific/subject/electronics/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"电子","key":"electronics","articles_count":780},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"url":"","introduction":"脑机接口界的小动物爱好者，可穿戴传感设备界的灵魂画手，人工智障技术研究员。","nickname":"莘莘","avatar":{"small":"https://1-im.guokr.com/ePxGTDWqC9fu8OfQ74-bif19D7TZg78Qu45s0Jep_-T8AAAA_gAAAEpQ.jpg","large":"https://1-im.guokr.com/ePxGTDWqC9fu8OfQ74-bif19D7TZg78Qu45s0Jep_-T8AAAA_gAAAEpQ.jpg","normal":"https://1-im.guokr.com/ePxGTDWqC9fu8OfQ74-bif19D7TZg78Qu45s0Jep_-T8AAAA_gAAAEpQ.jpg"}},"image_description":"","is_show_summary":false,"minisite_key":"digest","image_info":{"url":"https://3-im.guokr.com/yfLagAzgHeXKu1STC8XfwlCN8W_hFEot-cLXZrQBuItKAQAABwEAAEpQ.jpg","width":330,"height":263},"subject_key":"electronics","minisite":{"name":"环球科技观光团","url":"http://www.guokr.com/site/digest/","introduction":"这里报道最新、最酷的资讯，带你快速了解世界科研一线成就","key":"digest","date_created":"2010-10-20T16:20:44+08:00","icon":"https://3-im.guokr.com/TPdfxoaxAGOhbhf2mHZsjvnXSCsT8dtGv0ItDjLiAmBuAAAAWgAAAEpQ.jpg"},"tags":[],"date_published":"2018-06-01T14:24:49+08:00","video_content":"","authors":[{"introduction":"脑机接口界的小动物爱好者，可穿戴传感设备界的灵魂画手，人工智障技术研究员。","url":"","nickname":"莘莘","avatar":"https://1-im.guokr.com/ePxGTDWqC9fu8OfQ74-bif19D7TZg78Qu45s0Jep_-T8AAAA_gAAAEpQ.jpg","ukey_author":null}],"replies_count":6,"is_author_external":true,"recommends_count":0,"title_hide":"这个儿童节，我可以拥有哆啦A梦了吗？","date_modified":"2018-06-01T14:26:43.432511+08:00","url":"http://www.guokr.com/article/442989/","title":"这个儿童节，我可以拥有哆啦A梦了吗？","small_image":"https://3-im.guokr.com/yfLagAzgHeXKu1STC8XfwlCN8W_hFEot-cLXZrQBuItKAQAABwEAAEpQ.jpg","summary":"想得美！","ukey_author":null,"date_created":"2018-06-01T14:24:49+08:00","resource_url":"http://apis.guokr.com/minisite/article/442989.json"},{"image":"","is_replyable":true,"channels":[],"channel_keys":[],"preface":"","id":442988,"subject":{"url":"http://www.guokr.com/scientific/subject/others/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"其他","key":"others","articles_count":291},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"url":"","introduction":"中国科普研究所","nickname":"王大鹏","avatar":{"small":"https://2-im.guokr.com/ddVa-TCvjjcfMAyF74vXi_SIm0Do-bqZNl5iEh9ZZMr_AAAAAAEAAEpQ.jpg","large":"https://2-im.guokr.com/ddVa-TCvjjcfMAyF74vXi_SIm0Do-bqZNl5iEh9ZZMr_AAAAAAEAAEpQ.jpg","normal":"https://2-im.guokr.com/ddVa-TCvjjcfMAyF74vXi_SIm0Do-bqZNl5iEh9ZZMr_AAAAAAEAAEpQ.jpg"}},"image_description":"","is_show_summary":false,"minisite_key":null,"image_info":{"url":"https://1-im.guokr.com/BKRFIIBHToe-2gqv9dV1UQ_vJ6lx0Dr9ksYbPM-OqO9KAQAAoAAAAEpQ.jpg","width":330,"height":160},"subject_key":"others","minisite":null,"tags":[],"date_published":"2018-05-31T16:12:37+08:00","video_content":"","authors":[{"introduction":"中国科普研究所","url":"","nickname":"王大鹏","avatar":"https://2-im.guokr.com/ddVa-TCvjjcfMAyF74vXi_SIm0Do-bqZNl5iEh9ZZMr_AAAAAAEAAEpQ.jpg","ukey_author":null}],"replies_count":12,"is_author_external":true,"recommends_count":0,"title_hide":"道理讲了好多遍，为啥他就是听不进去？ ","date_modified":"2018-05-31T15:57:33.369940+08:00","url":"http://www.guokr.com/article/442988/","title":"道理讲了好多遍，为啥他就是听不进去？ ","small_image":"https://1-im.guokr.com/BKRFIIBHToe-2gqv9dV1UQ_vJ6lx0Dr9ksYbPM-OqO9KAQAAoAAAAEpQ.jpg","summary":"从\u201c庖丁解台词\u201d谈谈科学传播","ukey_author":null,"date_created":"2018-05-31T16:12:37+08:00","resource_url":"http://apis.guokr.com/minisite/article/442988.json"},{"image":"","is_replyable":true,"channels":[{"url":"http://www.guokr.com/scientific/channel/frontier/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"前沿","key":"frontier","articles_count":4208}],"channel_keys":["frontier"],"preface":"","id":442987,"subject":{"url":"http://www.guokr.com/scientific/subject/earth/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"地学","key":"earth","articles_count":185},"is_editor_recommend":false,"copyright":"owned_by_translate","author":{"url":"https://www.sciencenews.org/author/laurel-hamers","introduction":"Science News 杂志记者","nickname":"Laurel Hamers","avatar":{"small":"https://3-im.guokr.com/Gm9ehHJiIAHnAjvh0ywvAU_JdzDSq-NGuYoJRJfyQ8uSAwAAiAMAAFBO.png","large":"https://3-im.guokr.com/Gm9ehHJiIAHnAjvh0ywvAU_JdzDSq-NGuYoJRJfyQ8uSAwAAiAMAAFBO.png","normal":"https://3-im.guokr.com/Gm9ehHJiIAHnAjvh0ywvAU_JdzDSq-NGuYoJRJfyQ8uSAwAAiAMAAFBO.png"}},"image_description":"","is_show_summary":false,"minisite_key":null,"image_info":{"url":"https://1-im.guokr.com/RsvsBgrZRagpKk010QhelyZMRW431KrnqaKVDEPGHIRKAQAAsQAAAEpQ.jpg","width":330,"height":177},"subject_key":"earth","minisite":null,"tags":["小行星","全球变暖","天文","恐龙"],"date_published":"2018-05-31T13:59:16+08:00","video_content":"","authors":[{"introduction":"Science News 杂志记者","url":"https://www.sciencenews.org/author/laurel-hamers","nickname":"Laurel Hamers","avatar":"https://3-im.guokr.com/Gm9ehHJiIAHnAjvh0ywvAU_JdzDSq-NGuYoJRJfyQ8uSAwAAiAMAAFBO.png","ukey_author":null}],"replies_count":7,"is_author_external":true,"recommends_count":0,"title_hide":"它不只灭掉了恐龙，还导致了随后10万年的全球变暖","date_modified":"2018-05-31T13:59:10.703975+08:00","url":"http://www.guokr.com/article/442987/","title":"它不只灭掉了恐龙，还导致了随后10万年的全球变暖","small_image":"https://1-im.guokr.com/RsvsBgrZRagpKk010QhelyZMRW431KrnqaKVDEPGHIRKAQAAsQAAAEpQ.jpg","summary":"6600万年前撞击地球的那颗小行星，在随后的大约10万年里，让地球上的海洋温度上升了大约5℃。","ukey_author":null,"date_created":"2018-05-31T13:59:16+08:00","resource_url":"http://apis.guokr.com/minisite/article/442987.json"},{"image":"","is_replyable":true,"channels":[{"url":"http://www.guokr.com/scientific/channel/hot/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"热点","key":"hot","articles_count":1921}],"channel_keys":["hot"],"preface":"","id":442986,"subject":{"url":"http://www.guokr.com/scientific/subject/others/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"其他","key":"others","articles_count":291},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"ukey":"2dxj2y","is_title_authorized":false,"nickname":"vicko238","master_category":"normal","amended_reliability":"0","is_exists":true,"title":"","url":"http://www.guokr.com/i/0144331738/","gender":null,"followers_count":0,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/2dxj2y.json"},"image_description":"","is_show_summary":false,"minisite_key":null,"image_info":{"url":"https://1-im.guokr.com/xxz-r6j3SZuYnm2Ibg7Pk1wSPWf4HxdNHuyO-lOkhQVKAQAA6wAAAEpQ.jpg","width":330,"height":235},"subject_key":"others","minisite":null,"tags":["考古","火山","历史"],"date_published":"2018-05-31T11:04:37+08:00","video_content":"","authors":[{"ukey":"2dxj2y","is_title_authorized":false,"nickname":"vicko238","master_category":"normal","amended_reliability":"0","is_exists":true,"title":"","url":"http://www.guokr.com/i/0144331738/","gender":null,"followers_count":0,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/2dxj2y.json"}],"replies_count":22,"is_author_external":false,"recommends_count":0,"title_hide":"庞贝古城新发现，一米巨石砸中逃难男子","date_modified":"2018-05-31T11:09:13.675472+08:00","url":"http://www.guokr.com/article/442986/","title":"庞贝古城新发现，一米巨石砸中逃难男子","small_image":"https://1-im.guokr.com/xxz-r6j3SZuYnm2Ibg7Pk1wSPWf4HxdNHuyO-lOkhQVKAQAA6wAAAEpQ.jpg","summary":"现在他的头还不知所踪。","ukey_author":"2dxj2y","date_created":"2018-05-31T11:04:37+08:00","resource_url":"http://apis.guokr.com/minisite/article/442986.json"}]
     * offset : 0
     * total : 13396
    </iframe> */
    var now: String? = null
    var isOk = false
    var limit = 0
    var offset = 0
    var total = 0
    var result: RealmList<Result>? = null

    @PrimaryKey
    var id: String? = "first_page"
}

open class Result : Serializable, RealmObject() {
    /**
     * image :
     * is_replyable : true
     * channels : []
     * channel_keys : []
     * preface :
     * id : 443006
     * subject : {"url":"http://www.guokr.com/scientific/subject/medicine/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"医学","key":"medicine","articles_count":1819}
     * is_editor_recommend : false
     * copyright : owned_by_guokr
     * author : {"ukey":"1jodqq","is_title_authorized":true,"nickname":"飞刀断雨","master_category":"personal","amended_reliability":"0","is_exists":true,"title":"临床医学专业，玄牝之门小组管理员","url":"http://www.guokr.com/i/0093516434/","gender":"female","followers_count":221,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/1jodqq.json"}
     * image_description :
     * is_show_summary : false
     * minisite_key : health
     * image_info : {"url":"https://2-im.guokr.com/bfjAMXmfv0qwjQtnzLVOpJt-vu98su4VvVcHR_sUoh9KAQAA8AAAAEpQ.jpg","width":330,"height":240}
     * subject_key : medicine
     * minisite : {"name":"健康朝九晚五","url":"http://www.guokr.com/site/health/","introduction":"每天的朝九晚五，上几篇健康小文，有趣、靠谱，还很给力","key":"health","date_created":"2010-10-20T16:20:43+08:00","icon":"https://2-im.guokr.com/gDNaY8kFHIUnyDRLBlb1hWY3fwK9eAuV5icTVSuh7TNuAAAAWgAAAEpQ.jpg"}
     * tags : ["转胎药","转胎丸"]
     * date_published : 2018-06-08T14:26:01+08:00
     * video_content :
     * authors : [{"ukey":"1jodqq","is_title_authorized":true,"nickname":"飞刀断雨","master_category":"personal","amended_reliability":"0","is_exists":true,"title":"临床医学专业，玄牝之门小组管理员","url":"http://www.guokr.com/i/0093516434/","gender":"female","followers_count":221,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/1jodqq.json"}]
     * replies_count : 13
     * is_author_external : false
     * recommends_count : 0
     * title_hide : “转胎丸”到底是个什么玩意？
     * date_modified : 2018-06-08T14:59:40.953735+08:00
     * url : http://www.guokr.com/article/443006/
     * title : “转胎丸”到底是个什么玩意？
     * small_image : https://2-im.guokr.com/bfjAMXmfv0qwjQtnzLVOpJt-vu98su4VvVcHR_sUoh9KAQAA8AAAAEpQ.jpg
     * summary : 怀孕后吃几颗药，就能保证生男孩？所谓把女胎“转”成男胎的“转胎丸”，其实已经酿成了不少悲剧。
     * ukey_author : 1jodqq
     * date_created : 2018-06-08T14:26:01+08:00
     * resource_url : http://apis.guokr.com/minisite/article/443006.json
     */
    var image: String? = null
    var isIs_replyable = false
        private set
    var preface: String? = null
    var id = 0
    var subject: Subject? = null
    var isIs_editor_recommend = false
        private set
    var copyright: String? = null
    var author: GuokrAuthor? = null
    var image_description: String? = null
    var isIs_show_summary = false
        private set
    var minisite_key: String? = null
    var image_info: ImageInfo? = null
    var subject_key: String? = null
    var minisite: Minisite? = null
    var date_published: String? = null
    var video_content: String? = null
    var replies_count = 0
    var isIs_author_external = false
        private set
    var recommends_count = 0
    var title_hide: String? = null
    var date_modified: String? = null
    var url: String? = null
    var title: String? = null
    var small_image: String? = null
    var summary: String? = null
    var ukey_author: String? = null
    var date_created: String? = null
    var resource_url: String? = null
    var tags: RealmList<String>? = null
    var authors: RealmList<Authors>? = null

    fun setIs_replyable(is_replyable: Boolean) {
        isIs_replyable = is_replyable
    }

    fun setIs_editor_recommend(is_editor_recommend: Boolean) {
        isIs_editor_recommend = is_editor_recommend
    }

    fun setIs_show_summary(is_show_summary: Boolean) {
        isIs_show_summary = is_show_summary
    }

    fun setIs_author_external(is_author_external: Boolean) {
        isIs_author_external = is_author_external
    }
}

open class Subject : Serializable, RealmObject() {
    /**
     * url : http://www.guokr.com/scientific/subject/medicine/
     * date_created : 2014-05-23T16:22:09.282129+08:00
     * name : 医学
     * key : medicine
     * articles_count : 1819
     */
    var url: String? = null
    var date_created: String? = null
    var name: String? = null
    var key: String? = null
    var articles_count = 0
}

open class GuokrAuthor : Serializable, RealmObject() {
    /**
     * ukey : 1jodqq
     * is_title_authorized : true
     * nickname : 飞刀断雨
     * master_category : personal
     * amended_reliability : 0
     * is_exists : true
     * title : 临床医学专业，玄牝之门小组管理员
     * url : http://www.guokr.com/i/0093516434/
     * gender : female
     * followers_count : 221
     * avatar : {"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"}
     * resource_url : http://apis.guokr.com/community/user/1jodqq.json
     */
    var ukey: String? = null
    var isIs_title_authorized = false
        private set
    var nickname: String? = null
    var master_category: String? = null
    var amended_reliability: String? = null
    var isIs_exists = false
        private set
    var title: String? = null
    var url: String? = null
    var gender: String? = null
    var followers_count = 0
    var avatar: Avatar? = null
    var resource_url: String? = null

    fun setIs_title_authorized(is_title_authorized: Boolean) {
        isIs_title_authorized = is_title_authorized
    }

    fun setIs_exists(is_exists: Boolean) {
        isIs_exists = is_exists
    }
}

open class Avatar : Serializable, RealmObject() {
    /**
     * large : https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160
     * small : https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24
     * normal : https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48
     */
    var large: String? = null
    var small: String? = null
    var normal: String? = null
}

open class ImageInfo : Serializable, RealmObject() {
    /**
     * url : https://2-im.guokr.com/bfjAMXmfv0qwjQtnzLVOpJt-vu98su4VvVcHR_sUoh9KAQAA8AAAAEpQ.jpg
     * width : 330
     * height : 240
     */
    var url: String? = null
    var width = 0
    var height = 0
}

open class Minisite : Serializable, RealmObject() {
    /**
     * name : 健康朝九晚五
     * url : http://www.guokr.com/site/health/
     * introduction : 每天的朝九晚五，上几篇健康小文，有趣、靠谱，还很给力
     * key : health
     * date_created : 2010-10-20T16:20:43+08:00
     * icon : https://2-im.guokr.com/gDNaY8kFHIUnyDRLBlb1hWY3fwK9eAuV5icTVSuh7TNuAAAAWgAAAEpQ.jpg
     */
    var name: String? = null
    var url: String? = null
    var introduction: String? = null
    var key: String? = null
    var date_created: String? = null
    var icon: String? = null
}

open class Authors : Serializable, RealmObject() {
    /**
     * ukey : 1jodqq
     * is_title_authorized : true
     * nickname : 飞刀断雨
     * master_category : personal
     * amended_reliability : 0
     * is_exists : true
     * title : 临床医学专业，玄牝之门小组管理员
     * url : http://www.guokr.com/i/0093516434/
     * gender : female
     * followers_count : 221
     * avatar : {"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"}
     * resource_url : http://apis.guokr.com/community/user/1jodqq.json
     */
    var ukey: String? = null
    var isIs_title_authorized = false
        private set
    var nickname: String? = null
    var master_category: String? = null
    var amended_reliability: String? = null
    var isIs_exists = false
        private set
    var title: String? = null
    var url: String? = null
    var gender: String? = null
    var followers_count = 0
    var avatar: Avatar? = null
    var resource_url: String? = null

    fun setIs_title_authorized(is_title_authorized: Boolean) {
        isIs_title_authorized = is_title_authorized
    }

    fun setIs_exists(is_exists: Boolean) {
        isIs_exists = is_exists
    }
}

open class AvatarX : Serializable, RealmObject() {
    /**
     * large : https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160
     * small : https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24
     * normal : https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48
     */
    var large: String? = null
    var small: String? = null
    var normal: String? = null
}

class GuokrNewsContent {
    /**
     * now : 2018-06-10T19:07:18.169461+08:00
     * ok : true
     * result : {"image":"","is_replyable":true,"channels":[],"channel_keys":[],"preface":"","id":443006,"subject":{"url":"http://www.guokr.com/scientific/subject/medicine/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"医学","key":"medicine","articles_count":1819},"is_editor_recommend":false,"copyright":"owned_by_guokr","author":{"ukey":"1jodqq","is_title_authorized":true,"nickname":"飞刀断雨","master_category":"personal","amended_reliability":"0","is_exists":true,"title":"临床医学专业，玄牝之门小组管理员","url":"http://www.guokr.com/i/0093516434/","gender":"female","followers_count":221,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/1jodqq.json"},"image_description":"","content":"<div>
     *
     *号称能\u201c包生男\u201d的药物，从前一直有，以后也不会消失。这些药物又可以分为两大类。第一类谋财不害命，宣称无效退款，发货就发几颗维生素丸或者糯米粉丸，虽然退款率高达一半，但还有另一半能稳稳白赚。比第一类更可怕的，是谋财又害命的第二类药物，它们常常顶着\u201c转胎药\u201d的名字，干着致人流产、畸形、生病的勾当。<\/p>\r\n\r\n<h2>\u201c转胎药\u201d，其实是大剂量雄激素<\/h2>\r\n\r\n
     *
     *随便搜一下\u201c转胎药\u201d，就会看到许多不幸的新闻\u2014\u2014<\/p>\r\n\r\n
     *
     *2012年3月，河南驻马店，女子因服用转胎丸生下双性儿。<\/p>\r\n\r\n
     *
     *<img src=\"https://1-im.guokr.com/8Trde1eaoQAx9DWXIX1ESc8cc8H6rzLsjPjJhcOn3dKjAwAAxgEAAFBO.png?imageView2/1/w/555/h/270\"></img><\/p>\r\n\r\n
     *
     *2017年8月，江苏连云港，家长带着一名现已四岁的双性儿来求医，询问病史后发现，其母亲怀孕时服用过\u201c转胎药\u201d。<\/p>\r\n\r\n
     *
     *<img src=\"https://2-im.guokr.com/t6RpvdziERn3fKIJVWto5249Vm0MO6BFBfMH0rozX5znAwAAGgEAAFBO.png?imageView2/1/w/555/h/156\"></img><\/p>\r\n\r\n
     *
     *这些新闻里的\u201c双性人\u201d，医学上叫做女性假两性畸形。这种畸形，是因为在胚胎发育期间接触了过多的雄激素。<\/p>\r\n\r\n
     *
     *雄激素从何而来？常见原因有两种，一种是胎儿自身产生了过多的雄激素，如患有先天性肾上腺皮质增生症的胎儿；另一种就是孕妇给了胎儿过多的雄激素，比如孕妇患有多囊卵巢等导致雄激素增高的疾病，又比如，孕妇在孕期服用了雄激素类药物<sup>[1]<\/sup>。<\/p>\r\n\r\n</sup>
     *
     *新闻里孕妇吃下的\u201c转胎药\u201d，正是雄激素的来源，也是造成这些孩子畸形的罪魁祸首。而且这些悲剧绝不是孤例。1997年11月~1999年12月，安徽省淮南矿工二院曾陆续收治了九名来自同一村镇的假两性畸形患者，她们的母亲均在孕2月左右服用了名为\u201c转胎药\u201d的甲基睾丸素<sup>[2]<\/sup>。<\/p>\r\n\r\n</sup>
     *
     *甲基睾丸素是雄激素类药，会导致女性胎儿生殖器官发育异常。就算在大剂量雄激素作用下变得看似男性，性染色体还是女孩，结果就会变成女性假两性畸形。<\/p>\r\n\r\n
     *
     *如果没有雄激素的干扰，她们本该发育成正常女性，故而她们卵巢、子宫、阴道都有。然而过多的雄激素让外生殖器男性化，男性化的严重程度取决于接触雄激素的时机和量。如果服药较晚，胎儿泌尿生殖系统已经发育完成，那么可能仅表现为阴蒂粗大。如果孕早期就服药，泌尿生殖系统还没有发育完成，则可能会有阴茎形成，阴唇肥大融合如同没有睾丸的阴囊，覆盖尿道和阴道口，还可能会伴有泌尿系统畸形。<\/p>\r\n\r\n
     *
     *比起胎儿本身原因导致的男性化畸形，服药导致的畸形症状一般都相对较轻，而且因为脱离母体后就不再处于高雄激素环境，畸形症状在出生后不会进一步加剧，有的人还能正常月经来潮甚至正常生育<sup>[3]<\/sup>。但是先天性畸形毕竟已经造成，除了男性化外观带来的心理压力外，许多人还伴有多毛症、经血不能正常排出、血尿等问题<sup>[4]<\/sup>。<\/p>\r\n\r\n</sup></sup>
     *
     *如果要治疗假两性畸形，矫正手术最佳的年龄是2~4岁，过早有病情复发、需要二次手术的风险，过晚又会影响患者的精神心理<sup>[5]<\/sup>。然而因为家中对此病认识的不足，患者就医时往往已错过最佳手术时机。另外，阴蒂成形手术还有导致患者性敏感度降低的风险<sup>[6]<\/sup>，很多孩子手术后也会因为社会性别和生理性别不相符，心理上发生严重冲突<sup>[7]<\/sup>。<\/p>\r\n\r\n</sup></sup></sup>
     *
     *也不要以为雄激素只会伤害女胎，男胎一样会出问题。转胎药完全可能造成流产。吃完转胎药还能平安出生的，都是命大。这是因为甲基睾丸素还有抗生育的作用，可降低蜕膜组织雌激素受体含量，使蜕膜和绒毛组织退化变性<sup>[8]<\/sup>，简而言之，这种药能切断胚胎和母体的联系，和药物流产是一个道理。临床上也常有将甲基睾丸素和药物流产的药物协同使用，以加强药流效果的。<\/p>\r\n\r\n</sup><h2>安全又有效的\u201c转胎药\u201d，根本不可能存在<\/h2>\r\n\r\n
     *
     *肚子里的孩子是男还是女，受精卵形成的那一刻就已经决定了。<\/p>\r\n\r\n
     *
     *决定生物性状的，是遗传物质，所以鸭蛋里不会孵出熊猫，人类也不会生出龙宝宝。女性的性染色体是XX组合，男性的性染色体是XY组合。想强行把胎儿的X变成Y，是绝对办不到的。<\/p>\r\n\r\n
     *
     *<img src=\"https://3-im.guokr.com/0lSlGxgGIQkSQVA_Ja0U3Gxo0tPNIxuBCIXElrbkhpEXBAAAagMAAFBO.png?imageView2/1/w/555/h/463\"></img>看看X染色体和Y染色体的长相差多少吧。图片来源：Jonathan Bailey, NHGRI<\/p>\r\n\r\n
     *
     *服下\u201c转胎药\u201d的那一刻，往往怀孕已经好几个月，受精卵不知分裂了多少次，这时候要把胚胎的X染色体变成Y染色体，这个操作的难度有多大呢？<\/p>\r\n\r\n
     *
     *首先，得把所有胚胎细胞的染色体都改掉，一个细胞都不可以漏掉。否则万一用来分化成丁丁蛋蛋的那部分细胞正好没改，不就白费了这番辛苦？<\/p>\r\n\r\n
     *
     *其次，人类的46条染色体各有不同、不可或缺，改的时候得从46条染色体里精准地找出其中一条X染色体，不能误伤另一条X染色体和其它常染色体，更不能把两个X染色体全变成Y染色体\u2014\u2014这样的胚胎缺少X染色体上的发育所必须的关键基因，根本活不下来。得是什么样的\u201c转胎药\u201d才能有这么好的眼神呢？<\/p>\r\n\r\n
     *
     *最后也是最重要的一点，染色体不是任你搓圆揉扁的橡皮泥。染色体中有海量的遗传信息，Y染色体上更是有X染色体上所没有的性别决定基因SRY（Sex-Determining Region Y）和其它一些睾丸刺激因子、生精相关基因等，只有这些基因相互作用，最后才能刺激男性的生殖器官生成，任何一个环节出现异常，都会导致发育异常、不育等等问题<sup>[9]<\/sup>。想吃个药就把基因\u201c改头换面\u201d？怎么可能。退一万步说，要真有这么强大的药，你不怕它把整个基因组修改得面目全非，造成各种畸形乃至癌症吗？<\/p>\r\n\r\n</sup>
     *
     *如果吃转胎药只是一个像\u201c西红柿炒蛋里要不要加糖\u201d一样无可无不可的问题，我们大可以不去深究，各人随意。但这是药啊！医生和药师给孕妇开药的时候，在了解药物成分和用药风险的基础上，尚且要权衡利弊、斟酌用量，怎么你们随便搞一个来历不明的药，就敢让孕妇吃呢？<\/p>\r\n\r\n
     *
     *写这篇文章的过程中，我的心情一直非常沉重，调整了几次才慢慢组织出来。一方面，我心疼那些因为父母家人的无知而遭受无妄之灾，无端承受畸形痛苦的孩子们；另一方面，我也总会想起那些被堕胎的、被溺死的女婴。说到底，许多悲剧都是缘于重男轻女的思想。对男孩的偏好，造成了中国畸高的出生性别比和明显偏离正常的女孩死亡水平。即使我写了此文，也只是给重男轻女的人指明此路不通，而他们还会有更多层出不穷的办法，阻止女婴在这个世界降临和生存。每思及此，心灰意冷。 （编辑：游识猷） <\/p>\r\n\r\n<h2 class=\"references\">参考资料<\/h2>\r\n\r\n 1. Galani A，Kitsiou\u2014Tzeli S，Sofokleous C，et a1．Androgeninsensitivity syndrome：clinical features and molecular defects[J]．Hormones(Athens)， 2008，7(3)：217\u2014229．<\/li>\r\n\t 1. 李莉.孕早期服甲基睾丸素致子代女性假两性畸形9例[J].中国煤炭工业医学杂志,2001,(6):423. DOI:10.3969/j.issn.1007-9564.2001.06.010.<\/li>\r\n\t 1. 谢幸, 苟文丽. 妇产科学第八版[M]. 北京市:人民卫生出版社, 2013.<\/li>\r\n\t 1. 李全荣,林蓓,伊喜苓, 等.342例女性假两性畸形的荟萃分析[J].中国医科大学学报,2010,(1):64-66,70.<\/li>\r\n\t 1. LiBU，Balint JP．Cyclicvomiting syndrome：evolutionin our understanding of a brain\u2014gutdisorder[J]．Adv Pedimr，2000，47(2)： 117一160．<\/li>\r\n\t 1. ChepyMa P，Svoboda RP，Olden KW．Treatment of cyclic vomiting syndrome[J]．Curt Treat Options Gastroenteml，2007，10(4)：273- 282．<\/li>\r\n\t 1. 关琳瑶,刘长松.女性假两性畸形患者的心理分析及干预[J].中国美容医学,2012,(3):508-509. DOI:10.3969/j.issn.1008-6455.2012.03.072.<\/li>\r\n\t 1. 彭飞红.甲基睾丸素用于药物流产的临床应用[J].深圳中西医结合杂志,2001,(3):171-172. DOI:10.3969/j.issn.1007-0893.2001.03.024.<\/li>\r\n\t 1. 段晓刚,张红国.人类Y染色体研究进展[J].中国优生与遗传杂志,2006,(1):1-3. DOI:10.3969/j.issn.1006-9534.2006.01.001.<\/li>\r\n<\/ol><\/div>","is_show_summary":false,"minisite_key":"health","image_info":{"url":"https://2-im.guokr.com/bfjAMXmfv0qwjQtnzLVOpJt-vu98su4VvVcHR_sUoh9KAQAA8AAAAEpQ.jpg","width":330,"height":240},"subject_key":"medicine","minisite":{"name":"健康朝九晚五","url":"http://www.guokr.com/site/health/","introduction":"每天的朝九晚五，上几篇健康小文，有趣、靠谱，还很给力","key":"health","date_created":"2010-10-20T16:20:43+08:00","icon":"https://2-im.guokr.com/gDNaY8kFHIUnyDRLBlb1hWY3fwK9eAuV5icTVSuh7TNuAAAAWgAAAEpQ.jpg"},"tags":["转胎药","转胎丸"],"date_published":"2018-06-08T14:26:01+08:00","video_content":"","authors":[{"ukey":"1jodqq","is_title_authorized":true,"nickname":"飞刀断雨","master_category":"personal","amended_reliability":"0","is_exists":true,"title":"临床医学专业，玄牝之门小组管理员","url":"http://www.guokr.com/i/0093516434/","gender":"female","followers_count":221,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/1jodqq.json"}],"replies_count":14,"is_author_external":false,"recommends_count":0,"title_hide":"\u201c转胎丸\u201d到底是个什么玩意？","date_modified":"2018-06-08T14:59:40.953735+08:00","url":"http://www.guokr.com/article/443006/","title":"\u201c转胎丸\u201d到底是个什么玩意？","small_image":"https://2-im.guokr.com/bfjAMXmfv0qwjQtnzLVOpJt-vu98su4VvVcHR_sUoh9KAQAA8AAAAEpQ.jpg","summary":"怀孕后吃几颗药，就能保证生男孩？所谓把女胎\u201c转\u201d成男胎的\u201c转胎丸\u201d，其实已经酿成了不少悲剧。","ukey_author":"1jodqq","date_created":"2018-06-08T14:26:01+08:00","resource_url":"http://apis.guokr.com/minisite/article/443006.json"}
    </h2></h2></h2></div> */
    var now: String? = null
    var isOk = false
    var result: Result? = null

    class Result {
        /**
         * image :
         * is_replyable : true
         * channels : []
         * channel_keys : []
         * preface :
         * id : 443006
         * subject : {"url":"http://www.guokr.com/scientific/subject/medicine/","date_created":"2014-05-23T16:22:09.282129+08:00","name":"医学","key":"medicine","articles_count":1819}
         * is_editor_recommend : false
         * copyright : owned_by_guokr
         * author : {"ukey":"1jodqq","is_title_authorized":true,"nickname":"飞刀断雨","master_category":"personal","amended_reliability":"0","is_exists":true,"title":"临床医学专业，玄牝之门小组管理员","url":"http://www.guokr.com/i/0093516434/","gender":"female","followers_count":221,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/1jodqq.json"}
         * image_description :
         * content : <div>
         *
         *号称能“包生男”的药物，从前一直有，以后也不会消失。这些药物又可以分为两大类。第一类谋财不害命，宣称无效退款，发货就发几颗维生素丸或者糯米粉丸，虽然退款率高达一半，但还有另一半能稳稳白赚。比第一类更可怕的，是谋财又害命的第二类药物，它们常常顶着“转胎药”的名字，干着致人流产、畸形、生病的勾当。
         *
         * <h2>“转胎药”，其实是大剂量雄激素</h2>
         *
         *
         * 随便搜一下“转胎药”，就会看到许多不幸的新闻——
         *
         *
         * 2012年3月，河南驻马店，女子因服用转胎丸生下双性儿。
         *
         *
         * <img src="https://1-im.guokr.com/8Trde1eaoQAx9DWXIX1ESc8cc8H6rzLsjPjJhcOn3dKjAwAAxgEAAFBO.png?imageView2/1/w/555/h/270"></img>
         *
         *
         * 2017年8月，江苏连云港，家长带着一名现已四岁的双性儿来求医，询问病史后发现，其母亲怀孕时服用过“转胎药”。
         *
         *
         * <img src="https://2-im.guokr.com/t6RpvdziERn3fKIJVWto5249Vm0MO6BFBfMH0rozX5znAwAAGgEAAFBO.png?imageView2/1/w/555/h/156"></img>
         *
         *
         * 这些新闻里的“双性人”，医学上叫做女性假两性畸形。这种畸形，是因为在胚胎发育期间接触了过多的雄激素。
         *
         *
         * 雄激素从何而来？常见原因有两种，一种是胎儿自身产生了过多的雄激素，如患有先天性肾上腺皮质增生症的胎儿；另一种就是孕妇给了胎儿过多的雄激素，比如孕妇患有多囊卵巢等导致雄激素增高的疾病，又比如，孕妇在孕期服用了雄激素类药物<sup>[1]</sup>。
         *
         *
         * 新闻里孕妇吃下的“转胎药”，正是雄激素的来源，也是造成这些孩子畸形的罪魁祸首。而且这些悲剧绝不是孤例。1997年11月~1999年12月，安徽省淮南矿工二院曾陆续收治了九名来自同一村镇的假两性畸形患者，她们的母亲均在孕2月左右服用了名为“转胎药”的甲基睾丸素<sup>[2]</sup>。
         *
         *
         * 甲基睾丸素是雄激素类药，会导致女性胎儿生殖器官发育异常。就算在大剂量雄激素作用下变得看似男性，性染色体还是女孩，结果就会变成女性假两性畸形。
         *
         *
         * 如果没有雄激素的干扰，她们本该发育成正常女性，故而她们卵巢、子宫、阴道都有。然而过多的雄激素让外生殖器男性化，男性化的严重程度取决于接触雄激素的时机和量。如果服药较晚，胎儿泌尿生殖系统已经发育完成，那么可能仅表现为阴蒂粗大。如果孕早期就服药，泌尿生殖系统还没有发育完成，则可能会有阴茎形成，阴唇肥大融合如同没有睾丸的阴囊，覆盖尿道和阴道口，还可能会伴有泌尿系统畸形。
         *
         *
         * 比起胎儿本身原因导致的男性化畸形，服药导致的畸形症状一般都相对较轻，而且因为脱离母体后就不再处于高雄激素环境，畸形症状在出生后不会进一步加剧，有的人还能正常月经来潮甚至正常生育<sup>[3]</sup>。但是先天性畸形毕竟已经造成，除了男性化外观带来的心理压力外，许多人还伴有多毛症、经血不能正常排出、血尿等问题<sup>[4]</sup>。
         *
         *
         * 如果要治疗假两性畸形，矫正手术最佳的年龄是2~4岁，过早有病情复发、需要二次手术的风险，过晚又会影响患者的精神心理<sup>[5]</sup>。然而因为家中对此病认识的不足，患者就医时往往已错过最佳手术时机。另外，阴蒂成形手术还有导致患者性敏感度降低的风险<sup>[6]</sup>，很多孩子手术后也会因为社会性别和生理性别不相符，心理上发生严重冲突<sup>[7]</sup>。
         *
         *
         * 也不要以为雄激素只会伤害女胎，男胎一样会出问题。转胎药完全可能造成流产。吃完转胎药还能平安出生的，都是命大。这是因为甲基睾丸素还有抗生育的作用，可降低蜕膜组织雌激素受体含量，使蜕膜和绒毛组织退化变性<sup>[8]</sup>，简而言之，这种药能切断胚胎和母体的联系，和药物流产是一个道理。临床上也常有将甲基睾丸素和药物流产的药物协同使用，以加强药流效果的。
         *
         * <h2>安全又有效的“转胎药”，根本不可能存在</h2>
         *
         *
         * 肚子里的孩子是男还是女，受精卵形成的那一刻就已经决定了。
         *
         *
         * 决定生物性状的，是遗传物质，所以鸭蛋里不会孵出熊猫，人类也不会生出龙宝宝。女性的性染色体是XX组合，男性的性染色体是XY组合。想强行把胎儿的X变成Y，是绝对办不到的。
         *
         *
         * <img src="https://3-im.guokr.com/0lSlGxgGIQkSQVA_Ja0U3Gxo0tPNIxuBCIXElrbkhpEXBAAAagMAAFBO.png?imageView2/1/w/555/h/463"></img>看看X染色体和Y染色体的长相差多少吧。图片来源：Jonathan Bailey, NHGRI
         *
         *
         * 服下“转胎药”的那一刻，往往怀孕已经好几个月，受精卵不知分裂了多少次，这时候要把胚胎的X染色体变成Y染色体，这个操作的难度有多大呢？
         *
         *
         * 首先，得把所有胚胎细胞的染色体都改掉，一个细胞都不可以漏掉。否则万一用来分化成丁丁蛋蛋的那部分细胞正好没改，不就白费了这番辛苦？
         *
         *
         * 其次，人类的46条染色体各有不同、不可或缺，改的时候得从46条染色体里精准地找出其中一条X染色体，不能误伤另一条X染色体和其它常染色体，更不能把两个X染色体全变成Y染色体——这样的胚胎缺少X染色体上的发育所必须的关键基因，根本活不下来。得是什么样的“转胎药”才能有这么好的眼神呢？
         *
         *
         * 最后也是最重要的一点，染色体不是任你搓圆揉扁的橡皮泥。染色体中有海量的遗传信息，Y染色体上更是有X染色体上所没有的性别决定基因SRY（Sex-Determining Region Y）和其它一些睾丸刺激因子、生精相关基因等，只有这些基因相互作用，最后才能刺激男性的生殖器官生成，任何一个环节出现异常，都会导致发育异常、不育等等问题<sup>[9]</sup>。想吃个药就把基因“改头换面”？怎么可能。退一万步说，要真有这么强大的药，你不怕它把整个基因组修改得面目全非，造成各种畸形乃至癌症吗？
         *
         *
         * 如果吃转胎药只是一个像“西红柿炒蛋里要不要加糖”一样无可无不可的问题，我们大可以不去深究，各人随意。但这是药啊！医生和药师给孕妇开药的时候，在了解药物成分和用药风险的基础上，尚且要权衡利弊、斟酌用量，怎么你们随便搞一个来历不明的药，就敢让孕妇吃呢？
         *
         *
         * 写这篇文章的过程中，我的心情一直非常沉重，调整了几次才慢慢组织出来。一方面，我心疼那些因为父母家人的无知而遭受无妄之灾，无端承受畸形痛苦的孩子们；另一方面，我也总会想起那些被堕胎的、被溺死的女婴。说到底，许多悲剧都是缘于重男轻女的思想。对男孩的偏好，造成了中国畸高的出生性别比和明显偏离正常的女孩死亡水平。即使我写了此文，也只是给重男轻女的人指明此路不通，而他们还会有更多层出不穷的办法，阻止女婴在这个世界降临和生存。每思及此，心灰意冷。 （编辑：游识猷） 
         *
         * <h2 class="references">参考资料</h2>
         *
         *  1. Galani A，Kitsiou—Tzeli S，Sofokleous C，et a1．Androgeninsensitivity syndrome：clinical features and molecular defects[J]．Hormones(Athens)， 2008，7(3)：217—229．
         *  1. 李莉.孕早期服甲基睾丸素致子代女性假两性畸形9例[J].中国煤炭工业医学杂志,2001,(6):423. DOI:10.3969/j.issn.1007-9564.2001.06.010.
         *  1. 谢幸, 苟文丽. 妇产科学第八版[M]. 北京市:人民卫生出版社, 2013.
         *  1. 李全荣,林蓓,伊喜苓, 等.342例女性假两性畸形的荟萃分析[J].中国医科大学学报,2010,(1):64-66,70.
         *  1. LiBU，Balint JP．Cyclicvomiting syndrome：evolutionin our understanding of a brain—gutdisorder[J]．Adv Pedimr，2000，47(2)： 117一160．
         *  1. ChepyMa P，Svoboda RP，Olden KW．Treatment of cyclic vomiting syndrome[J]．Curt Treat Options Gastroenteml，2007，10(4)：273- 282．
         *  1. 关琳瑶,刘长松.女性假两性畸形患者的心理分析及干预[J].中国美容医学,2012,(3):508-509. DOI:10.3969/j.issn.1008-6455.2012.03.072.
         *  1. 彭飞红.甲基睾丸素用于药物流产的临床应用[J].深圳中西医结合杂志,2001,(3):171-172. DOI:10.3969/j.issn.1007-0893.2001.03.024.
         *  1. 段晓刚,张红国.人类Y染色体研究进展[J].中国优生与遗传杂志,2006,(1):1-3. DOI:10.3969/j.issn.1006-9534.2006.01.001.
        </div> *
         * is_show_summary : false
         * minisite_key : health
         * image_info : {"url":"https://2-im.guokr.com/bfjAMXmfv0qwjQtnzLVOpJt-vu98su4VvVcHR_sUoh9KAQAA8AAAAEpQ.jpg","width":330,"height":240}
         * subject_key : medicine
         * minisite : {"name":"健康朝九晚五","url":"http://www.guokr.com/site/health/","introduction":"每天的朝九晚五，上几篇健康小文，有趣、靠谱，还很给力","key":"health","date_created":"2010-10-20T16:20:43+08:00","icon":"https://2-im.guokr.com/gDNaY8kFHIUnyDRLBlb1hWY3fwK9eAuV5icTVSuh7TNuAAAAWgAAAEpQ.jpg"}
         * tags : ["转胎药","转胎丸"]
         * date_published : 2018-06-08T14:26:01+08:00
         * video_content :
         * authors : [{"ukey":"1jodqq","is_title_authorized":true,"nickname":"飞刀断雨","master_category":"personal","amended_reliability":"0","is_exists":true,"title":"临床医学专业，玄牝之门小组管理员","url":"http://www.guokr.com/i/0093516434/","gender":"female","followers_count":221,"avatar":{"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"},"resource_url":"http://apis.guokr.com/community/user/1jodqq.json"}]
         * replies_count : 14
         * is_author_external : false
         * recommends_count : 0
         * title_hide : “转胎丸”到底是个什么玩意？
         * date_modified : 2018-06-08T14:59:40.953735+08:00
         * url : http://www.guokr.com/article/443006/
         * title : “转胎丸”到底是个什么玩意？
         * small_image : https://2-im.guokr.com/bfjAMXmfv0qwjQtnzLVOpJt-vu98su4VvVcHR_sUoh9KAQAA8AAAAEpQ.jpg
         * summary : 怀孕后吃几颗药，就能保证生男孩？所谓把女胎“转”成男胎的“转胎丸”，其实已经酿成了不少悲剧。
         * ukey_author : 1jodqq
         * date_created : 2018-06-08T14:26:01+08:00
         * resource_url : http://apis.guokr.com/minisite/article/443006.json
         */
        var image: String? = null
        var isIs_replyable = false
            private set
        var preface: String? = null
        var id = 0
        var subject: Subject? = null
        var isIs_editor_recommend = false
            private set
        var copyright: String? = null
        var author: Author? = null
        var image_description: String? = null
        var content: String? = null
        var isIs_show_summary = false
            private set
        var minisite_key: String? = null
        var image_info: ImageInfo? = null
        var subject_key: String? = null
        var minisite: Minisite? = null
        var date_published: String? = null
        var video_content: String? = null
        var replies_count = 0
        var isIs_author_external = false
            private set
        var recommends_count = 0
        var title_hide: String? = null
        var date_modified: String? = null
        var url: String? = null
        var title: String? = null
        var small_image: String? = null
        var summary: String? = null
        var ukey_author: String? = null
        var date_created: String? = null
        var resource_url: String? = null
        var channels: List<*>? = null
        var channel_keys: List<*>? = null
        var tags: List<String>? = null
        var authors: List<Authors>? = null

        fun setIs_replyable(is_replyable: Boolean) {
            isIs_replyable = is_replyable
        }

        fun setIs_editor_recommend(is_editor_recommend: Boolean) {
            isIs_editor_recommend = is_editor_recommend
        }

        fun setIs_show_summary(is_show_summary: Boolean) {
            isIs_show_summary = is_show_summary
        }

        fun setIs_author_external(is_author_external: Boolean) {
            isIs_author_external = is_author_external
        }

        class Subject {
            /**
             * url : http://www.guokr.com/scientific/subject/medicine/
             * date_created : 2014-05-23T16:22:09.282129+08:00
             * name : 医学
             * key : medicine
             * articles_count : 1819
             */
            var url: String? = null
            var date_created: String? = null
            var name: String? = null
            var key: String? = null
            var articles_count = 0
        }

        class Author {
            /**
             * ukey : 1jodqq
             * is_title_authorized : true
             * nickname : 飞刀断雨
             * master_category : personal
             * amended_reliability : 0
             * is_exists : true
             * title : 临床医学专业，玄牝之门小组管理员
             * url : http://www.guokr.com/i/0093516434/
             * gender : female
             * followers_count : 221
             * avatar : {"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"}
             * resource_url : http://apis.guokr.com/community/user/1jodqq.json
             */
            var ukey: String? = null
            var isIs_title_authorized = false
                private set
            var nickname: String? = null
            var master_category: String? = null
            var amended_reliability: String? = null
            var isIs_exists = false
                private set
            var title: String? = null
            var url: String? = null
            var gender: String? = null
            var followers_count = 0
            var avatar: Avatar? = null
            var resource_url: String? = null

            fun setIs_title_authorized(is_title_authorized: Boolean) {
                isIs_title_authorized = is_title_authorized
            }

            fun setIs_exists(is_exists: Boolean) {
                isIs_exists = is_exists
            }

            class Avatar {
                /**
                 * large : https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160
                 * small : https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24
                 * normal : https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48
                 */
                var large: String? = null
                var small: String? = null
                var normal: String? = null
            }
        }

        class ImageInfo {
            /**
             * url : https://2-im.guokr.com/bfjAMXmfv0qwjQtnzLVOpJt-vu98su4VvVcHR_sUoh9KAQAA8AAAAEpQ.jpg
             * width : 330
             * height : 240
             */
            var url: String? = null
            var width = 0
            var height = 0
        }

        class Minisite {
            /**
             * name : 健康朝九晚五
             * url : http://www.guokr.com/site/health/
             * introduction : 每天的朝九晚五，上几篇健康小文，有趣、靠谱，还很给力
             * key : health
             * date_created : 2010-10-20T16:20:43+08:00
             * icon : https://2-im.guokr.com/gDNaY8kFHIUnyDRLBlb1hWY3fwK9eAuV5icTVSuh7TNuAAAAWgAAAEpQ.jpg
             */
            var name: String? = null
            var url: String? = null
            var introduction: String? = null
            var key: String? = null
            var date_created: String? = null
            var icon: String? = null
        }

        class Authors {
            /**
             * ukey : 1jodqq
             * is_title_authorized : true
             * nickname : 飞刀断雨
             * master_category : personal
             * amended_reliability : 0
             * is_exists : true
             * title : 临床医学专业，玄牝之门小组管理员
             * url : http://www.guokr.com/i/0093516434/
             * gender : female
             * followers_count : 221
             * avatar : {"large":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160","small":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24","normal":"https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48"}
             * resource_url : http://apis.guokr.com/community/user/1jodqq.json
             */
            var ukey: String? = null
            var isIs_title_authorized = false
                private set
            var nickname: String? = null
            var master_category: String? = null
            var amended_reliability: String? = null
            var isIs_exists = false
                private set
            var title: String? = null
            var url: String? = null
            var gender: String? = null
            var followers_count = 0
            var avatar: Any? = null
            var resource_url: String? = null

            fun setIs_title_authorized(is_title_authorized: Boolean) {
                isIs_title_authorized = is_title_authorized
            }

            fun setIs_exists(is_exists: Boolean) {
                isIs_exists = is_exists
            }

            class AvatarX {
                /**
                 * large : https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/160/h/160
                 * small : https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/24/h/24
                 * normal : https://3-im.guokr.com/1XElhEGExSSNXC7Z3F2ZC_M7akiW_uq9rKeXbAwVotQ4AAAAOAAAAFBO.png?imageView2/1/w/48/h/48
                 */
                var large: String? = null
                var small: String? = null
                var normal: String? = null
            }
        }
    }
}
