import java.util.Random;

public final class Botin {

    private Botin() {
    }

    // Utilizamos las mismas probabilidades tanto en la ventana como en la terminal.
    public static Objeto obtenerLoot(Random random) {
        if (GameConfig.PROBABILIDADES_LOOT_ACUMULADAS.length != GameConfig.LOOT_POSIBLE.length) {
            throw new IllegalStateException("Cada posibilidad de loot debe tener una probabilidad asociada.");
        }

        int tirada = random.nextInt(100) + 1;
        for (int i = 0; i < GameConfig.PROBABILIDADES_LOOT_ACUMULADAS.length; i++) {
            if (tirada <= GameConfig.PROBABILIDADES_LOOT_ACUMULADAS[i]) {
                return GameConfig.LOOT_POSIBLE[i];
            }
        }

        throw new IllegalStateException("Las probabilidades de loot no cubren todos los resultados.");
    }
}
