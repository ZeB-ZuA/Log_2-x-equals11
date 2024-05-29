# Log_2-x-equals11

Se debe realizar una aplicación para Android con el *juego 2048* usando Firebase. 
La idea es fusionar fichas en una cuadrícula de 4x4 para crear una ficha con el número 2048.


## Consideraciones

1. El juego comienza con dos fichas colocadas al azar en la cuadrícula, cada una con un valor de 2 o 4.

2. El jugador puede mover todas las fichas de la cuadrícula en cuatro direcciones: arriba, abajo, izquierda y derecha. Cuando el jugador hace un movimiento, todas las fichas de la cuadrícula se moverán en la dirección elegida hasta llegar al borde de la cuadrícula o chocar con otra ficha.

3. Si dos fichas con el mismo número chocan mientras se mueven en una dirección específica, se fusionarán con un valor igual a la suma de sus valores originales. Por ejemplo, si dos fichas con el número 2 chocan, se fusionarán para formar una sola ficha con el número 4.

4. Después de cada movimiento, aparecerá una nueva ficha en un lugar vacío aleatorio de la cuadrícula. Esta nueva ficha tendrá un valor de 2 o 4.

5. El juego termina cuando la cuadrícula está llena y no quedan más movimientos válidos por hacer (es decir, no hay fichas adyacentes con el mismo valor ni espacios vacíos para

fusionarse). En este punto, la puntuación final del jugador se calcula en función de los valores de todas las fichas de la cuadrícula.

6. La puntuación del jugador aumenta cada vez que se fusionan fichas. La puntuación de cada combinación es el valor de la ficha recién creada. Por ejemplo, fusionar dos fichas con el número 32 aumentará la puntuación del jugador en 32 puntos.

7. El jugador gana cuando crea con éxito una ficha con el número 2048.

## Evaluación

· Generación de pistas, ver consideración 1. (0.3 puntos)

· Guardar juego en Firebase (1 punto)

· Actualización de fichas, ver consideración 3 y 4. (0.7 puntos)

· Calcular Score, ver consideración 6 (0.5 punto)

· Generar movimiento del tablero, ver consideración 2 (1.5 punto)

· Calcular si el juego finaliza, ver consideración 5 y 7 (1 punto)