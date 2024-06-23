(ns proyectotp2.sistemal
  (:require
    [clojure.string :as str]
    [proyectotp2.tortuga :as tort]))

(defn obtener-reemplazo
  "Recibe Map de reglas segun filemanager y un Character para key.
  Devuelve un String."
  [reglas char]
  (let [reemplazo (get reglas char)]
    (if (nil? reemplazo) (String/valueOf char) reemplazo)))

(defn cambiar-ocurrencias-segun-reglas
  "Recibe un String a modificar, Map de reglas segun filemanager.
  Devuelve un String."
  [string reglas _unused]
  (reduce #(str/join (concat %1 (obtener-reemplazo reglas %2))) "" (seq string)))

(defn transformar-axioma
  "Recibe un axioma como String, un Map de reglas segun filemanager, y un Integer de cantidad de iteracione.
  Devuelve un Vector."
  [axioma reglas num_iteraciones]
  (vec (seq (reduce #(cambiar-ocurrencias-segun-reglas %1 reglas %2) axioma (range num_iteraciones)))))

(defn cambiar-tortuga-tope [tortuga tort_pile]
  (assoc tort_pile (- (count tort_pile) 1) tortuga))

(defn apilar-tortuga [tortuga tort_pile]
  (assoc tort_pile (count tort_pile) tortuga))

(defn desapilar-tortuga [tort_pile]
  (pop tort_pile))

(defn ejecutar-comando [cmd tort_pile turn_angle write_file]
  (cond
    (and (or (= cmd (first "F")) (= cmd (first "G"))) (not-empty tort_pile))
      (cambiar-tortuga-tope (tort/avanzar (last tort_pile) 5.0 write_file) tort_pile)
    (and (or (= cmd (first "f")) (= cmd (first "g"))) (not-empty tort_pile))
      (cambiar-tortuga-tope (tort/avanzar-sin-escribir (last tort_pile) 5.0 write_file) tort_pile)
    (and (= cmd (first "+")) (not-empty tort_pile))
      (cambiar-tortuga-tope (tort/derecha (last tort_pile) turn_angle) tort_pile)
    (and (= cmd (first "-")) (not-empty tort_pile))
      (cambiar-tortuga-tope (tort/izquierda (last tort_pile) turn_angle) tort_pile)
    (and (= cmd (first "|")) (not-empty tort_pile))
      (cambiar-tortuga-tope (tort/derecha (last tort_pile) 180.0) tort_pile)
    (= cmd (first "["))
      (apilar-tortuga (tort/duplicar (last tort_pile)) tort_pile)
    (and (= cmd (first "]")) (not-empty tort_pile))
      (desapilar-tortuga tort_pile)
    :else tort_pile
    ))

(defn procesar [cmd_seq turn_angle write_file]
  (reduce #(ejecutar-comando %2 %1 turn_angle write_file) (vector (tort/->Tortuga (vector 0.0 0.0) -90.0 false)) cmd_seq))