package framework.sql

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Generated
import org.greenrobot.greendao.annotation.Id

/**
 * Entity mapped to table "SEARCH_SUFFIX".
 */
@Entity
class SearchSuffix @Generated(hash = 1682689098) constructor(@Id var id: Int?, suffix: String) {
    var suffix: String? = suffix
}
