package modelo;

import java.util.Random;

/** Selecciona los objetos que pueden encontrarse tras una victoria. */
public final class Botin {

    private Botin() {
    }

    /**
     * Elige un objeto usando los límites acumulados de {@link GameConfig}.
     *
     * @param random generador usado para la tirada
     * @return objeto obtenido
     * @throws IllegalStateException si la configuración no permite elegir un objeto
     */
    public static Objeto obtenerLoot(Random random) {
        if (GameConfig.PROBABILIDADES_LOOT_ACUMULADAS.length != GameConfig.LOOT_POSIBLE.length) {
            throw new IllegalStateException("Cada posibilidad de loot debe tener una probabilidad asociada.");
        }

        // Por ejemplo, con límites 50, 75 y 100 las probabilidades son 50 %, 25 % y 25 %.
        int tirada = random.nextInt(100) + 1;
        for (int i = 0; i < GameConfig.PROBABILIDADES_LOOT_ACUMULADAS.length; i++) {
            if (tirada <= GameConfig.PROBABILIDADES_LOOT_ACUMULADAS[i]) {
                return GameConfig.LOOT_POSIBLE[i];
            }
        }

        throw new IllegalStateException("Las probabilidades de loot no cubren todos los resultados.");
    }
}
