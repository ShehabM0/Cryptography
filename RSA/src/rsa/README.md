# RSA
- RSA (Rivest–Shamir–Adleman) is one of the first public-key cryptosystems and is widely used for secure data transmission.
- It is an asymmetric cryptographic algorithm, which means it uses a pair of keys a public key and a private key.

## Key Generation
1. Choose two large prime numbers $p$ and $q$.
2. Compute $n = p \cdot q$.
3. Compute $\phi(n) = (p - 1) \cdot (q - 1)$.
4. Choose an integer $e$ such that $1 < e < \phi(n)$ and $gcd(e, \phi(n)) = 1$.
5. Determine d as $d \equiv e^{−1} \pmod{\phi(n)}$.
6. Public Key is $(e, n)$, Private Key is $(d, n)$.

- $\phi(n)$ is the Euler's totient function.
- $d$ is the modular multiplicative inverse of $e$ modulo $\phi(n)$.

## Encryption
- Let Message $M$.
- Turns $M$ into an integer $m$, such that $0 ≤ m < n$.

$$
c \equiv m^{e} \pmod{n}
$$

## Decryption
- Recover $m$ from $c$ by using private key exponent $d$.

$$
m \equiv c^{d} \pmod{n}
$$

