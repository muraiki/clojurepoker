# clojurepoker

A poker library for Clojure and Clojurescript, early stages.

# Usage

Generate a hand:
```clj
(hand [:hearts :ace] [:spades :queen] [:diamonds :king] [:clubs :4 :8])
```

A card is a map with this format:
```clj
{:suit :hearts, :rank :ace}
```

Draw seven cards and determine the best possible hand that can generated from them:
```clj
(bestallhands (:drawncards (drawmulti 7 deck))
```

Compare two hands: (I could probably use a better label than :best)
```clj
(comparetwohands {:best :royalflush, :result (hand [:hearts :queen :10 :jack :ace :king])}
                 {:best :onepair, :result (hand [:hearts :2 :3 :4] [:spades :4 :jack])})
```

## License

Copyright © 2013 Erik Ferguson and Chip Hogg.

Distributed under the Eclipse Public License, the same as Clojure.