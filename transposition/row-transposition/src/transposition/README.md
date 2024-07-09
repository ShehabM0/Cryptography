# Row Transposition
- The Row Transposition cipher is a type of transposition cipher, where the plaintext is written out in rows of fixed length and then read out column by column according to a specified order to create the ciphertext.
- This method rearranges the characters of the plaintext rather than substituting them.

## Encryption
- **Key:**
    - Select a *key* that determines the order in which the columns will be read, The *key* should be a permutation of the numbers 1 through n, where $n$ is the number of columns.
- **Grid:**
    - Write the *plaintext* message row by row where the number of columns is determined by the *key* $n$.
    - If the length of the *plaintext* is not a multiple of the number of columns, pad the remaining grid cells by 'X' or alphabet letters that hasn't appeared before in the grid'.
- **Ciphertext:**
    - Write down all the letters column by column based on the order of columns that determined by the *key* length from 1 to $n$.

## Decryption
- **Grid:**
    - Knowing the key, create a grid with the same number of columns and fill it column by column according to the numerical order of the key.
- **Plaintext:**
    - Read the characters row by row to obtain the original plaintext.

