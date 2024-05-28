(ns proyectotp2.tortuga
  (:require
    [clojure.math :as math]
    [proyectotp2.filemanager :as fman]))

(defprotocol TortugaAcciones
  (duplicar [tortuga])
  (izquierda [tortuga angulo-giro])
  (derecha [tortuga angulo-giro])
  (pluma-abajo [tortuga])
  (pluma-arriba [tortuga])
  (avanzar [tortuga cantidad archivo])
  (avanzar-sin-escribir [tortuga cantidad archivo]))

(defrecord Tortuga [posicion angulo pluma-arriba?]
  TortugaAcciones
  (duplicar
    [tortuga]
    (Tortuga. (:posicion tortuga) (:angulo tortuga) (:pluma-arriba? tortuga)))
  (izquierda
    [tortuga angulo-giro]
    (Tortuga. (:posicion tortuga) (- (:angulo tortuga) angulo-giro) (:pluma-arriba? tortuga)))
  (derecha
    [tortuga angulo-giro]
    (Tortuga. (:posicion tortuga) (+ (:angulo tortuga) angulo-giro) (:pluma-arriba? tortuga)))
  (pluma-arriba
    [tortuga]
    (Tortuga. (:posicion tortuga) (:angulo tortuga) true))
  (pluma-abajo
    [tortuga]
    (Tortuga. (:posicion tortuga) (:angulo tortuga) false))
  (avanzar
    [tortuga cantidad archivo]
    (let [direction (vector (math/cos (math/to-radians (:angulo tortuga))) (math/sin (math/to-radians (:angulo tortuga))))
          new-position (vector (+ (get (:posicion tortuga) 0) (* (get direction 0) cantidad)) (+ (get (:posicion tortuga) 1) (* (get direction 1) cantidad)))]
      (if (:pluma-arriba? tortuga)
        nil
        (fman/escribir-linea-vector archivo (:posicion tortuga) new-position))
      (Tortuga. new-position (:angulo tortuga) (:pluma-arriba? tortuga))))
  (avanzar-sin-escribir
    [tortuga cantidad archivo]
    (pluma-arriba tortuga)
    (avanzar tortuga cantidad archivo)
    (pluma-abajo tortuga)))