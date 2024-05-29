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
  [write_file x_min y_min x_max y_max]
  (spit write_file (format "<svg viewBox=\"%.1f %.1f %.1f %.1f\" preserveAspectRatio=\"xMidYMid meet\" xmlns=\"http://www.w3.org/2000/svg\">\n" x_min y_min x_max y_max)))

(defn escribir-linea-vector
  "Escribe una linea (tal que dos vectores posicion) en formato SVG en el archivo especificado.
  No sobreescribe el archivo."
  [write_file start_pos end_pos]
  (spit write_file (format "<line x1=\"%.1f\" y1=\"%.1f\" x2=\"%.1f\" y2=\"%.1f\" stroke-width=\"1\" stroke=\"black\"/>\n" (get start_pos 0) (get start_pos 1) (get end_pos 0) (get end_pos 1)) :append true))

(defn escribir-ultima-linea
  "Escribe la ultima linea para un archivo SVG en el archivo especificado.
  No sobreescribe el archivo."
  [write_file]
  (spit write_file "\n</svg>" :append true))

(defn conseguir-floats
  "Devuelve un vector de floats obtenidos de leer el String como si fuese un elemento linea de SVG."
  [string]
  (vector (Float/parseFloat (get (str/split string #"\"") 1))
          (Float/parseFloat (get (str/split string #"\"") 3))
          (Float/parseFloat (get (str/split string #"\"") 5))
          (Float/parseFloat (get (str/split string #"\"") 7))))

(defn vector-maximizado
  "Devuelve un vector con los valores maximizados (en positivo y negativo) de ambos vectores"
  [vect_1 vect_2]
  (vector (min (get vect_1 0) (get vect_2 0) (get vect_2 2))
          (min (get vect_1 1) (get vect_2 1) (get vect_2 3))
          (max (get vect_1 2) (abs (get vect_2 0)) (abs (get vect_2 2)))
          (max (get vect_1 3) (abs (get vect_2 1)) (abs (get vect_2 3)))))

(defn obtener-valores-viewbox
  "Devuelve un vector ordenado segun x_min y_min ancho alto, cuyos valores son obtenidos del array de elementos linea pasados."
  [vect_elementos_linea]
  (reduce #(vector-maximizado %1 (conseguir-floats %2)) [(float 0) (float 0) (float 0) (float 0)] vect_elementos_linea))

(defn ajustar-viewbox
  "Modifica la primer linea del SVG guardado, cambiando el tamaño del viewBox para que contenga las lineas generadas
  Sobreescribe el archivo pero modificando la primer linea, el resto intacto.
  No contempla la existencia de una ultima linea de cierre de SVG."
  [write_file]
  (let [vect_archivo (str/split-lines (slurp write_file))
        vect_elementos_linea (rest vect_archivo)
        vect_margenes_viewbox (obtener-valores-viewbox vect_elementos_linea)]
    (escribir-primer-linea write_file
                           (- (get vect_margenes_viewbox 0) 50)
                           (- (get vect_margenes_viewbox 1) 50)
                           (+ (get vect_margenes_viewbox 2) (abs (get vect_margenes_viewbox 0)) 100)
                           (+ (get vect_margenes_viewbox 3) (abs (get vect_margenes_viewbox 0)) 100))
    (spit write_file (str/join "\n" vect_elementos_linea) :append true)))