(ns proyectotp2.sistemal
  (:require
    [clojure.string :as str]))

(defn get-string-if-char-at-first-pos [char string]
  (if (= 0 (compare (first char) (first string))) (reduced string) char))

(defn get-corresponding-rule [char rules]
  (str/trim (str/replace-first (reduce get-string-if-char-at-first-pos char rules) char " ")))

(defn change-recurrences-by-rules [toChange rules _unused]
  (reduce #(if (str/blank? (get-corresponding-rule (str %2) rules))
       (str/join (concat %1 (str %2)))
       (str/join (concat %1 (get-corresponding-rule (str %2) rules)))) "" (seq toChange)))

(defn transformar-axioma
  "Axioma es un String.
  Reglas es un vector de Strings.
  Iteraciones es un Integer.
  Devuelve un vector de caracteres."
  [axioma reglas iteraciones]
  (vec (seq (reduce #(change-recurrences-by-rules %1 reglas %2) axioma (range iteraciones)))))