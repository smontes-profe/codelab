public enum TipoEnemigo {
    TROLL(50),
    TRASGO(25);
    private final int danio;
    TipoEnemigo(int danio) {
        this.danio = danio;
    }
    public int getDanio() {
        return danio;
    }
}
