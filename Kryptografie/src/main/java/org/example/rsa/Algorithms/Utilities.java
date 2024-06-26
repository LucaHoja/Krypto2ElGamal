package org.example.rsa.Algorithms;



import org.example.rsa.PairTypes.PrivateKey;
import org.example.rsa.PairTypes.PublicKey;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Utilities {

    private static double m = 200;

    public static double getM() {
        return m;
    }

    public static void setM(double m) {
        Utilities.m = m;
    }

    /**
     * primes up to 1000 to check against
     */
    private static final BigInteger[] smallPrimes = {
            BigInteger.valueOf(2),
            BigInteger.valueOf(3),
            BigInteger.valueOf(5),
            BigInteger.valueOf(7),
            BigInteger.valueOf(11),
            BigInteger.valueOf(13),
            BigInteger.valueOf(17),
            BigInteger.valueOf(19),
            BigInteger.valueOf(23),
            BigInteger.valueOf(29),
            BigInteger.valueOf(31),
            BigInteger.valueOf(37),
            BigInteger.valueOf(41),
            BigInteger.valueOf(43),
            BigInteger.valueOf(47),
            BigInteger.valueOf(53),
            BigInteger.valueOf(59),
            BigInteger.valueOf(61),
            BigInteger.valueOf(67),
            BigInteger.valueOf(71),
            BigInteger.valueOf(73),
            BigInteger.valueOf(79),
            BigInteger.valueOf(83),
            BigInteger.valueOf(89),
            BigInteger.valueOf(97),
            BigInteger.valueOf(101),
            BigInteger.valueOf(103),
            BigInteger.valueOf(107),
            BigInteger.valueOf(109),
            BigInteger.valueOf(113),
            BigInteger.valueOf(127),
            BigInteger.valueOf(131),
            BigInteger.valueOf(137),
            BigInteger.valueOf(139),
            BigInteger.valueOf(149),
            BigInteger.valueOf(151),
            BigInteger.valueOf(157),
            BigInteger.valueOf(163),
            BigInteger.valueOf(167),
            BigInteger.valueOf(173),
            BigInteger.valueOf(179),
            BigInteger.valueOf(181),
            BigInteger.valueOf(191),
            BigInteger.valueOf(193),
            BigInteger.valueOf(197),
            BigInteger.valueOf(199),
            BigInteger.valueOf(211),
            BigInteger.valueOf(223),
            BigInteger.valueOf(227),
            BigInteger.valueOf(229),
            BigInteger.valueOf(233),
            BigInteger.valueOf(239),
            BigInteger.valueOf(241),
            BigInteger.valueOf(251),
            BigInteger.valueOf(257),
            BigInteger.valueOf(263),
            BigInteger.valueOf(269),
            BigInteger.valueOf(271),
            BigInteger.valueOf(277),
            BigInteger.valueOf(281),
            BigInteger.valueOf(283),
            BigInteger.valueOf(293),
            BigInteger.valueOf(307),
            BigInteger.valueOf(311),
            BigInteger.valueOf(313),
            BigInteger.valueOf(317),
            BigInteger.valueOf(331),
            BigInteger.valueOf(337),
            BigInteger.valueOf(347),
            BigInteger.valueOf(349),
            BigInteger.valueOf(353),
            BigInteger.valueOf(359),
            BigInteger.valueOf(367),
            BigInteger.valueOf(373),
            BigInteger.valueOf(379),
            BigInteger.valueOf(383),
            BigInteger.valueOf(389),
            BigInteger.valueOf(397),
            BigInteger.valueOf(401),
            BigInteger.valueOf(409),
            BigInteger.valueOf(419),
            BigInteger.valueOf(421),
            BigInteger.valueOf(431),
            BigInteger.valueOf(433),
            BigInteger.valueOf(439),
            BigInteger.valueOf(443),
            BigInteger.valueOf(449),
            BigInteger.valueOf(457),
            BigInteger.valueOf(461),
            BigInteger.valueOf(463),
            BigInteger.valueOf(467),
            BigInteger.valueOf(479),
            BigInteger.valueOf(487),
            BigInteger.valueOf(491),
            BigInteger.valueOf(499),
            BigInteger.valueOf(503),
            BigInteger.valueOf(509),
            BigInteger.valueOf(521),
            BigInteger.valueOf(523),
            BigInteger.valueOf(541),
            BigInteger.valueOf(547),
            BigInteger.valueOf(557),
            BigInteger.valueOf(563),
            BigInteger.valueOf(569),
            BigInteger.valueOf(571),
            BigInteger.valueOf(577),
            BigInteger.valueOf(587),
            BigInteger.valueOf(593),
            BigInteger.valueOf(599),
            BigInteger.valueOf(601),
            BigInteger.valueOf(607),
            BigInteger.valueOf(613),
            BigInteger.valueOf(617),
            BigInteger.valueOf(619),
            BigInteger.valueOf(631),
            BigInteger.valueOf(641),
            BigInteger.valueOf(643),
            BigInteger.valueOf(647),
            BigInteger.valueOf(653),
            BigInteger.valueOf(659),
            BigInteger.valueOf(661),
            BigInteger.valueOf(673),
            BigInteger.valueOf(677),
            BigInteger.valueOf(683),
            BigInteger.valueOf(691),
            BigInteger.valueOf(701),
            BigInteger.valueOf(709),
            BigInteger.valueOf(719),
            BigInteger.valueOf(727),
            BigInteger.valueOf(733),
            BigInteger.valueOf(739),
            BigInteger.valueOf(743),
            BigInteger.valueOf(751),
            BigInteger.valueOf(757),
            BigInteger.valueOf(761),
            BigInteger.valueOf(769),
            BigInteger.valueOf(773),
            BigInteger.valueOf(787),
            BigInteger.valueOf(797),
            BigInteger.valueOf(809),
            BigInteger.valueOf(811),
            BigInteger.valueOf(821),
            BigInteger.valueOf(823),
            BigInteger.valueOf(827),
            BigInteger.valueOf(829),
            BigInteger.valueOf(839),
            BigInteger.valueOf(853),
            BigInteger.valueOf(857),
            BigInteger.valueOf(859),
            BigInteger.valueOf(863),
            BigInteger.valueOf(877),
            BigInteger.valueOf(881),
            BigInteger.valueOf(883),
            BigInteger.valueOf(887),
            BigInteger.valueOf(907),
            BigInteger.valueOf(911),
            BigInteger.valueOf(919),
            BigInteger.valueOf(929),
            BigInteger.valueOf(937),
            BigInteger.valueOf(941),
            BigInteger.valueOf(947),
            BigInteger.valueOf(953),
            BigInteger.valueOf(967),
            BigInteger.valueOf(971),
            BigInteger.valueOf(977),
            BigInteger.valueOf(983),
            BigInteger.valueOf(991),
            BigInteger.valueOf(997)
    };

    private static BigInteger[] tryForPrime(double m, BigInteger a, BigInteger b, int millerRabinTrials){
        List<BigInteger> candidates = generateListOfRandomNumbersForSafePrimes(a, b,5, m);
        for (BigInteger candidate : candidates) {
            //candidate = candidate.setBit(0);
//            boolean divisible = false;
//            for (BigInteger smallPrime : smallPrimes){
//                BigInteger x = candidate.mod(smallPrime);
//                if(x.equals(BigInteger.ZERO) || x.equals(smallPrime.subtract(BigInteger.ONE).divide(BigInteger.TWO))){
//                    divisible = true;
//                    break;
//                }
//            }
            if (!isNotPrime(candidate, millerRabinTrials, 200)) {
                return new BigInteger[]{candidate,BigInteger.valueOf((long) m)};
            }
        }
        return null;
    }

    /**
     * returns random number with primality check using Miller-Rabin
     * @param a lower bound
     * @param b upper bound
     * @param millerRabinTrials millerrabintrials
     * @return random biginteger which is probably prime
     */
    public static BigInteger generateRandomPrime(BigInteger a, BigInteger b, int millerRabinTrials) {
        int numberThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numberThreads);
        List<Future<BigInteger[]>> futures = new ArrayList<>();
        double Seed = getM();

        try {
            while (true) {
                for (int i = 0; i < numberThreads; i++) {
                    final double currentM = Seed + i; // Increment m for each thread
                    System.out.println(currentM);
                    Future<BigInteger[]> future = executor.submit(() -> tryForPrime(currentM, a, b, millerRabinTrials));
                    futures.add(future);
                }
                Seed = Seed + numberThreads;
                for (Future<BigInteger[]> future : futures) {
                    BigInteger[] randomNum = future.get();
                    if (randomNum != null) {
                        setM(Seed + numberThreads);
                        return randomNum[0];
                    }
                }
                futures.clear();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return null;
    }

    public static BigInteger generateRandomPrime(int bitlength, int millerRabinTrials){
        BigInteger a = BigInteger.TWO.pow(bitlength - 1);
        BigInteger b = BigInteger.TWO.pow(bitlength);
        return generateRandomPrime(a,b,millerRabinTrials);
    }

    public static BigInteger[] generateRandomSafePrime(int bitlength, int millerRabinTrials){
        BigInteger a = BigInteger.TWO.pow(bitlength - 1);
        BigInteger b = BigInteger.TWO.pow(bitlength);
        return generateRandomSafePrime(a,b,millerRabinTrials);
    }

    /**
     * returns random number with primality check using Miller-Rabin
     * @param a lower bound
     * @param b upper bound
     * @param millerRabinTrials millerrabintrials
     * @return random biginteger which is probably prime
     */
    public static BigInteger[] generateRandomSafePrime(BigInteger a, BigInteger b, int millerRabinTrials) {
        int numberThreads = 20;
        ExecutorService executor = Executors.newFixedThreadPool(numberThreads);
        List<Future<BigInteger[]>> futures = new ArrayList<>();
        double Seed = getM();
        int count = 1;

        try {
            while (true) {
                for (int i = 0; i < numberThreads; i++) {
                    final double currentM = Seed + i; // Increment m for each thread

                    Future<BigInteger[]> future = executor.submit(() -> tryForPrime(currentM, a, b, millerRabinTrials));
                    futures.add(future);
                }
                Seed = Seed + numberThreads;
                for (Future<BigInteger[]> future : futures) {
                    BigInteger[] candidate = future.get();
                    if (candidate != null) {
                        System.out.println(count++ + " Primecandidate for Secure Prime: " + candidate[0] + "Seed: " + candidate[1]);
                        BigInteger p = candidate[0].multiply(BigInteger.TWO).add(BigInteger.ONE);
                        if (!isNotPrime(p,millerRabinTrials, 200)){
                            setM(Seed + numberThreads);
                            return new BigInteger[]{candidate[0], p};
                        }
//                        p = candidate[0].subtract(BigInteger.ONE).divide(BigInteger.TWO);
//                        if (!isNotPrime(p,millerRabinTrials, 200)){
//                            setM(Seed + numberThreads);
//                            return new BigInteger[]{p, candidate[0]};
//                        }
                    }
                }
                futures.clear();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return null;
    }




    /**
     * Miller Rabin primality check
     * @param probablyPrime number to check
     * @param k number of trials
     * @param m seed
     * @return false if probably prime
     */
    public static Boolean isNotPrime(BigInteger probablyPrime, int k, double m) {

        BigInteger j = probablyPrime.subtract(BigInteger.ONE);
        BigInteger exponent = probablyPrime.subtract(BigInteger.TWO);

        int s = 0;
        while (j.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            j = j.divide(BigInteger.TWO);
            s++;
        }

        for (BigInteger x : Utilities.generateListOfRandomNumbers(BigInteger.TWO,exponent,k,m)) {

            BigInteger y = FastExponentiation.exponentiation(x, j, probablyPrime);

            if (!y.equals(BigInteger.ONE) || y.equals(probablyPrime.subtract(BigInteger.ONE))) {
                continue;
            }
            int r;
            for (r = 1; r < s; r++) {
                y = FastExponentiation.exponentiation(y, BigInteger.TWO, probablyPrime);
                if (y.equals(BigInteger.ONE)) {
                    return false;
                }
                if (y.equals(probablyPrime.subtract(BigInteger.ONE))) {
                    break;
                }
            }
            if (r == s) {
                return false;
            }
        }
        return true;
    }


    /**
     * returns random biginteger
     * based on it security script page 97
     * @param a lower bounds
     * @param b upper bounds
     * @return random biginteger
     */
    public static BigInteger getRandomBigInteger(BigInteger a, BigInteger b) {
        setM(getM()+1);
        return generateListOfRandomNumbers(a,b,1,getM()).get(0);
    }

    public static List<BigInteger> generateListOfRandomNumbers(BigInteger a, BigInteger b, int count, double m) {

        BigDecimal intervalLength = new BigDecimal(b.subtract(a).add(BigInteger.ONE));
        List<BigInteger> randomNumbers = new ArrayList<>();

        double sqrtM = Math.sqrt(m);

        for (int n = 1; n <= count; n++) {
            double nSqrtM = n * sqrtM;

            double fractionalPart = nSqrtM - Math.floor(nSqrtM);

            BigDecimal scaledValue = BigDecimal.valueOf(fractionalPart).multiply(intervalLength);
            BigInteger sn = a.add(scaledValue.setScale(0, RoundingMode.FLOOR).toBigInteger());

            randomNumbers.add(sn);
        }

        return randomNumbers;
    }

    public static List<BigInteger> generateListOfRandomNumbersForSafePrimes(BigInteger a, BigInteger b, int count, double m) {

        BigDecimal intervalLength = new BigDecimal(b.subtract(a).add(BigInteger.ONE));
        List<BigInteger> randomNumbers = new ArrayList<>();

        double sqrtM = Math.sqrt(m);
        int n = 1;
        while (randomNumbers.size() <= count && n <= 1_000_000){
            double nSqrtM = n * sqrtM;

            double fractionalPart = nSqrtM - Math.floor(nSqrtM);

            BigDecimal scaledValue = BigDecimal.valueOf(fractionalPart).multiply(intervalLength);
            BigInteger sn = a.add(scaledValue.setScale(0, RoundingMode.FLOOR).toBigInteger());
            sn = sn.setBit(0);
            boolean Composite = false;
            for (BigInteger smallPrime : smallPrimes){
                BigInteger x = sn.mod(smallPrime);
                if(x.equals(BigInteger.ZERO) || x.equals(smallPrime.subtract(BigInteger.ONE).divide(BigInteger.TWO))){
                    Composite = true;
                    break;
                }
            }
            if (!Composite){
                if (sn.mod(BigInteger.valueOf(6)).equals(BigInteger.valueOf(5))){
                    BigInteger candidate = sn.multiply(BigInteger.TWO).add(BigInteger.ONE);
                    boolean compositeCandidate = false;
                    for (BigInteger smallPrime : smallPrimes){
                        BigInteger x = candidate.mod(smallPrime);
                        if(x.equals(BigInteger.ZERO) || x.equals(smallPrime.subtract(BigInteger.ONE).divide(BigInteger.TWO))){
                            compositeCandidate = true;
                            break;
                        }
                    }
                    if (!compositeCandidate){
                        if (candidate.mod(BigInteger.valueOf(12)).equals(BigInteger.valueOf(11))) {
                            if (!isNotPrime(sn, 1, 200) && !isNotPrime(candidate, 1, 200)) {
                                randomNumbers.add(sn);
                            }
                        }
                    }
                }
            }
            n++;
        }

        return randomNumbers;
    }


}
