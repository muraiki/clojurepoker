(defproject clojurepoker "0.1.0-SNAPSHOT"
  :description "A Clojure and Clojurescript poker library"
  
  :url "https://github.com/muraiki/clojurepoker"
  
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  :dependencies [[org.clojure/clojure "1.4.0"]]
  
  :min-lein-version "2.0.0"
  
  :plugins [[lein-cljsbuild "0.3.0"]]

  :source-paths ["src/clj"]
  
  :cljsbuild {

    :crossovers [clojurepoker.core clojurepoker.combo]

    :crossover-path "src/crossover-cljs"

    :crossover-jar false
    
    :builds {

      :dev
      {:source-paths ["src/crossover-cljs"]
        :compiler
        {:pretty-print true
         :output-to "public/clojurepoker-dev.js"
         :optimizations :whitespace}}

      :prod
      {:source-paths ["src/crossover-cljs"]
        :compiler
        {:pretty-print false
         :output-to "public/clojurepoker.js"
         :optimizations :advanced}}}})
