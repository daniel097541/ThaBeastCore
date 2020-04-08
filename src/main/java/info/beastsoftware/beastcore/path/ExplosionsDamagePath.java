package info.beastsoftware.beastcore.path;

public enum ExplosionsDamagePath {



    NO_EXPLOSIONS_DAMAGE_PERMISSION("No-Explosions-Damage.permission");


    private String path;


    ExplosionsDamagePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
