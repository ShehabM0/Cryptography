# Caesar
The Caesar cipher involves replacing each letter of the alphabet with the letter standing three places further down the alphabet.

## Encryption
The Encryption Algorithm

$$
P = E(K, P) = (P + K) \bmod 26
$$

## Decryption
The Decryption Algorithm

$$
P = D(K, C) = (C - K) \bmod 26
$$

- $C$ is the `Ciphertext`
- $P$ is the `Plaintext`
- $K$ is the `Key`

