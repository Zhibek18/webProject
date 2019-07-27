package kz.kakimzhanova.delivery.pool;

public enum DBParameter {
    DB_DRIVER("db.driver"), DB_URL("db.url"), DB_USER("db.user"), DB_PASSWORD("db.password"), DB_POOL_SIZE("db.poolsize");
    private String name;

    DBParameter(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
