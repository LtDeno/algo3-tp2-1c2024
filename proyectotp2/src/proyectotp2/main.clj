(ns proyectotp2.main
  (:require
    [clojure.string :as str]
    [proyectotp2.sistemal :as sisl]
    ))

(defn -main [read_file num_iterations write_file]
  (transformar-axioma)
  (println read_file num_iterations write_file "Main.")
  (def pila (Pila. [(Tortuga. (vector 0 0) 0 true)]))
  (def sistema-l-especificaciones (str/split (slurp read_file) #"\n"))
  (def angulo-giro (first sistema-l-especificaciones))
  (def axioma (first (rest sistema-l-especificaciones)))
  (def pares-predecesor-sucesor (map seq (map #(str/split % #" ") (rest (rest sistema-l-especificaciones)))))
  (def sistema-l (apply ->Sistema-L axioma angulo-giro pares-predecesor-sucesor))
  (funcion-recursiva num_iterations pila sistema-l write_file))

(defn funcion-recursiva
  [i pila-tortugas sistema-l ruta-svg]
  (if (zero? i)
    nil
    (funcion-recursiva pila-tortugas sistema-l ruta-svg (dec i))))

(defprotocol TortugaAcciones
  (avanzar [tortuga cantidad archivo])
  (izquierda [tortuga angulo])
  (derecha [tortuga angulo])
  (pluma-abajo [tortuga])
  (pluma-arriba [tortuga]))

(defrecord Tortuga [posicion angulo pluma-arriba?]
  TortugaAcciones
  (avanzar [tortuga cantidad archivo] ())
  (izquierda [tortuga angulo] ())
  (derecha [tortuga angulo] ())
  (pluma-abajo [tortuga] ())
  (pluma-arriba [tortuga] ()))

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

(defrecord Sistema-L [axioma angulo-giro & pares-predecesor-sucesor]
  Sistema-LAcciones
  (suceder [sistema-l] ()))
