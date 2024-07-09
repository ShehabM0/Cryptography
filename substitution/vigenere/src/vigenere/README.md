# Vigenère
- Simplest substitution cipher effectively multiple caesar ciphers.
- Keyword length $m$ must be same as plaintext length, write the keyword repeated above the plaintext.

## Encryption
A general equation of the encryption process is

$$
C_\mathbf{i} = (P_\mathbf{i} + K_\mathbf{i \bmod m}) \bmod 26
$$

## Decryption
A general equation of the decryption process is

$$
P_\mathbf{i} = (C_\mathbf{i} - K_\mathbf{i \bmod m}) \bmod 26
$$

- $C$ is the `Ciphertext`
- $P$ is the `Plaintext`
- $K$ is the `Key`

