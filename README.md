What?
===

This is a very simple, rough, and probably bad test of RNG 
wrapping-unwrapping performance. Hotspotting makes it challenging, 
because the implemented pattern is far from typical.

See: https://github.com/generativists/ThirdWay/issues/1


Output
---
On my MBP with:

    java version "1.8.0_66"
    Java(TM) SE Runtime Environment (build 1.8.0_66-b17)
    Java HotSpot(TM) 64-Bit Server VM (build 25.66-b17, mixed mode)

and:

    2.3 GHz Intel Core i7

The program output is:

    > run
    [info] Running RNGWrappingPerformance 
    scala.util.Random   760ms   1.3157894736842105E7/s
    java.util.Random    708ms   1.4124293785310734E7/s
    MersenneTwister     555ms   1.801801801801802E7/s
    RandomAdaptor MT    597ms   1.6750418760469012E7/s

    ----

    scala.util.Random  =>  java.util.Random 744ms   1.3440860215053763E7/s
    java.util.Random   => scala.util.Random 739ms   1.3531799729364008E7/s
    scala.util.Random  =>   RandomGenerator 624ms   1.6025641025641026E7/s
