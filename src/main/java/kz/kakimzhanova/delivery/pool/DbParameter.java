package kz.kakimzhanova.delivery.pool;

/**
 * DbParameter contains all database parameter names
 */
enum DbParameter {
    DB_DRIVER("db.driver"), DB_URL("db.url"), DB_USER("db.user"), DB_PASSWORD("db.password"), DB_POOL_SIZE("db.poolsize");
    private String name;

    DbParameter(String name){
        this.name = name;
    }

    String getName() {
        return name;
    }
}
