(ns proyectotp2.filemanager
  (:require
    [clojure.string :as str]))

(defn obtener-archivo
  "Devuelve un vector de Strings sacado del archivo de nombre especificado con separacion de salto de linea."
  [read_file]
  (str/split (slurp read_file) #"\n"))

(defn obtener-angulo
  "Devuelve el angulo como String. El primer String del vector obtenido del archivo de lectura."
  [read_vect]
  (get read_vect 0))

(defn obtener-axioma
  "Devuelve el axioma como String. El segundo String del vector obtenido del archivo de lectura."
  [read_vect]
  (get read_vect 1))

(defn obtener-reglas
  "Devuelve el axioma como String. El tercero a ultimo String del vector obtenido del archivo de lectura."
  [read_vect]
  (next (next read_vect)))

(defn escribir-primer-linea
  "Escribe la primera linea para un archivo SVG en el archivo especificado.
  Sobreescribe el archivo."
  [write_file]
  (spit write_file "<svg viewBox=\"-9000 -9000 20000 20000\" preserveAspectRatio=\"xMidYMid meet\" xmlns=\"http://www.w3.org/2000/svg\">"))

(defn escribir-linea-vector
  "Escribe una linea (tal que dos vectores posicion) en formato SVG en el archivo especificado.
  No sobreescribe el archivo."
  [write_file start_pos end_pos]
  (spit write_file (format "\n<line x1=\"%.1f\" y1=\"%.1f\" x2=\"%.1f\" y2=\"%.1f\" stroke-width=\"1\" stroke=\"black\" />" (get start_pos 0) (get start_pos 1) (get end_pos 0) (get end_pos 1)) :append true))

(defn escribir-ultima-linea
  "Escribe la ultima linea para un archivo SVG en el archivo especificado.
  No sobreescribe el archivo."
  [write_file]
  (spit write_file "\n</svg>" :append true))