package framework.sql;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Entity mapped to table "SEARCH_SUFFIX".
 */
@Entity
public class SearchSuffix {

    @Id
    private Integer id;
    private String suffix;

    @Generated(hash = 1682689098)
    public SearchSuffix(Integer id, String suffix) {
        this.id = id;
        this.suffix = suffix;
    }

    @Generated(hash = 607692482)
    public SearchSuffix() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}
