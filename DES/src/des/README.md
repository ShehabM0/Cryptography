# DES
- DES (Data Encryption Standard) is one of the most widely used block cipher cryptographic algorithms.
- Encrypts 64-bit data using 56-bit key with 16-round.

## DES Tables
- Initial Permutation ($IP$)
- Inverse Initial Permutation ($IP^{–1}$)
- Expansion Permutation ($E$)
- Permutation Function ($P$)
- S-Boxes ($S$)
- Permuted Choice One ($PC-1$)
- Permuted Choice Two ($PC-2$)
- Schedule of Left Shifts ($Shift$)

## Key Generation
- **Initial-Key:**
    - 64-bit binary key.
- **Sub-key:** 
    - Apply a fixed Permutation ($PC-1$) to the 64-bit key to generate a 56-bit sub-key.
    - Split the generated 56-bit key into two 28-bit halves labeled $C_{0}$ and $D_{0}$.
    - At each round of 16, $C_{i-1}$ and $D_{i-1}$ are separately subjected to a circular left shift using Schedule of Left Shifts ($Shift$).
- **Round-Key:**
    - Merge $C_{i}$ and $D_{i}$.
    - At each round of 16, Apply a fixed Permutation ($PC-2$) to the 56-bit key halves merged $C_{i}D_{i}$ resulting 48-bit round-key.

## Encryption
- **Initial-Plaintext:**
    - 64-bit plaintext.
    - Apply Initial Permutation ($IP$) to the 64-bit plaintext.
    - Split the generated 64-bit plaintext into two 56--bit halves labeled $L_{0}$ and $R_{0}$.
- **Feistel:**
    - Expansion Permutation ($E$): Expands the 32-bit half of the data to 48-bits.
    - XOR with Round Key: XORs the expanded 48-bit data with the current round's 48-bit subkey.
    - S-Boxes ($S): Divides the XOR result into 6-bit chunks and substitutes each chunk using the 8 predefined S-boxes
    - Permutation Function ($P$): Permutates the output of the S-boxes to create a new 32-bit output for the round.
- **Final Permutation:**
    - Merge $R_{16}$ and $L_{16}$ in reverse.
    - After the 16 rounds, Apply a final Permutation ($IP^{–1}$) to the merged 64-bit halves $R_{16}L_{16}$ resulting 64-bit Ciphertext.

## Decryption
- Same Encryption steps but the subkeys are applied in reverse order i.e.

$$
R_{1} = L_{0} \oplus F(R_{0}, K_{16})
$$

$$
\vdots
\notag
$$

$$
R_{16} = L_{15} \oplus F(R_{15}, K_{1})
$$



## Single Round
$$
R_{i} = L_{i-1} \oplus F(R_{i-1}, K_{i})
$$

$$
L_{i} = R_{i-1}
$$

- $K_{i}$ is `48-bits round key`
- $R_{i}$ is `32-bits input`
- $F$ is `Feistel cipher`
