# Miller-Rabin
- The Miller-Rabin Primality Test is a probabilistic algorithm used to determine if a number is a probable prime
- It is widely used due to its efficiency and reliability.
- This test is a generalization of the Fermat Primality Test and can be used to verify the primality of large numbers.

## Algorithm
1. Input Odd Integer $n$, otherwise it would be *composite*.
2. Let $n = 2^{k} \cdot q$, where $k$ is a positive integer and $q$ is an odd.
3. Consider an integer $a$, called a base, which is coprime to $n$ i.e., $gcd(a, n) = 1$
4. $n$ is said to be a strong **probable prime** to base a if one of these congruence relations holds:

$$
a^{q} \equiv 1 \pmod{n}
$$

$$
a^{2^{i} \cdot q} \equiv -1 \pmod{n} \quad
\exists i \in (0, k]
$$

5. if n is not a strong **probable prime to base $a$** after $k$ iterations, then $n$ is definitely **composite**, and $a$ is called a **witness** for the compositeness of $n$.

