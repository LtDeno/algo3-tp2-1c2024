(ns proyectotp2.sistemal
  (:require
    [clojure.string :as str]
    [proyectotp2.tortuga :as tort]))

(defn get-string-if-char-at-first-pos [char string]
  (if (= 0 (compare (first char) (first string))) (reduced string) char))

(defn get-corresponding-rule [char rules]
  (str/trim (str/replace-first (reduce get-string-if-char-at-first-pos char rules) char " ")))

(defn change-recurrences-by-rules [to_change rules _unused]
  (reduce #(if (str/blank? (get-corresponding-rule (str %2) rules))
       (str/join (concat %1 (str %2)))
       (str/join (concat %1 (get-corresponding-rule (str %2) rules)))) "" (seq to_change)))

(defn transformar-axioma
  "Axioma es un String.
  Reglas es un vector de Strings.
  Iteraciones es un Number.
  Devuelve un vector de caracteres para usar como acciones."
  [axiom rules num_iterations]
  (vec (seq (reduce #(change-recurrences-by-rules %1 rules %2) axiom (range num_iterations)))))

(defn cambiar-tortuga-tope [tortuga tort_pile]
  (assoc tort_pile (- (count tort_pile) 1) tortuga))

(defn apilar-tortuga [tortuga tort_pile]
  (assoc tort_pile (count tort_pile) tortuga))

(defn desapilar-tortuga [tort_pile]
  (pop tort_pile))

(defn ejecutar-comando [cmd tort_pile turn_angle write_file]
  (println tort_pile)
  (cond
    (and (or (= cmd (first "F")) (= cmd (first "G"))) (not-empty tort_pile))
      (cambiar-tortuga-tope (tort/avanzar (last tort_pile) 1.0 write_file) tort_pile)
    (and (or (= cmd (first "f")) (= cmd (first "g"))) (not-empty tort_pile))
      (cambiar-tortuga-tope (tort/avanzar-sin-escribir (last tort_pile) 1.0 write_file) tort_pile)
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
    )
  )

(defn procesar [cmd_seq turn_angle write_file]
  (reduce #(ejecutar-comando %2 %1 turn_angle write_file) (vector (tort/->Tortuga (vector 0.0 0.0) 0.0 false)) cmd_seq))