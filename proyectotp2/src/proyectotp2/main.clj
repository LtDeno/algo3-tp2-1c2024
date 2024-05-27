(ns proyectotp2.main
  (:require
    [clojure.string :as str]
    [clojure.math :as math]
    [proyectotp2.sistemal :as sisl]
    ))
(java.util.Locale/setDefault java.util.Locale/US)

(defn funcion-recursiva
  [i pila-tortugas sistema-l ruta-svg]
  (if (zero? i)
    (spit ruta-svg "</svg>" :append true)
    (funcion-recursiva pila-tortugas sistema-l ruta-svg (dec i))))

(defn -main [read_file num_iterations write_file]
  (spit write_file "<svg viewBox=\"-50 -150 300 200\" xmlns=\"http://www.w3.org/2000/svg\">")
  (let [pila (Pila. [(Tortuga. (vector 0.0 0.0) 0.0 true)])
    sistema-l-especificaciones (str/split (slurp read_file) #"\n")
    angulo-giro (first sistema-l-especificaciones)
    axioma (first (rest sistema-l-especificaciones))
    pares-predecesor-sucesor (apply merge (map #(hash-map (keyword (first %1)) (get (vec (rest %1)) 0)) (vec (map #(str/split % #" ") (rest (rest sistema-l-especificaciones))))))
    sistema-l (Sistema-L. axioma angulo-giro pares-predecesor-sucesor)]
    (funcion-recursiva num_iterations pila sistema-l write_file)))

(defn escribir-svg
  "Recibe el nombre de un archivo al cual agregar una linea en SVG.
   Recibe un mapping [] o {} con las coordenadas XY del punto incial y el punto final."
  [archivo punto-a punto-b]
  (spit archivo (format "<line x1=\"%.1f\" y1=\"%.1f\" x2=\"%.1f\" y2=\"%.1f\" stroke-width=\"1\" stroke=\"black\" />" (get punto-a 0) (get punto-a 1) (get punto-b 0) (get punto-b 1)) :append true))

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


(defprotocol PilaAcciones
  (apilar [pila elemento])
  (desapilar [pila])
  (ver-tope [pila]))

(defrecord Pila [elementos]
  PilaAcciones
  (apilar [pila elemento] ())
  (desapilar [pila] ())
  (ver-tope [pila] ()))

(defprotocol Sistema-LAcciones
  (suceder [sistema-l]))

(defrecord Sistema-L [axioma angulo-giro pares-predecesor-sucesor]
  Sistema-LAcciones
  (suceder [sistema-l] ()))
