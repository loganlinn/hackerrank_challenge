(ns hackerrank.challenge
  (:require clojure.set))

(defn- alpha-rotate [c shift]
  "Circularly shifts an alpha character. All other characters are left the same"
  (let [between #(and (>= %1 (int %2)) (<= %1 (int %3)))
        alpha-wrap (fn [c shift offset] (+ (mod (- (+ c shift) offset) 26) offset))]
    (if (between c \A \Z) (alpha-wrap c shift (int \A))
      (if (between c \a \z) (alpha-wrap c shift (int \a)) c))))

(defn- decypher [input shift]
  "Simple substitution cypher. Only alphabetic characters are operated on"
  (reduce str
          (map #(char (alpha-rotate % shift))
               (map #(int (char %)) input))))

(defn solve-sample [sample-encoded sample-decoded string]
  "Decyphers string given a sample"
  (let [shift (- (int (first sample-decoded)) (int (first sample-encoded)))]
    (if-not (= (decypher sample-encoded shift) sample-decoded)
      "Decypher sample failed"
      (decypher string shift))))

;(solve-sample "Mrvhsklqh Slqfnqhb" "Josephine Pinckney" "hljkw kxqguhg dqg iliwb-wzr")
;(solve-sample "Fssj Ufwwnxm" "Anne Parrish" "tsj ymtzxfsi fsi ymwjj")
;(solve-sample "Whuhth Jpaf" "Panama City" "upul aovbzhuk, zlclu obukylk huk mpmaf-vul")
;(solve-sample "Eujmrvra Wjkxtxe" "Vladimir Nabokov" "nrpqc cqxdbjwm, xwn qdwmanm jwm oxdach-oxda")

;;;;;;;;;;;;;

(def ^:private words ["zero" "one" "two" "three" "four" "five" "six" "seven" "eight" "nine"
                      "ten" "eleven" "twelve" "thirteen" "fourteen" "fifteen" "sixteen" "seventeen" "eighteen" "nineteen"
                      "twenty" "thirty" "fourty" "fifty" "sixty" "seventy" "eighty" "ninety"
                      "hundred" "thousand" "million" "billion" "trillion"
                      "and"])

(defn- char-diff [ch1 ch2] (- (int ch2) (int ch1)))

(defn- consistent-diff?
  "Determines if the word has a consistent shift width"
  ([word1 word2 diff] (seq [(= word1 (decypher word2 diff)) diff]))
  ([word1 word2] (consistent-diff? word1
                                   word2
                                   (#(if (pos? %) % (+ % 26)) ; normalize diff
                                       (char-diff (first word2) (first word1))))))

(defn- possible-diffs [word words]
  "Returns a set of all possible diffs (shift widths) for a spelled-out number"
  (let [matching-words (filter #(= (.length %) (.length word)) words)]
    (map second
         (filter first
                 (map #(consistent-diff? word %) matching-words)))))

(defn- find-diffs [string]
  "Finds intersection of all possible diffs for each word"
  (apply clojure.set/intersection (map #(set (possible-diffs % words))
                                       (re-seq #"\w+" string))))
(defn solve [string]
  "Decyphers a string of numbers (spelled out) with substitution cypher"
  (let [diffs (find-diffs string)]
    (if (> (count diffs) 1)
      (println (str "More than 1 character shift width: " diffs))
      (if (empty? diffs)
        (println "Failed to find character shift width")
        (decypher string (- (first diffs)))))))

;(solve "hljkw kxqguhg dqg iliwb-wzr")
;(solve "tsj ymtzxfsi fsi ymwjj")
;(solve "upul aovbzhuk, zlclu obukylk huk mpmaf-vul")
;(solve "nrpqc cqxdbjwm, xwn qdwmanm jwm oxdach-oxda")
;(solve "qtgp sfyocpo lyo dpgpyej-ptrse")
;(solve "clro qelrpxka, pbsbk erkaoba xka pbsbkqbbk")
;(solve "mcr nbiomuhx uhx mypyh")
