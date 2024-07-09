# Rail Fence
- The Rail Fence cipher is a type of transposition cipher where the plaintext is written in a zigzag pattern of depth $n$.
- This method rearranges the characters of the plaintext rather than substituting them.

## Encryption
- **Key:**
    - Rail Fence *key* is about the depth $n$, where is specifies Grid rows.
- **Grid:**
    - Write the plaintext message in a zigzag pattern across the Grid.
- **Ciphertext:**
    - Write down all the letters row by row.

## Decryption
- **Grid:**
    - Grid rows is equal to depth $n$.
    - Grid columns is equal to length of Ciphertext.
    - Reconstruct the Grid by Marking the grid cells using the zigzag pattern.
    - Fill the marked grid cells row by row using *Ciphertext* letters.
- **Plaintext:**
    - Read the letters in the zigzag pattern where they were originally written.

