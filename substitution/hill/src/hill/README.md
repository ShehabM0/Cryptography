# Hill
- The Hill cipher is a substitution cipher that uses linear algebra for encryption.
- It encrypts blocks of text using matrix multiplication.

## Encryption
The Encryption algorithm takes m successive plaintext letters and substitutes for them m ciphertext letters using $m \ast m$ Key matrix. The substitution is determined by row vectors and matrices in which each character is assigned a numerical value (a = 0, b = 1,... , z = 25).

$$
C = P \cdot K \bmod 26
$$

## Decryption
The Decryption algorithm requires using the inverse of the matrix K such that:-

$$
K^{-1} = |K|^{-1} \cdot adj(K)\pmod{26}
$$

$$
P = C \cdot |K|^{-1} \bmod 26
$$

- $C$ is row vector of length m representing the `Plaintext`
- $P$ is row vector of length m representing the `Ciphertext`
- $K$ is a $m \ast m$ matrix representing the `Key`
- $|K|^{-1}$ is the `Modular Inverse of the Determinant`
- $adj(K)$ is the `Adjugate Matrix`

