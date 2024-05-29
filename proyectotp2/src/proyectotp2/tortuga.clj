(ns proyectotp2.tortuga
  (:require
    [clojure.math :as math]
    [proyectotp2.filemanager :as fman]))


(defrecord Tortuga [posicion angulo pluma-arriba?])

(defn duplicar
  [tortuga]
  (Tortuga. (:posicion tortuga) (:angulo tortuga) (:pluma-arriba? tortuga)))

(defn izquierda
  [tortuga angulo-giro]
  (Tortuga. (:posicion tortuga) (- (:angulo tortuga) angulo-giro) (:pluma-arriba? tortuga)))

(defn derecha
  [tortuga angulo-giro]
  (Tortuga. (:posicion tortuga) (+ (:angulo tortuga) angulo-giro) (:pluma-arriba? tortuga)))

(defn pluma-arriba
  [tortuga]
  (Tortuga. (:posicion tortuga) (:angulo tortuga) true))

(defn pluma-abajo
  [tortuga]
  (Tortuga. (:posicion tortuga) (:angulo tortuga) false))

(defn avanzar
  [tortuga cantidad archivo]
  (let [direction (vector (math/cos (math/to-radians (:angulo tortuga))) (math/sin (math/to-radians (:angulo tortuga))))
        new-position (vector (+ (get (:posicion tortuga) 0) (* (get direction 0) cantidad)) (+ (get (:posicion tortuga) 1) (* (get direction 1) cantidad)))]
    (if (:pluma-arriba? tortuga)
      nil
      (fman/escribir-linea-vector archivo (:posicion tortuga) new-position))
    (Tortuga. new-position (:angulo tortuga) (:pluma-arriba? tortuga))))

(defn avanzar-sin-escribir
  [tortuga cantidad archivo]
  (pluma-arriba tortuga)
  (avanzar tortuga cantidad archivo)
  (pluma-abajo tortuga))


