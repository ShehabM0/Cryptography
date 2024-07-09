# Ceaser
The Caesar cipher involves replacing each letter of the alphabet with the letter standing three places further down the alphabet.

## Encryption
The Encryption Algorithm

    C = E(K, P) = (P + K) mod 26

## Decryption
The Decryption Algorithm

    P = D(K, C) = (C - K) mod 26

where **C** is the `ciphertext` and **P** is the `plaintext`, between **K** is the `key`.

