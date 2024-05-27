(ns proyectotp2.tortuga)

(defprotocol TortugaAcciones
  (avanzar [tortuga cantidad archivo])
  (izquierda [tortuga angulo-giro])
  (derecha [tortuga angulo-giro])
  (pluma-abajo [tortuga])
  (pluma-arriba [tortuga]))

(defrecord Tortuga [posicion angulo pluma-arriba?]
  TortugaAcciones
  (avanzar
    [tortuga cantidad archivo]
    (let [direction (vector (math/cos (math/to-radians (:angulo tortuga))) (math/sin (math/to-radians (:angulo tortuga))))
          new-position (vector (+ (get (:posicion tortuga) 0) (* (get direction 0) cantidad)) (+ (get (:posicion tortuga) 1) (* (get direction 1) cantidad)))]
      (if (:pluma-arriba? tortuga)
        nil
        (escribir-svg archivo (:posicion tortuga) new-position))
      (Tortuga. new-position (:angulo tortuga) (:pluma-arriba? tortuga))))

  (izquierda
    [tortuga angulo-giro]
    (Tortuga. (:posicion tortuga) (- (:angulo tortuga) angulo-giro) (:pluma-arriba? tortuga)))

  (derecha
    [tortuga angulo-giro]
    (Tortuga. (:posicion tortuga) (+ (:angulo tortuga) angulo-giro) (:pluma-arriba? tortuga)))

  (pluma-abajo [tortuga]
    (Tortuga. (:posicion tortuga) (:angulo tortuga)  false))

  (pluma-arriba
    [tortuga]
    (Tortuga. (:posicion tortuga) (:angulo tortuga) true)))

