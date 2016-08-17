package framework.sql;


import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {

    private static Entity entity;
    private static DaoGenerator daoGenerator;
    private static final String PACKAGE = "develop.y.zhzl";
    private static final String BEAN_NAME = "SearchSuffix";

    private static class SchemaHolder {
        public static final Schema schema = new Schema(1, PACKAGE);
    }

    private static Schema getSchema() {
        return SchemaHolder.schema;
    }

    private static Entity initEntity(String sqlName) {
        entity = getSchema().addEntity(sqlName);
        return entity;
    }

    private static DaoGenerator getDaoGenerator() throws IOException {
        if (daoGenerator == null) {
            daoGenerator = new DaoGenerator();
        }
        return daoGenerator;
    }

    private static void startGreenDao() throws Exception {
        getDaoGenerator().generateAll(getSchema(), "../ZLSimple/app/src/main/java-gen");
    }

    public static void main(String[] args) throws Exception {
        initSearchSuffix();
        startGreenDao();
    }

    private static void initSearchSuffix() {
        initEntity(BEAN_NAME);
        entity.addIntProperty("id").primaryKey();
        entity.addStringProperty("suffix");
    }

}
