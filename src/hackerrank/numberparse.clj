(ns hackerrank.numberparse)

(def ^{:private true} token-values
  {"zero" 0
   "one" 1
   "two" 2
   "three" 3
   "four" 4
   "five" 5
   "six" 6
   "seven" 7
   "eight" 8
   "nine" 9
   "ten" 10
   "eleven" 11
   "twelve" 12
   "thirteen" 13
   "fourteen" 14
   "fifteen" 15
   "sixteen" 16
   "seventeen" 17
   "eighteen" 18
   "nineteen" 19
   "twenty" 20
   "thirty" 30
   "fourty" 40
   "fifty" 50
   "sixty" 60
   "seventy" 70
   "eighty" 80
   "ninety" 90
   "hundred" 100
   "thousand" 1000
   "million" 1000000
   "billion" 1000000000
   "trillion" 1000000000000
   "and" 0})

(def ^{:private true} multiplicitive-tokens #{"hundred" "thousand" "million" "billion" "trillion"})

(defn- do-parse [tokens prev total]
  (let [token (first tokens)
        value (token-values token)]
    (if (empty? tokens)
      total
      (if (nil? value)
        (recur (rest tokens) prev total)
        (if (contains? multiplicitive-tokens token)
          (let [product (* value prev)]
            (recur (rest tokens) product (+ (- total prev) product)))
          (recur (rest tokens) value (+ total value)))))))

(defn- tokenize [input] (re-seq #"\w+" input))

(defn parse [s]
  (do-parse (tokenize s) 0 0))

(defn- parse-debug [s] [s (parse s)])
