package develop.y.zhzl.detail.model

/**
 * by y on 2016/8/7.
 */
class DetailModel {
    var titleImage: String? = null
    var title: String? = null
    var content: String? = null
    var author: Author? = null

    class Author {
        var profileUrl: String? = null
        var bio: String? = null
        var name: String? = null

    }
}
